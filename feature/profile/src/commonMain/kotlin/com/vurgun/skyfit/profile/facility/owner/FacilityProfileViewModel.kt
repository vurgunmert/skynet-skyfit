package com.vurgun.skyfit.profile.facility.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.profile.model.ProfileDestination
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface FacilityProfileUiState {
    data object Loading : FacilityProfileUiState
    data class Error(val message: String) : FacilityProfileUiState
    data class Content(
        val isVisiting: Boolean = false,
        val destination: ProfileDestination = ProfileDestination.About,
        val profile: FacilityProfile,
        val gallery: PhotoGalleryStackViewData? = null,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val trainers: List<FacilityTrainerProfile> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : FacilityProfileUiState
}

sealed interface FacilityProfileAction {
    data class TogglePostVisibility(val visible: Boolean) : FacilityProfileAction
    data object NavigateToGallery : FacilityProfileAction
    data object NavigateToTrainers : FacilityProfileAction
    data object OnClickSettings : FacilityProfileAction
    data object NavigateToLessonListing : FacilityProfileAction
    data object NavigateToCreatePost : FacilityProfileAction
    data object NavigateBack : FacilityProfileAction
    data class OnDestinationChanged(val destination: ProfileDestination) : FacilityProfileAction
}

sealed interface FacilityProfileOwnerEffect {
    data object NavigateBack : FacilityProfileOwnerEffect
    data object NavigateToLessonListing : FacilityProfileOwnerEffect
    data object NavigateToSettings : FacilityProfileOwnerEffect
    data object NavigateToTrainers : FacilityProfileOwnerEffect
    data object NavigateToGallery : FacilityProfileOwnerEffect
    data object NavigateToCreatePost : FacilityProfileOwnerEffect
    data class NavigateToVisitTrainer(val trainerId: Int) : FacilityProfileOwnerEffect
}

class FacilityProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityProfileUiState>(FacilityProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityProfileOwnerEffect>()
    val effect: SharedFlow<FacilityProfileOwnerEffect> = _effect

    fun onAction(action: FacilityProfileAction) {
        when (action) {
            is FacilityProfileAction.TogglePostVisibility -> togglePostVisibility(action.visible)
            FacilityProfileAction.NavigateBack -> emitEffect(FacilityProfileOwnerEffect.NavigateBack)
            FacilityProfileAction.NavigateToGallery -> emitEffect(FacilityProfileOwnerEffect.NavigateToGallery)
            FacilityProfileAction.NavigateToLessonListing -> emitEffect(FacilityProfileOwnerEffect.NavigateToLessonListing)
            FacilityProfileAction.OnClickSettings -> emitEffect(FacilityProfileOwnerEffect.NavigateToSettings)
            FacilityProfileAction.NavigateToTrainers -> emitEffect(FacilityProfileOwnerEffect.NavigateToTrainers)
            FacilityProfileAction.NavigateToCreatePost -> emitEffect(FacilityProfileOwnerEffect.NavigateToCreatePost)
            is FacilityProfileAction.OnDestinationChanged -> {
                val currentState = (uiState.value as? FacilityProfileUiState.Content) ?: return
                _uiState.update(currentState.copy(destination = action.destination))
            }
        }
    }

    fun loadData(facilityId: Int? = null) {
        val isVisiting = facilityId != null

        screenModelScope.launch {
            _uiState.update(FacilityProfileUiState.Loading)

            runCatching {
                val facilityId = facilityId ?: (userManager.account.value as? FacilityAccount)?.gymId
                ?: error("User is not a Facility")

                val profileDeferred = async { facilityRepository.getFacilityProfile(facilityId).getOrThrow() }
                val lessonsDeferred = async { fetchLessons(facilityId) }
                val trainersDeferred = async { fetchTrainers(facilityId) }

                _uiState.update(
                    FacilityProfileUiState.Content(
                        profile = profileDeferred.await(),
                        lessons = lessonsDeferred.await(),
                        trainers = trainersDeferred.await()
                    )
                )

            }.onFailure { error ->
                _uiState.update(FacilityProfileUiState.Error(error.message ?: "Error loading profile"))
            }
        }
    }

    private fun togglePostVisibility(visible: Boolean) {
        val currentState = (uiState.value as? FacilityProfileUiState.Content) ?: return
        _uiState.update(currentState.copy(postsVisible = visible))
    }

    private fun emitEffect(effect: FacilityProfileOwnerEffect) {
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

    private suspend fun fetchLessons(facilityId: Int): List<LessonSessionItemViewData> {
        return facilityRepository.getUpcomingLessonsByFacility(facilityId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private suspend fun fetchTrainers(facilityId: Int): List<FacilityTrainerProfile> {
        return facilityRepository.getFacilityTrainerProfiles(facilityId).getOrDefault(emptyList())
    }
}