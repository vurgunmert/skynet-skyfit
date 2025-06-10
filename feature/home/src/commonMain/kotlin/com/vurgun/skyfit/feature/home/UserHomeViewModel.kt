package com.vurgun.skyfit.feature.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.feature.dashboard.component.HomeAppointmentItemViewData
import com.vurgun.skyfit.feature.home.UserHomeEffect.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface UserHomeUiState {
    data object Loading : UserHomeUiState
    data class Error(val message: String?) : UserHomeUiState
    data class Content(
        val profile: UserProfile,
        val memberFacility: FacilityProfile? = null,
        val characterType: CharacterType,
        val appointments: List<HomeAppointmentItemViewData> = emptyList(),
        val activeCalendarDates: Set<LocalDate> = emptySet(),
        val showMembershipRequests: Boolean = false // TODO: Replace with modal
    ) : UserHomeUiState
}

sealed class UserHomeAction {
    data class OnClickFacility(val id: Int) : UserHomeAction()
    data object OnClickNotifications : UserHomeAction()
    data object OnClickConversations : UserHomeAction()
    data object OnClickAppointments : UserHomeAction()
    data object OnClickShowCalendar : UserHomeAction()
    data class OnChangeSelectedDate(val date: LocalDate) : UserHomeAction()
}

sealed class UserHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : UserHomeEffect()
    data object NavigateToNotifications : UserHomeEffect()
    data object NavigateToConversations : UserHomeEffect()
    data object NavigateToAppointments : UserHomeEffect()
    data class NavigateToActivityCalendar(val date: LocalDate) : UserHomeEffect()
}

class UserHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserHomeUiState>(UserHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserHomeEffect>()
    val effect: SharedFlow<UserHomeEffect> = _effect

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

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

            UserHomeAction.OnClickShowCalendar ->
                emitEffect(NavigateToActivityCalendar(_selectedDate.value))

            is UserHomeAction.OnChangeSelectedDate -> {
                _selectedDate.value = action.date
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val userDetail = (userManager.user.value as UserAccount)

                val userProfile = userRepository.getUserProfile(userDetail.normalUserId).getOrThrow()

                val facilityProfile = userProfile.memberGymId?.let { gymId ->
                    facilityRepository.getFacilityProfile(gymId).getOrThrow()
                }

                val appointments = userRepository.getUpcomingAppointmentsByUser(userDetail.normalUserId, limit = 3)
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

                val calendarEvents = userRepository.getCalendarEvents().getOrDefault(emptyList())
                val activeCalendarDates = calendarEvents.map { it.startDate }.toSet()

                _uiState.update(
                    UserHomeUiState.Content(
                        profile = userProfile,
                        memberFacility = facilityProfile,
                        characterType = userDetail.characterType,
                        appointments = appointments,
                        activeCalendarDates = activeCalendarDates
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