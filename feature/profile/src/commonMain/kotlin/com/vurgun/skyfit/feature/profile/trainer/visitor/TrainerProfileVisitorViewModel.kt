package com.vurgun.skyfit.feature.profile.trainer.visitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.repository.ProfileRepository
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

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
    data class ChangeDate(val date: LocalDate) : TrainerProfileVisitorAction
    data object NavigateToSchedule : TrainerProfileVisitorAction
    data object NavigateToChat : TrainerProfileVisitorAction
}

sealed interface TrainerProfileVisitorEffect {
    data object NavigateBack : TrainerProfileVisitorEffect
    data object NavigateToChat : TrainerProfileVisitorEffect
    data object NavigateToSchedule : TrainerProfileVisitorEffect
}

class TrainerProfileVisitorViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TrainerProfileVisitorUiState>(TrainerProfileVisitorUiState.Loading)
    val uiState: StateFlow<TrainerProfileVisitorUiState> = _uiState

    private val _effect = MutableSharedFlow<TrainerProfileVisitorEffect>()
    val effect: SharedFlow<TrainerProfileVisitorEffect> = _effect

    private val visitor: BaseUserDetail
        get() = userManager.user.value ?: error("Visitor not found")

    private var currentTrainerId: Int? = null

    fun onAction(action: TrainerProfileVisitorAction) {
        when (action) {
            is TrainerProfileVisitorAction.ChangeDate -> updateLessons(action.date)
            TrainerProfileVisitorAction.Exit -> emitEffect(TrainerProfileVisitorEffect.NavigateBack)
            TrainerProfileVisitorAction.NavigateToSchedule -> emitEffect(TrainerProfileVisitorEffect.NavigateToSchedule)
            TrainerProfileVisitorAction.NavigateToChat -> emitEffect(TrainerProfileVisitorEffect.NavigateToChat)
            TrainerProfileVisitorAction.Follow -> followTrainer()
            TrainerProfileVisitorAction.Unfollow -> unfollowTrainer()
        }
    }

    fun loadProfile(trainerId: Int) {
        currentTrainerId = trainerId
        viewModelScope.launch {
            _uiState.value = TrainerProfileVisitorUiState.Loading

            val profileDeferred = async { profileRepository.getTrainerProfile(trainerId).getOrThrow() }
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

    fun updateLessons(date: LocalDate = LocalDate.now()) {
        val id = currentTrainerId ?: return
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is TrainerProfileVisitorUiState.Content) {
                try {
                    val newLessons = fetchLessons(id, date)
                    _uiState.value = currentState.copy(lessons = newLessons)
                } catch (e: Exception) {
                    _uiState.value = TrainerProfileVisitorUiState.Error("Failed to update lessons: ${e.message}")
                }
            }
        }
    }

    fun followTrainer() {
        // TODO: ("Not yet implemented")
    }

    fun unfollowTrainer() {
        // TODO: ("Not yet implemented")
    }

    private suspend fun fetchLessons(facilityId: Int, date: LocalDate = LocalDate.now()): List<LessonSessionItemViewData> {
        return courseRepository.getLessonsByTrainer(facilityId, date, date)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun emitEffect(effect: TrainerProfileVisitorEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}