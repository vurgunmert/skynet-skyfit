package com.vurgun.skyfit.feature.dashboard.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.CharacterType
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.UserDetail
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.feature.dashboard.component.HomeAppointmentItemViewData
import com.vurgun.skyfit.feature.dashboard.home.UserHomeEffect.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface UserHomeUiState {
    data object Loading : UserHomeUiState
    data class Error(val message: String?) : UserHomeUiState
    data class Content(
        val profile: UserProfile,
        val memberFacility: FacilityProfile? = null,
        val characterType: CharacterType,
        val appointments: List<HomeAppointmentItemViewData> = emptyList()
    ) : UserHomeUiState
}

sealed class UserHomeAction {
    data class OnClickFacility(val id: Int) : UserHomeAction()
    data object OnClickNotifications : UserHomeAction()
    data object OnClickConversations : UserHomeAction()
    data object OnClickAppointments : UserHomeAction()
}

sealed class UserHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : UserHomeEffect()
    data object NavigateToNotifications : UserHomeEffect()
    data object NavigateToConversations : UserHomeEffect()
    data object NavigateToAppointments : UserHomeEffect()
}

class UserHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserHomeUiState>(UserHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserHomeEffect>()
    val effect: SharedFlow<UserHomeEffect> = _effect

    fun onAction(action: UserHomeAction) {
        when (action) {
            is UserHomeAction.OnClickFacility ->
                emitEffect(NavigateToVisitFacility(facilityId = action.id))

            UserHomeAction.OnClickAppointments ->
                emitEffect(NavigateToAppointments)

            UserHomeAction.OnClickConversations ->
                emitEffect(NavigateToConversations)

            UserHomeAction.OnClickNotifications ->
                emitEffect(NavigateToNotifications)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val userDetail = (userManager.user.value as UserDetail)

                val userProfile = profileRepository.getUserProfile(userDetail.normalUserId).getOrThrow()

                val facilityProfile = userProfile.memberGymId?.let { gymId ->
                    profileRepository.getFacilityProfile(gymId).getOrNull()
                }

                val appointments = courseRepository.getUpcomingAppointmentsByUser(userDetail.normalUserId)
                    .getOrDefault(emptyList())
                    .map {
                        HomeAppointmentItemViewData(
                            it.lessonId,
                            it.iconId,
                            it.title,
                            it.startTime.toString(),
                            it.facilityName
                        )
                    }

                _uiState.update(
                    UserHomeUiState.Content(
                        profile = userProfile,
                        memberFacility = facilityProfile,
                        characterType = userDetail.characterType,
                        appointments = appointments
                    )
                )
            }.onFailure {
                _uiState.update(UserHomeUiState.Error(it.message))
            }
        }
    }

    private fun emitEffect(effect: UserHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}