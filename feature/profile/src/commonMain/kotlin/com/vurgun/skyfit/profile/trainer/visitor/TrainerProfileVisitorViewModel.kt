package com.vurgun.skyfit.profile.trainer.visitor

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerProfileVisitorUiState {
    data object Loading : TrainerProfileVisitorUiState
    data class Error(val message: String) : TrainerProfileVisitorUiState
    data class Content(
        val profile: TrainerProfile,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false,
        val isFollowedByVisitor: Boolean = false
    ) : TrainerProfileVisitorUiState
}

sealed interface TrainerProfileVisitorAction {
    data object Exit : TrainerProfileVisitorAction
    data object Follow : TrainerProfileVisitorAction
    data object Unfollow : TrainerProfileVisitorAction
    data object NavigateToSchedule : TrainerProfileVisitorAction
    data object NavigateToChat : TrainerProfileVisitorAction
}

sealed interface TrainerProfileVisitorEffect {
    data object NavigateBack : TrainerProfileVisitorEffect
    data object NavigateToChat : TrainerProfileVisitorEffect
    data object NavigateToSchedule : TrainerProfileVisitorEffect
}

class TrainerProfileVisitorViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val trainerRepository: TrainerRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerProfileVisitorUiState>(TrainerProfileVisitorUiState.Loading)
    val uiState: StateFlow<TrainerProfileVisitorUiState> = _uiState

    private val _effect = MutableSharedFlow<TrainerProfileVisitorEffect>()
    val effect: SharedFlow<TrainerProfileVisitorEffect> = _effect

    private val visitor: Account
        get() = userManager.account.value ?: error("Visitor not found")

    private var currentTrainerId: Int? = null

    fun onAction(action: TrainerProfileVisitorAction) {
        when (action) {
            TrainerProfileVisitorAction.Exit -> emitEffect(TrainerProfileVisitorEffect.NavigateBack)
            TrainerProfileVisitorAction.NavigateToSchedule -> emitEffect(TrainerProfileVisitorEffect.NavigateToSchedule)
            TrainerProfileVisitorAction.NavigateToChat -> emitEffect(TrainerProfileVisitorEffect.NavigateToChat)
            TrainerProfileVisitorAction.Follow -> followTrainer()
            TrainerProfileVisitorAction.Unfollow -> unfollowTrainer()
        }
    }

    fun loadProfile(trainerId: Int) {
        currentTrainerId = trainerId
        screenModelScope.launch {
            _uiState.value = TrainerProfileVisitorUiState.Loading

            val profileDeferred = async { trainerRepository.getTrainerProfile(trainerId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(trainerId) }

            try {
                val profile = profileDeferred.await()
                val lessons = lessonsDeferred.await()

                _uiState.value = TrainerProfileVisitorUiState.Content(
                    profile = profile,
                    lessons = lessons,
                )
            } catch (e: Exception) {
                _uiState.value = TrainerProfileVisitorUiState.Error(e.message ?: "Error loading profile")
            }
        }
    }

    fun followTrainer() {
        // TODO: ("Not yet implemented")
    }

    fun unfollowTrainer() {
        // TODO: ("Not yet implemented")
    }

    private suspend fun fetchLessons(trainerId: Int): List<LessonSessionItemViewData> {
        return trainerRepository.getUpcomingLessonsByTrainer(trainerId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun emitEffect(effect: TrainerProfileVisitorEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}