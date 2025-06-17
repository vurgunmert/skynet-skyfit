package com.vurgun.skyfit.profile.user.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.owner.UserProfileEffect.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class UserProfileUiState {
    data object Loading : UserProfileUiState()
    data class Error(val message: String) : UserProfileUiState()
    data class Content(
        val route: ProfileDestination,
        val isVisiting: Boolean,
        val isFollowing: Boolean,
        val profile: UserProfile,
        val memberFacilityProfile: FacilityProfile? = null,
        val appointments: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val exercises: List<LifestyleActionItemViewData> = emptyList(),
        val habits: List<LifestyleActionItemViewData> = emptyList(),
    ) : UserProfileUiState()
}

sealed interface UserProfileAction {
    data object ClickBack : UserProfileAction
    data object ClickAppointments : UserProfileAction
    data object ClickSettings : UserProfileAction
    data object ClickCreatePost : UserProfileAction
    data object OnClickFollow : UserProfileAction
    data object OnClickUnfollow : UserProfileAction
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileAction
    data class ChangeTab(val route: ProfileDestination) : UserProfileAction
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

    private val _uiState = UiStateDelegate<UserProfileUiState>(UserProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserProfileEffect>()
    val effect: SharedFlow<UserProfileEffect> = _effect

    fun onAction(action: UserProfileAction) {
        when (action) {
            UserProfileAction.ClickBack ->
                _effect.emitIn(screenModelScope, NavigateBack)

            UserProfileAction.ClickAppointments ->
                _effect.emitIn(screenModelScope, NavigateToAppointments)

            UserProfileAction.ClickSettings ->
                _effect.emitIn(screenModelScope, NavigateToSettings)

            UserProfileAction.ClickCreatePost ->
                _effect.emitIn(screenModelScope, NavigateToCreatePost)

            is UserProfileAction.NavigateToVisitFacility ->
                _effect.emitIn(screenModelScope, NavigateToVisitFacility(action.gymId))

            is UserProfileAction.ChangeTab -> changeTab(action.route)
            UserProfileAction.OnClickFollow -> TODO()
            UserProfileAction.OnClickUnfollow -> TODO()
        }
    }

    fun loadData(userId: Int? = null) {
        val isVisiting = userId != null
        val loadedUserId = userId ?: userManager.account.value?.userId ?: return

        screenModelScope.launch {
            _uiState.update(UserProfileUiState.Loading)
            val profileDeferred = async { userRepository.getUserProfile(loadedUserId).getOrThrow() }
            val appointmentsDeferred = async { fetchAppointments(loadedUserId) }

            try {
                val profile = profileDeferred.await()
                val facilityDeferred = profile.memberGymId?.let { gymId ->
                    async { facilityRepository.getFacilityProfile(gymId).getOrNull() }
                }

                val appointments = appointmentsDeferred.await()
                val memberFacilityProfile = facilityDeferred?.await()

                _uiState.update(
                    UserProfileUiState.Content(
                        route = ProfileDestination.About,
                        isVisiting = isVisiting,
                        isFollowing = false, //TODO: nul
                        profile = profile,
                        memberFacilityProfile = memberFacilityProfile,
                        appointments = appointments,
                    )
                )
            } catch (e: Exception) {
                _uiState.update(UserProfileUiState.Error(e.message ?: "Profil yüklenemedi."))
            }
        }
    }

    private fun changeTab(route: ProfileDestination) {
        uiState.value
            .let { it as? UserProfileUiState.Content }
            ?.let {
                _uiState.update(it.copy(route = route))
            }
    }

    private suspend fun fetchAppointments(normalUserId: Int): List<LessonSessionItemViewData> {
        return userRepository.getUpcomingAppointmentsByUser(normalUserId, 3)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun fetchPhotoGallery() {
//        val photoDiaryViewData = PhotoGalleryStackViewData()
    }

    private fun fetchExerciseHistory() {

//        val exercisesViewData = listOf(
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
//        )
//        val exercisesRowViewData = LifestyleActionRowViewData(
//            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
//            title = "Egzersiz Geçmişi",
//            items = exercisesViewData
//        )
    }

    private fun fetchHabits() {

//        val habitsViewData = listOf(
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
//        )
//        val habitsRowViewData = LifestyleActionRowViewData(
//            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
//            title = "Alışkanlıklar",
//            items = habitsViewData
//        )
    }
}