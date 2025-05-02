package com.vurgun.skyfit.feature.profile.facility.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface FacilityProfileOwnerUiState {
    data object Loading : FacilityProfileOwnerUiState
    data class Error(val message: String) : FacilityProfileOwnerUiState
    data class Content(
        val profile: FacilityProfile,
        val gallery: PhotoGalleryStackViewData? = null,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val trainers: List<FacilityTrainerProfile> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : FacilityProfileOwnerUiState
}

sealed interface FacilityProfileOwnerAction {
    data class TogglePostVisibility(val visible: Boolean) : FacilityProfileOwnerAction
    data object NavigateToGallery : FacilityProfileOwnerAction
    data object NavigateToTrainers : FacilityProfileOwnerAction
    data object NavigateToSettings : FacilityProfileOwnerAction
    data object NavigateToLessonListing : FacilityProfileOwnerAction
    data object NavigateToCreatePost : FacilityProfileOwnerAction
    data object NavigateBack : FacilityProfileOwnerAction
}

sealed interface FacilityProfileOwnerEffect {
    data object NavigateBack : FacilityProfileOwnerEffect
    data object NavigateToLessonListing : FacilityProfileOwnerEffect
    data object NavigateToSettings : FacilityProfileOwnerEffect
    data object NavigateToTrainers : FacilityProfileOwnerEffect
    data object NavigateToGallery : FacilityProfileOwnerEffect
    data object NavigateToCreatePost : FacilityProfileOwnerEffect
}

class FacilityProfileOwnerViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityProfileOwnerUiState>(FacilityProfileOwnerUiState.Loading)
    val uiState: StateFlow<FacilityProfileOwnerUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityProfileOwnerEffect>()
    val effect: SharedFlow<FacilityProfileOwnerEffect> = _effect

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    init {
        loadProfile(facilityId = facilityUser.gymId)
    }

    fun onAction(action: FacilityProfileOwnerAction) {
        when (action) {
            is FacilityProfileOwnerAction.TogglePostVisibility -> togglePostVisibility(action.visible)
            FacilityProfileOwnerAction.NavigateBack -> emitEffect(FacilityProfileOwnerEffect.NavigateBack)
            FacilityProfileOwnerAction.NavigateToGallery -> emitEffect(FacilityProfileOwnerEffect.NavigateToGallery)
            FacilityProfileOwnerAction.NavigateToLessonListing -> emitEffect(FacilityProfileOwnerEffect.NavigateToLessonListing)
            FacilityProfileOwnerAction.NavigateToSettings -> emitEffect(FacilityProfileOwnerEffect.NavigateToSettings)
            FacilityProfileOwnerAction.NavigateToTrainers -> emitEffect(FacilityProfileOwnerEffect.NavigateToTrainers)
            FacilityProfileOwnerAction.NavigateToCreatePost -> emitEffect(FacilityProfileOwnerEffect.NavigateToCreatePost)
        }
    }

    fun loadProfile(facilityId: Int) {
        screenModelScope.launch {
            _uiState.value = FacilityProfileOwnerUiState.Loading

            val profileDeferred = async { profileRepository.getFacilityProfile(facilityId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(facilityId) }
            val trainersDeferred = async { fetchTrainers(facilityId) }

            try {
                _uiState.value = FacilityProfileOwnerUiState.Content(
                    profile = profileDeferred.await(),
                    lessons = lessonsDeferred.await(),
                    trainers = trainersDeferred.await()
                )
            } catch (e: Exception) {
                _uiState.value = FacilityProfileOwnerUiState.Error(e.message ?: "Error loading profile")
            }
        }
    }

    private fun togglePostVisibility(visible: Boolean) {
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is FacilityProfileOwnerUiState.Content) {
                _uiState.value = currentState.copy(postsVisible = visible)
            }
        }
    }

    private fun emitEffect(effect: FacilityProfileOwnerEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
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

    private suspend fun fetchLessons(facilityId: Int): List<LessonSessionItemViewData> {
        return courseRepository.getUpcomingLessonsByFacility(facilityId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private suspend fun fetchTrainers(facilityId: Int): List<FacilityTrainerProfile> {
        return profileRepository.getFacilityTrainerProfiles(facilityId).getOrDefault(emptyList())
    }
}