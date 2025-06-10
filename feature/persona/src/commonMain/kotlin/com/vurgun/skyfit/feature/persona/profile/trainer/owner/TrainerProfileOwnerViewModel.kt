package com.vurgun.skyfit.feature.persona.profile.trainer.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.persona.social.SocialPostItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerProfileOwnerUiState {
    data object Loading : TrainerProfileOwnerUiState
    data class Error(val message: String) : TrainerProfileOwnerUiState
    data class Content(
        val profile: TrainerProfile,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val gallery: PhotoGalleryStackViewData? = null,
        val specialties: LifestyleActionRowViewData? = null,
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : TrainerProfileOwnerUiState
}

sealed interface TrainerProfileOwnerAction {
    data class TogglePostVisibility(val visible: Boolean) : TrainerProfileOwnerAction
    data object NavigateToSettings : TrainerProfileOwnerAction
    data object NavigateToCreatePost : TrainerProfileOwnerAction
    data object NavigateToAppointments : TrainerProfileOwnerAction
}

sealed interface TrainerProfileOwnerEffect {
    data object NavigateToSettings : TrainerProfileOwnerEffect
    data object NavigateToCreatePost : TrainerProfileOwnerEffect
    data object NavigateToAppointments : TrainerProfileOwnerEffect
}

class TrainerProfileOwnerViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val trainerRepository: TrainerRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerProfileOwnerUiState>(TrainerProfileOwnerUiState.Loading)
    val uiState: StateFlow<TrainerProfileOwnerUiState> = _uiState

    private val _effect = SingleSharedFlow<TrainerProfileOwnerEffect>()
    val effect: SharedFlow<TrainerProfileOwnerEffect> = _effect

    private val trainerUser: TrainerAccount
        get() = userManager.user.value as? TrainerAccount
            ?: error("User is not a Trainer")

    fun onAction(action: TrainerProfileOwnerAction) {
        when (action) {
            is TrainerProfileOwnerAction.TogglePostVisibility -> togglePostVisibility(action.visible)
            is TrainerProfileOwnerAction.NavigateToCreatePost -> emitEffect(TrainerProfileOwnerEffect.NavigateToCreatePost)
            is TrainerProfileOwnerAction.NavigateToSettings -> emitEffect(TrainerProfileOwnerEffect.NavigateToSettings)
            is TrainerProfileOwnerAction.NavigateToAppointments -> emitEffect(TrainerProfileOwnerEffect.NavigateToAppointments)
        }
    }

    fun loadProfile() {
        screenModelScope.launch {
            _uiState.value = TrainerProfileOwnerUiState.Loading

            val profileDeferred = async { trainerRepository.getTrainerProfile(trainerUser.trainerId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(trainerUser.trainerId) }

            try {
                val profile = profileDeferred.await()
                val lessons = lessonsDeferred.await()

                _uiState.value = TrainerProfileOwnerUiState.Content(
                    profile = profile,
                    lessons = lessons
                )
            } catch (e: Exception) {
                _uiState.value = TrainerProfileOwnerUiState.Error(e.message ?: "Error loading profile")
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

    private fun togglePostVisibility(visible: Boolean) {
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is TrainerProfileOwnerUiState.Content) {
                _uiState.value = currentState.copy(postsVisible = visible)
            }
        }
    }

    private fun emitEffect(effect: TrainerProfileOwnerEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}