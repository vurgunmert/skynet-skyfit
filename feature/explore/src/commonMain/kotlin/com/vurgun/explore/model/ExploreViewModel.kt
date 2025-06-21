package com.vurgun.explore.model


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.explore.ExploreRepository
import com.vurgun.skyfit.core.data.v1.domain.profile.FacilityProfileCardItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.TrainerProfileCardItemViewData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Error(val message: String?) : ExploreUiState()
    data class Content(
        val exercises: List<Any> = emptyList(),
        val trainers: List<TrainerProfileCardItemViewData>,
        val facilities: List<FacilityProfileCardItemViewData>,
        val communities: List<Any> = emptyList(),
        val challenges: List<Any> = emptyList(),
    ) : ExploreUiState()
}

sealed class ExploreAction {
    data object OnClickBack : ExploreAction()
    data object OnClickExercise : ExploreAction()
    data class OnClickTrainer(val trainerId: Int) : ExploreAction()
    data class OnClickFacility(val facilityId: Int) : ExploreAction()
    data object OnClickCommunities : ExploreAction()
    data object OnClickChallenges : ExploreAction()
    // TODO: add other actions
}

sealed class ExploreEffect {
    object NavigateToBack : ExploreEffect()
    data class NavigateToVisitTrainer(val trainerId: Int) : ExploreEffect()
    data class NavigateToVisitFacility(val facilityId: Int) : ExploreEffect()
    // TODO: add other effects
}

class ExploreViewModel(
    private val exploreRepository: ExploreRepository,
) : ScreenModel {

    private val _uiState = UiStateDelegate<ExploreUiState>(ExploreUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<ExploreEffect>()
    val effect = _effect as SharedFlow<ExploreEffect>

    fun onAction(action: ExploreAction) {
        when (action) {
            ExploreAction.OnClickBack ->
                _effect.emitIn(screenModelScope, ExploreEffect.NavigateToBack)

            is ExploreAction.OnClickTrainer ->
                _effect.emitIn(screenModelScope, ExploreEffect.NavigateToVisitTrainer(action.trainerId))

            is ExploreAction.OnClickFacility ->
                _effect.emitIn(screenModelScope, ExploreEffect.NavigateToVisitFacility(action.facilityId))

            else -> {

            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            _uiState.update(ExploreUiState.Loading)

            val facilities = exploreRepository
                .getAllFacilities()
                .getOrDefault(emptyList())
                .map {
                    FacilityProfileCardItemViewData(
                        facilityId = it.gymId,
                        imageUrl = it.backgroundImageUrl.toString(),
                        name = it.facilityName,
                        memberCount = it.memberCount,
                        trainerCount = it.trainerCount,
                        rating = it.point
                    )
                }


            val trainers = exploreRepository.getAllTrainers()
                .getOrDefault(emptyList())
                .map {
                    TrainerProfileCardItemViewData(
                        it.trainerId,
                        imageUrl = it.profileImageUrl.toString(),
                        name = it.fullName,
                        followerCount = it.followerCount,
                        classCount = it.lessonCount,
                        videoCount = it.videoCount,
                        rating = it.point
                    )
                }

            _uiState.update(
                ExploreUiState.Content(
                    trainers = trainers,
                    facilities = facilities,
                )
            )
        }
    }
}