package com.vurgun.skyfit.profile.facility.visitor

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.feature.persona.social.SocialPostItemViewData
import kotlinx.coroutines.async
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
        val trainers: List<FacilityTrainerProfile> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false,
        val isFollowedByVisitor: Boolean = false
    ) : FacilityProfileVisitorUiState
}

sealed interface FacilityProfileVisitorAction {
    data object NavigateToBack : FacilityProfileVisitorAction
    data object Follow : FacilityProfileVisitorAction
    data object Unfollow : FacilityProfileVisitorAction
    data class ChangeDate(val date: LocalDate) : FacilityProfileVisitorAction
    data class NavigateToTrainer(val trainerId: Int) : FacilityProfileVisitorAction
    data object NavigateToCalendar : FacilityProfileVisitorAction
    data object NavigateToChat : FacilityProfileVisitorAction
    data class TogglePostVisibility(val visible: Boolean) : FacilityProfileVisitorAction
}

sealed interface FacilityProfileVisitorEffect {
    data object NavigateToBack : FacilityProfileVisitorEffect
    data class NavigateToTrainer(val trainerId: Int) : FacilityProfileVisitorEffect
    data class NavigateToChat(val visitorId: Int) : FacilityProfileVisitorEffect
    data class NavigateToSchedule(val facilityId: Int) : FacilityProfileVisitorEffect
}

class FacilityProfileVisitorViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonSessionItemViewDataMapper: LessonSessionItemViewDataMapper,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityProfileVisitorUiState>(FacilityProfileVisitorUiState.Loading)
    val uiState: StateFlow<FacilityProfileVisitorUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityProfileVisitorEffect>()
    val effect: SharedFlow<FacilityProfileVisitorEffect> = _effect

    private val visitor: Account
        get() = userManager.user.value ?: error("Visitor not found")

    private var currentFacilityId: Int? = null

    fun onAction(action: FacilityProfileVisitorAction) {
        when (action) {
            is FacilityProfileVisitorAction.Follow -> followFacility()
            is FacilityProfileVisitorAction.Unfollow -> unfollowFacility()
            is FacilityProfileVisitorAction.ChangeDate -> updateLessons(action.date)
            is FacilityProfileVisitorAction.NavigateToTrainer -> emitEffect(
                FacilityProfileVisitorEffect.NavigateToTrainer(
                    action.trainerId
                )
            )

            is FacilityProfileVisitorAction.NavigateToBack -> emitEffect(FacilityProfileVisitorEffect.NavigateToBack)
            is FacilityProfileVisitorAction.NavigateToCalendar -> emitEffect(
                FacilityProfileVisitorEffect.NavigateToSchedule(
                    currentFacilityId!!
                )
            )

            is FacilityProfileVisitorAction.NavigateToChat -> emitEffect(
                FacilityProfileVisitorEffect.NavigateToChat(
                    visitor.userId
                )
            )

            is FacilityProfileVisitorAction.TogglePostVisibility -> togglePostVisibility(action.visible)
        }
    }

    fun loadProfile(facilityId: Int) {
        currentFacilityId = facilityId
        screenModelScope.launch {
            _uiState.value = FacilityProfileVisitorUiState.Loading

            val profileDeferred = async { facilityRepository.getFacilityProfile(facilityId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(facilityId) }
            val trainersDeferred = async { fetchTrainers(facilityId) }

            try {
                _uiState.value = FacilityProfileVisitorUiState.Content(
                    profile = profileDeferred.await(),
                    lessons = lessonsDeferred.await(),
                    trainers = trainersDeferred.await()
                )
            } catch (e: Exception) {
                _uiState.value = FacilityProfileVisitorUiState.Error(e.message ?: "Error loading profile")
            }
        }
    }

    private fun updateLessons(date: LocalDate = LocalDate.now()) {
        val id = currentFacilityId ?: return
        screenModelScope.launch {
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

    private fun followFacility() {
        // TODO: ("Not yet implemented")
    }

    private fun unfollowFacility() {
        // TODO: ("Not yet implemented")
    }

    private fun togglePostVisibility(visible: Boolean) {
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is FacilityProfileVisitorUiState.Content) {
                _uiState.value = currentState.copy(postsVisible = visible)
            }
        }
    }

    private suspend fun fetchTrainers(facilityId: Int): List<FacilityTrainerProfile> {
        return facilityRepository.getFacilityTrainerProfiles(facilityId).getOrDefault(emptyList())
    }

    private suspend fun fetchLessons(
        facilityId: Int,
        date: LocalDate = LocalDate.now()
    ): List<LessonSessionItemViewData> {
        return facilityRepository.getActiveLessonsByFacility(facilityId, date, date)
            .map { it.map(lessonSessionItemViewDataMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun emitEffect(effect: FacilityProfileVisitorEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}