package com.vurgun.skyfit.feature.profile.user.visitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.domain.UserProfile
import com.vurgun.skyfit.data.user.repository.ProfileRepository
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.viewdata.fakePosts
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserProfileVisitorUiState {
    data object Loading : UserProfileVisitorUiState
    data class Error(val message: String) : UserProfileVisitorUiState
    data class Content(
        val profile: UserProfile,
        val appointments: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val exercises: List<LifestyleActionItemViewData> = emptyList(),
        val habits: List<LifestyleActionItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : UserProfileVisitorUiState
}

sealed interface UserProfileVisitorAction {
    data object NavigateBack : UserProfileVisitorAction
    data class TogglePostVisibility(val visible: Boolean) : UserProfileVisitorAction
}

sealed interface UserProfileVisitorEffect {
    data object NavigateBack : UserProfileVisitorEffect
}

class UserProfileVisitorViewModel(
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserProfileVisitorUiState>(UserProfileVisitorUiState.Loading)
    val uiState: StateFlow<UserProfileVisitorUiState> = _uiState

    private val _effect = MutableSharedFlow<UserProfileVisitorEffect>()
    val effect: SharedFlow<UserProfileVisitorEffect> = _effect

    fun onAction(action: UserProfileVisitorAction) {
        when (action) {
            UserProfileVisitorAction.NavigateBack -> emitEffect(UserProfileVisitorEffect.NavigateBack)
            is UserProfileVisitorAction.TogglePostVisibility -> togglePostVisibility(action.visible)
        }
    }

    fun loadProfile(normalUserId: Int) {
        viewModelScope.launch {
            _uiState.value = UserProfileVisitorUiState.Loading
            val profileDeferred = async { profileRepository.getUserProfile(normalUserId).getOrThrow() }
            val appointmentsDeferred = async { fetchAppointments(normalUserId) }

            try {
                val profile = profileDeferred.await()
                val appointments = appointmentsDeferred.await()

                _uiState.value = UserProfileVisitorUiState.Content(
                    profile = profile,
                    appointments = appointments,
                    posts = fakePosts
                )
            } catch (e: Exception) {
                _uiState.value = UserProfileVisitorUiState.Error(e.message ?: "Profil yüklenemedi.")
            }
        }
    }

    private fun togglePostVisibility(visible: Boolean) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UserProfileVisitorUiState.Content) {
                _uiState.value = currentState.copy(postsVisible = visible)
            }
        }
    }

    private fun emitEffect(effect: UserProfileVisitorEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private suspend fun fetchAppointments(normalUserId: Int): List<LessonSessionItemViewData> {
        return courseRepository.getUpcomingAppointmentsByUser(normalUserId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }
}