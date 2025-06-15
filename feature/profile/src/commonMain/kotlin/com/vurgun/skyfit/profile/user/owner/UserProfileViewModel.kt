package com.vurgun.skyfit.profile.user.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.persona.social.SocialPostItemViewData
import com.vurgun.skyfit.profile.model.ProfileNavigationRoute
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserProfileUiState {
    data object Loading : UserProfileUiState
    data class Error(val message: String) : UserProfileUiState
    data class Content(
        val route: ProfileNavigationRoute,
        val isVisiting: Boolean,
        val profile: UserProfile,
        val memberFacilityProfile: FacilityProfile? = null,
        val appointments: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val exercises: List<LifestyleActionItemViewData> = emptyList(),
        val habits: List<LifestyleActionItemViewData> = emptyList(),
    ) : UserProfileUiState
}

sealed interface UserProfileAction {
    data object ClickBack : UserProfileAction
    data object ClickAppointments : UserProfileAction
    data object ClickSettings : UserProfileAction
    data object ClickCreatePost : UserProfileAction
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileAction
    data class TogglePostVisibility(val visible: Boolean) : UserProfileAction
    data class ChangeRoute(val route: ProfileNavigationRoute): UserProfileAction
}

sealed interface UserProfileEffect {
    data object NavigateBack : UserProfileEffect
    data object NavigateToAppointments : UserProfileEffect
    data object NavigateToSettings : UserProfileEffect
    data object NavigateToCreatePost : UserProfileEffect
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileEffect
}

class UserProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository,
    private val facilityRepository: FacilityRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper,
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val uiState: StateFlow<UserProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<UserProfileEffect>()
    val effect: SharedFlow<UserProfileEffect> = _effect

    fun onAction(action: UserProfileAction) {
        when (action) {
            UserProfileAction.ClickBack ->
                emitEffect(UserProfileEffect.NavigateBack)

            UserProfileAction.ClickAppointments ->
                emitEffect(UserProfileEffect.NavigateToAppointments)

            UserProfileAction.ClickSettings ->
                emitEffect(UserProfileEffect.NavigateToSettings)

            UserProfileAction.ClickCreatePost ->
                emitEffect(UserProfileEffect.NavigateToCreatePost)

            is UserProfileAction.NavigateToVisitFacility ->
                emitEffect(UserProfileEffect.NavigateToVisitFacility(action.gymId))

            is UserProfileAction.TogglePostVisibility -> togglePostVisibility(action.visible)
        }
    }

    fun loadData(userId: Int? = null) {
        val isVisiting = userId != null
        val loadedUserId = userId ?: userManager.user.value?.userId ?: return

        screenModelScope.launch {
            _uiState.value = UserProfileUiState.Loading
            val profileDeferred = async { userRepository.getUserProfile(loadedUserId).getOrThrow() }
            val appointmentsDeferred = async { fetchAppointments(loadedUserId) }

            try {
                val profile = profileDeferred.await()
                val facilityDeferred = profile.memberGymId?.let { gymId ->
                    async { facilityRepository.getFacilityProfile(gymId).getOrNull() }
                }

                val appointments = appointmentsDeferred.await()
                val memberFacilityProfile = facilityDeferred?.await()

                _uiState.value = UserProfileUiState.Content(
                    isVisiting = isVisiting,
                    profile = profile,
                    memberFacilityProfile = memberFacilityProfile,
                    appointments = appointments
                )
            } catch (e: Exception) {
                _uiState.value = UserProfileUiState.Error(e.message ?: "Profil yüklenemedi.")
            }
        }
    }

    private fun togglePostVisibility(visible: Boolean) {
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UserProfileUiState.Content) {
                _uiState.value = currentState.copy()
            }
        }
    }

    private fun emitEffect(effect: UserProfileEffect) {
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
        return userRepository.getUpcomingAppointmentsByUser(normalUserId, 3)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }
}