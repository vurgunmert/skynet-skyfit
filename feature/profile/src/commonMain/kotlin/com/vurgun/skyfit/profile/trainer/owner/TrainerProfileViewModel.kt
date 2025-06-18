package com.vurgun.skyfit.profile.trainer.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionRowViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.profile.model.ProfileDestination
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface TrainerProfileUiState {
    data object Loading : TrainerProfileUiState
    data class Error(val message: String) : TrainerProfileUiState
    data class Content(
        val profile: TrainerProfile,
        val destination: ProfileDestination = ProfileDestination.About,
        val isVisiting: Boolean = false,
        val isFollowing: Boolean = false,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val gallery: PhotoGalleryStackViewData? = null,
        val specialties: LifestyleActionRowViewData? = null,
        val posts: List<SocialPostItemViewData> = emptyList()
    ) : TrainerProfileUiState
}

sealed interface TrainerProfileUiAction {
    data class OnDestinationChanged(val destination: ProfileDestination) : TrainerProfileUiAction
    data object OnClickSettings : TrainerProfileUiAction
    data object OnClickToCreatePost : TrainerProfileUiAction
    data object OnClickToAppointments : TrainerProfileUiAction
}

sealed interface TrainerProfileUiEffect {
    data object NavigateToSettings : TrainerProfileUiEffect
    data object NavigateToCreatePost : TrainerProfileUiEffect
    data object NavigateToAppointments : TrainerProfileUiEffect
}

class TrainerProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val trainerRepository: TrainerRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<TrainerProfileUiState>(TrainerProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<TrainerProfileUiEffect>()
    val effect: SharedFlow<TrainerProfileUiEffect> = _effect

    fun onAction(action: TrainerProfileUiAction) {
        when (action) {
            is TrainerProfileUiAction.OnDestinationChanged -> updateDestination(action.destination)
            is TrainerProfileUiAction.OnClickToCreatePost -> emitEffect(TrainerProfileUiEffect.NavigateToCreatePost)
            is TrainerProfileUiAction.OnClickSettings -> emitEffect(TrainerProfileUiEffect.NavigateToSettings)
            is TrainerProfileUiAction.OnClickToAppointments -> emitEffect(TrainerProfileUiEffect.NavigateToAppointments)
        }
    }

    fun loadData(trainerId: Int? = null) {
        val isVisiting = trainerId != null
        val loadedTrainerId = trainerId ?: (userManager.account.value as? TrainerAccount)?.trainerId ?: return

        screenModelScope.launch {
            _uiState.update(TrainerProfileUiState.Loading)

            runCatching {
                val profileDeferred = async { trainerRepository.getTrainerProfile(loadedTrainerId).getOrThrow() }
                val lessonsDeferred = async { fetchLessons(loadedTrainerId) }

                _uiState.update(
                    TrainerProfileUiState.Content(
                        destination = ProfileDestination.About,
                        isVisiting = isVisiting,
                        isFollowing = false,
                        profile = profileDeferred.await(),
                        lessons = lessonsDeferred.await()
                    )
                )
            }.onFailure { error ->
                _uiState.update(TrainerProfileUiState.Error(error.message ?: "Error loading profile"))
            }
        }
    }

    fun fetchSpecialities(): LifestyleActionRowViewData {
        val specialtiesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Fonksiyonel Antrenman"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id, "Kuvvet ve Kondisyon"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Beslenme Danışmanlığı"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Atletik Performans Geliştirme")
        )

        return LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.TROPHY.id, // Medal Icon for Specialties
            title = "Uzmanlık Alanları",
            items = specialtiesViewData,
            iconSizePx = 48
        )
    }

    private suspend fun fetchLessons(trainerId: Int): List<LessonSessionItemViewData> {
        return trainerRepository.getUpcomingLessonsByTrainer(trainerId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun updateDestination(destination: ProfileDestination) {
        uiState.value.let { it as? TrainerProfileUiState.Content }?.let {
            _uiState.update(it.copy(destination = destination))
        }
    }

    private fun emitEffect(effect: TrainerProfileUiEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}