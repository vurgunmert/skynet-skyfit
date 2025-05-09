package com.vurgun.skyfit.feature.persona.profile.user.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.UserDetail
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.persona.social.SocialPostItemViewData
import com.vurgun.skyfit.feature.persona.social.fakePosts
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserProfileOwnerUiState {
    data object Loading : UserProfileOwnerUiState
    data class Error(val message: String) : UserProfileOwnerUiState
    data class Content(
        val profile: UserProfile,
        val memberFacilityProfile: FacilityProfile? = null,
        val appointments: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val exercises: List<LifestyleActionItemViewData> = emptyList(),
        val habits: List<LifestyleActionItemViewData> = emptyList(),
        val postsVisible: Boolean = false
    ) : UserProfileOwnerUiState
}

sealed interface UserProfileOwnerAction {
    data object NavigateBack : UserProfileOwnerAction
    data object NavigateToAppointments : UserProfileOwnerAction
    data object NavigateToSettings : UserProfileOwnerAction
    data object NavigateToCreatePost : UserProfileOwnerAction
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileOwnerAction
    data class TogglePostVisibility(val visible: Boolean) : UserProfileOwnerAction
}

sealed interface UserProfileOwnerEffect {
    data object NavigateBack : UserProfileOwnerEffect
    data object NavigateToAppointments : UserProfileOwnerEffect
    data object NavigateToSettings : UserProfileOwnerEffect
    data object NavigateToCreatePost : UserProfileOwnerEffect
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileOwnerEffect
}

class UserProfileOwnerViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper,
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserProfileOwnerUiState>(UserProfileOwnerUiState.Loading)
    val uiState: StateFlow<UserProfileOwnerUiState> = _uiState

    private val _effect = SingleSharedFlow<UserProfileOwnerEffect>()
    val effect: SharedFlow<UserProfileOwnerEffect> = _effect

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    fun onAction(action: UserProfileOwnerAction) {
        when (action) {
            UserProfileOwnerAction.NavigateBack -> emitEffect(UserProfileOwnerEffect.NavigateBack)
            UserProfileOwnerAction.NavigateToAppointments -> emitEffect(UserProfileOwnerEffect.NavigateToAppointments)
            UserProfileOwnerAction.NavigateToSettings -> emitEffect(UserProfileOwnerEffect.NavigateToSettings)
            UserProfileOwnerAction.NavigateToCreatePost -> emitEffect(UserProfileOwnerEffect.NavigateToCreatePost)
            is UserProfileOwnerAction.NavigateToVisitFacility -> emitEffect(UserProfileOwnerEffect.NavigateToVisitFacility(action.gymId))
            is UserProfileOwnerAction.TogglePostVisibility -> togglePostVisibility(action.visible)
        }
    }

    fun loadProfile() {
        screenModelScope.launch {
            _uiState.value = UserProfileOwnerUiState.Loading
            val profileDeferred = async { profileRepository.getUserProfile(user.normalUserId).getOrThrow() }
            val appointmentsDeferred = async { fetchAppointments(user.normalUserId) }

            try {
                val profile = profileDeferred.await()
                val facilityDeferred = profile.memberGymId?.let { gymId ->
                    async { profileRepository.getFacilityProfile(gymId).getOrNull() }
                }

                val appointments = appointmentsDeferred.await()
                val memberFacilityProfile = facilityDeferred?.await()

                _uiState.value = UserProfileOwnerUiState.Content(
                    profile = profile,
                    memberFacilityProfile = memberFacilityProfile,
                    appointments = appointments,
                    posts = fakePosts //TODO: REMOVE!
                )
            } catch (e: Exception) {
                _uiState.value = UserProfileOwnerUiState.Error(e.message ?: "Profil yüklenemedi.")
            }
        }
    }

    private fun togglePostVisibility(visible: Boolean) {
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UserProfileOwnerUiState.Content) {
                _uiState.value = currentState.copy(postsVisible = visible)
            }
        }
    }

    private fun emitEffect(effect: UserProfileOwnerEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }

    private fun fetchPhotoGallery() {
        val photoDiaryViewData = PhotoGalleryStackViewData()
    }

    private fun fetchExerciseHistory() {

        val exercisesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
        )
        val exercisesRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
            title = "Egzersiz Geçmişi",
            items = exercisesViewData
        )
    }

    private fun fetchHabits() {

        val habitsViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
        )
        val habitsRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
            title = "Alışkanlıklar",
            items = habitsViewData
        )
    }

    private suspend fun fetchAppointments(normalUserId: Int): List<LessonSessionItemViewData> {
        return courseRepository.getUpcomingAppointmentsByUser(normalUserId)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }
}