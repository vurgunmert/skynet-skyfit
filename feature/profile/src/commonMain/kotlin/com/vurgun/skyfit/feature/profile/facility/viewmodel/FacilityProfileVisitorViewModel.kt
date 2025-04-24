package com.vurgun.skyfit.feature.profile.facility.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.repository.ProfileRepository
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.feature.profile.components.viewdata.TrainerProfileCardItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface FacilityProfileVisitorUiState {

    data object Loading : FacilityProfileVisitorUiState
    data class Error(val message: String) : FacilityProfileVisitorUiState
    data class Content(
        val profile: FacilityProfile,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val trainers: List<TrainerProfileCardItemViewData> = emptyList()
    ) : FacilityProfileVisitorUiState
}

sealed interface FacilityProfileVisitorAction {
    data object Exit : FacilityProfileVisitorAction
    data object FollowFacility : FacilityProfileVisitorAction
    data object UnfollowFacility : FacilityProfileVisitorAction
    data class ChangeDate(val date: LocalDate) : FacilityProfileVisitorAction
    data class NavigateToTrainer(val trainerId: Int) : FacilityProfileVisitorAction
    data object NavigateToCalendar : FacilityProfileVisitorAction
    data object NavigateToChat : FacilityProfileVisitorAction
}

sealed interface FacilityProfileVisitorEffect {
    data object NavigateBack : FacilityProfileVisitorEffect
    data class NavigateToTrainer(val trainerId: Int) : FacilityProfileVisitorEffect
    data object NavigateToChat : FacilityProfileVisitorEffect
    data object NavigateToCalendar : FacilityProfileVisitorEffect
}

class FacilityProfileVisitorViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val lessonSessionItemViewDataMapper: LessonSessionItemViewDataMapper,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FacilityProfileVisitorUiState>(FacilityProfileVisitorUiState.Loading)
    val uiState: StateFlow<FacilityProfileVisitorUiState> = _uiState

    private val _effect = MutableSharedFlow<FacilityProfileVisitorEffect>()
    val effect: SharedFlow<FacilityProfileVisitorEffect> = _effect

    private val visitor: BaseUserDetail
        get() = userManager.user.value ?: error("Visitor not found")

    private var currentFacilityId: Int? = null


    fun onAction(action: FacilityProfileVisitorAction) {
        when (action) {
            is FacilityProfileVisitorAction.FollowFacility -> followFacility()
            is FacilityProfileVisitorAction.UnfollowFacility -> unfollowFacility()
            is FacilityProfileVisitorAction.ChangeDate -> updateLessons(action.date)
            is FacilityProfileVisitorAction.NavigateToTrainer -> emitEffect(FacilityProfileVisitorEffect.NavigateToTrainer(action.trainerId))
            is FacilityProfileVisitorAction.Exit -> emitEffect(FacilityProfileVisitorEffect.NavigateBack)
            is FacilityProfileVisitorAction.NavigateToCalendar -> emitEffect(FacilityProfileVisitorEffect.NavigateToCalendar)
            is FacilityProfileVisitorAction.NavigateToChat -> emitEffect(FacilityProfileVisitorEffect.NavigateToChat)
        }
    }

    private fun emitEffect(effect: FacilityProfileVisitorEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun loadProfile(facilityId: Int) {
        currentFacilityId = facilityId
        viewModelScope.launch {
            _uiState.value = FacilityProfileVisitorUiState.Loading

            val profileDeferred = async { profileRepository.getFacilityProfile(facilityId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(facilityId) }

            try {
                val profile = profileDeferred.await()
                val lessons = lessonsDeferred.await()

                _uiState.value = FacilityProfileVisitorUiState.Content(
                    profile = profile,
                    lessons = lessons,
                    trainers = emptyList() // TODO: Fetch trainers if needed
                )
            } catch (e: Exception) {
                _uiState.value = FacilityProfileVisitorUiState.Error(e.message ?: "Error loading profile")
            }
        }
    }

    fun updateLessons(date: LocalDate = LocalDate.now()) {
        val id = currentFacilityId ?: return
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is FacilityProfileVisitorUiState.Content) {
                try {
                    val newLessons = fetchLessons(id, date)
                    _uiState.value = currentState.copy(lessons = newLessons)
                } catch (e: Exception) {
                    _uiState.value = FacilityProfileVisitorUiState.Error("Failed to update lessons: ${e.message}")
                }
            }
        }
    }

    private suspend fun fetchLessons(facilityId: Int, date: LocalDate = LocalDate.now()): List<LessonSessionItemViewData> {
        return courseRepository.getLessonsByFacility(facilityId, date, date)
            .map { it.map(lessonSessionItemViewDataMapper::map) }
            .getOrDefault(emptyList())
    }

    fun followFacility() {
        // TODO: ("Not yet implemented")
    }

    fun unfollowFacility() {
        // TODO: ("Not yet implemented")
    }
}