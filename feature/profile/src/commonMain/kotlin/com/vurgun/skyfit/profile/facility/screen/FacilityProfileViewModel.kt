package com.vurgun.skyfit.profile.facility.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.profile.facility.screen.FacilityProfileUiEffect.*
import com.vurgun.skyfit.profile.model.ProfileDestination
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface FacilityProfileUiState {
    data object Loading : FacilityProfileUiState
    data class Error(val message: String) : FacilityProfileUiState
    data class Content(
        val isVisiting: Boolean = false,
        val isMemberVisiting: Boolean = false,
        val isVisitorFollowing: Boolean = false,
        val destination: ProfileDestination = ProfileDestination.About,
        val profile: FacilityProfile,
        val gallery: PhotoGalleryStackViewData? = null,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val trainers: List<FacilityTrainerProfile> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : FacilityProfileUiState
}

sealed interface FacilityProfileUiAction {
    data object NavigateToGallery : FacilityProfileUiAction
    data object NavigateToTrainers : FacilityProfileUiAction
    data object OnClickSettings : FacilityProfileUiAction
    data object NavigateToLessonListing : FacilityProfileUiAction
    data object OnClickNewPost : FacilityProfileUiAction
    data object NavigateBack : FacilityProfileUiAction
    data object OnClickBookAppointment : FacilityProfileUiAction
    data object OnClickSendMessage : FacilityProfileUiAction
    data object OnClickShowSchedule : FacilityProfileUiAction
    data object OnToggleFollow : FacilityProfileUiAction
    data object OnClickAllLessons : FacilityProfileUiAction
    data class ChangeDate(val date: LocalDate) : FacilityProfileUiAction
    data class OnDestinationChanged(val destination: ProfileDestination) : FacilityProfileUiAction
}

sealed interface FacilityProfileUiEffect {
    data object NavigateBack : FacilityProfileUiEffect
    data object NavigateToLessonListing : FacilityProfileUiEffect
    data object NavigateToSettings : FacilityProfileUiEffect
    data object NavigateToTrainers : FacilityProfileUiEffect
    data object NavigateToGallery : FacilityProfileUiEffect
    data object NavigateToCreatePost : FacilityProfileUiEffect
    data class NavigateToVisitTrainer(val trainerId: Int) : FacilityProfileUiEffect
    data class NavigateToFacilitySchedule(val facilityId: Int) : FacilityProfileUiEffect
    data class NavigateToFacilityChat(val facilityId: Int) : FacilityProfileUiEffect
}

class FacilityProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityProfileUiState>(FacilityProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityProfileUiEffect>()
    val effect: SharedFlow<FacilityProfileUiEffect> = _effect

    private var activeFacilityId: Int = 0

    fun onAction(action: FacilityProfileUiAction) {
        when (action) {
            FacilityProfileUiAction.NavigateBack -> emitEffect(NavigateBack)
            FacilityProfileUiAction.NavigateToGallery -> emitEffect(NavigateToGallery)
            FacilityProfileUiAction.NavigateToLessonListing -> emitEffect(NavigateToLessonListing)
            FacilityProfileUiAction.OnClickSettings -> emitEffect(NavigateToSettings)
            FacilityProfileUiAction.NavigateToTrainers -> emitEffect(NavigateToTrainers)
            FacilityProfileUiAction.OnClickNewPost -> emitEffect(NavigateToCreatePost)
            is FacilityProfileUiAction.OnDestinationChanged -> {
                val currentState = (uiState.value as? FacilityProfileUiState.Content) ?: return
                _uiState.update(currentState.copy(destination = action.destination))
            }

            is FacilityProfileUiAction.ChangeDate -> updateLessons(action.date)
            FacilityProfileUiAction.OnClickBookAppointment ->
                emitEffect(NavigateToFacilitySchedule(activeFacilityId))

            FacilityProfileUiAction.OnClickSendMessage ->
                emitEffect(NavigateToFacilityChat(activeFacilityId))

            FacilityProfileUiAction.OnClickAllLessons ->{
                val isVisiting = (uiState.value as? FacilityProfileUiState.Content)?.isVisiting ?: return
                if (isVisiting) {
                    emitEffect(NavigateToFacilitySchedule(activeFacilityId))
                } else {
                    emitEffect(NavigateToLessonListing)
                }
            }
            FacilityProfileUiAction.OnClickShowSchedule -> {
                emitEffect(NavigateToFacilitySchedule(activeFacilityId))
            }

            FacilityProfileUiAction.OnToggleFollow -> revertFollowState()
        }
    }

    fun loadData(facilityId: Int? = null) {
        _uiState.update(FacilityProfileUiState.Loading)

        val isVisiting = facilityId != null
        activeFacilityId = facilityId ?: (userManager.account.value as? FacilityAccount)?.gymId ?: return

        screenModelScope.launch {
            runCatching {

                val profileDeferred = async { facilityRepository.getFacilityProfile(activeFacilityId).getOrThrow() }
                val upcomingLessonsDeferred = async { fetchUpcomingLessons(activeFacilityId) }
                val trainersDeferred = async { fetchTrainers(activeFacilityId) }

                _uiState.update(
                    FacilityProfileUiState.Content(
                        isVisiting = isVisiting,
                        isMemberVisiting = isVisiting,
                        profile = profileDeferred.await(),
                        lessons = upcomingLessonsDeferred.await(),
                        trainers = trainersDeferred.await()
                    )
                )

            }.onFailure { error ->
                _uiState.update(FacilityProfileUiState.Error(error.message ?: "Error loading profile"))
            }
        }
    }

    fun refreshData() {
        loadData(activeFacilityId)
    }

    private fun revertFollowState() {
        val currentState = (uiState.value as? FacilityProfileUiState.Content) ?: return
        val isFollowing = currentState.isVisitorFollowing
        screenModelScope.launch {
            val newIsFollowing = !isFollowing
            val newContent = currentState.copy(isVisitorFollowing = newIsFollowing)
            _uiState.update(newContent)
            if (newIsFollowing) {}
        }
    }

    private fun emitEffect(effect: FacilityProfileUiEffect) {
        _effect.emitIn(screenModelScope, effect)
    }

    private suspend fun fetchGallery(): PhotoGalleryStackViewData {
        return PhotoGalleryStackViewData(
            title = "Salonu Keşfet",
            message = "8 fotoğraf, 1 video",
            imageUrls = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
                "https://ik.imagekit.io/skynet2skyfit/fake_facility_gym.png?updatedAt=1739637015082",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s"
            )
        )
    }

    private suspend fun fetchUpcomingLessons(facilityId: Int): List<LessonSessionItemViewData> {
        return facilityRepository.getUpcomingLessonsByFacility(facilityId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private suspend fun fetchLessons(
        facilityId: Int,
        date: LocalDate = LocalDate.now()
    ): List<LessonSessionItemViewData> {
        return facilityRepository.getAllLessonsByFacility(facilityId, date, null)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private suspend fun fetchTrainers(facilityId: Int): List<FacilityTrainerProfile> {
        return facilityRepository.getFacilityTrainerProfiles(facilityId).getOrDefault(emptyList())
    }

    private fun updateLessons(date: LocalDate = LocalDate.now()) {
        val currentState = uiState.value as? FacilityProfileUiState.Content ?: return
        screenModelScope.launch {
            runCatching {
                val newLessons = fetchLessons(activeFacilityId, date)
                _uiState.update(currentState.copy(lessons = newLessons))
            }.onFailure { error ->
                _uiState.update(FacilityProfileUiState.Error("Failed to update lessons: ${error.message}"))
            }
        }
    }
}