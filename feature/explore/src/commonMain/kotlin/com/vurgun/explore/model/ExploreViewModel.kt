package com.vurgun.explore.model


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.explore.model.ExploreUiEffect.*
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
        val exercises: List<Int> = emptyList(),
        val trainers: List<TrainerProfileCardItemViewData>,
        val facilities: List<FacilityProfileCardItemViewData>,
        val communities: List<Int> = emptyList(),
        val challenges: List<Int> = emptyList(),
    ) : ExploreUiState()
}

sealed class ExploreUiAction {
    data object OnClickBack : ExploreUiAction()
    data object OnClickExercises : ExploreUiAction()
    data object OnClickTrainers : ExploreUiAction()
    data object OnClickFacilities : ExploreUiAction()
    data object OnClickCommunities : ExploreUiAction()
    data object OnClickChallenges : ExploreUiAction()
    data class OnSelectCommunity(val communityId: Int) : ExploreUiAction()
    data class OnSelectChallenge(val challengeId: Int) : ExploreUiAction()
    data class OnSelectExercise(val exerciseId: Int) : ExploreUiAction()
    data class OnSelectTrainer(val trainerId: Int) : ExploreUiAction()
    data class OnSelectFacility(val facilityId: Int) : ExploreUiAction()
}

sealed class ExploreUiEffect {
    object NavigateToBack : ExploreUiEffect()
    data class NavigateToVisitTrainer(val trainerId: Int) : ExploreUiEffect()
    data class NavigateToVisitFacility(val facilityId: Int) : ExploreUiEffect()
    data object NavigateToExploreTrainers : ExploreUiEffect()
    data object NavigateToExploreFacilities : ExploreUiEffect()
    data object NavigateToExploreChallenges : ExploreUiEffect()
    data object NavigateToExploreCommunities : ExploreUiEffect()
    data class NavigateToExerciseDetail(val exerciseId: Int) : ExploreUiEffect()
}

class ExploreViewModel(
    private val exploreRepository: ExploreRepository,
) : ScreenModel {

    private val _uiState = UiStateDelegate<ExploreUiState>(ExploreUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<ExploreUiEffect>()
    val effect = _effect as SharedFlow<ExploreUiEffect>

    fun onAction(action: ExploreUiAction) {
        when (action) {
            ExploreUiAction.OnClickBack ->
                _effect.emitIn(screenModelScope, NavigateToBack)

            ExploreUiAction.OnClickChallenges -> {
//                TODO()
            }

            ExploreUiAction.OnClickCommunities -> {
//                TODO()
            }

            ExploreUiAction.OnClickExercises -> {
//                TODO()
            }

            ExploreUiAction.OnClickFacilities -> {
//                TODO()
            }

            ExploreUiAction.OnClickTrainers -> {
//                TODO()
            }

            is ExploreUiAction.OnSelectExercise -> {
//                TODO()
            }

            is ExploreUiAction.OnSelectFacility -> {
                _effect.emitIn(screenModelScope, NavigateToVisitFacility(action.facilityId))
            }

            is ExploreUiAction.OnSelectTrainer -> {
                _effect.emitIn(screenModelScope, NavigateToVisitTrainer(action.trainerId))
            }

            is ExploreUiAction.OnSelectChallenge -> {
//                TODO()
            }

            is ExploreUiAction.OnSelectCommunity -> {
//                TODO()
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
                    exercises = emptyList(),
                    trainers = trainers,
                    facilities = facilities,
                    challenges = emptyList(),
                    communities = emptyList()
                )
            )
        }
    }
}