package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.statistics.front.UserStatistics
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.data.v1.domain.workout.model.ExerciseProfile
import com.vurgun.skyfit.feature.home.model.UserHomeEffect.*
import com.vurgun.skyfit.feature.home.screen.user.UserAppointmentUiData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

data class UserHomeCalendarState(
    val activeCalendarDates: Set<LocalDate> = emptySet()
)

data class UserHomeMembershipState(
    val memberFacility: FacilityProfile,
    val memberDurationDays: Int = 0,
    val requestReceived: Boolean = false,
    val requestSent: Boolean = false,
)

data class UserHomeStatisticsState(
    val statistics: UserStatistics,
)

data class UserHomeDietState(
    val statistics: UserStatistics
)

data class UserHomeFeaturedContentState(
    val featuredTrainers: List<TrainerProfile> = emptyList(),
    val featuredExercises: List<ExerciseProfile> = emptyList(),
)

data class UserHomeAppointmentsState(
    val appointments: List<UserAppointmentUiData> = emptyList(),
)

internal sealed interface UserHomeUiState {
    data object Loading : UserHomeUiState
    data class Error(val message: String?) : UserHomeUiState
    data class Content(
        val account: UserAccount,
        val profile: UserProfile,
        val calendarState: UserHomeCalendarState? = null,
        val membershipState: UserHomeMembershipState? = null,
        val statisticsState: UserHomeStatisticsState? = null,
        val dietState: UserHomeDietState? = null,
        val featuredContentState: UserHomeFeaturedContentState? = null,
        val appointmentsState: UserHomeAppointmentsState? = null,
    ) : UserHomeUiState {
        val characterType = account.characterType
    }
}

sealed class UserHomeAction {
    data class OnClickFacility(val id: Int) : UserHomeAction()
    data object OnClickNotifications : UserHomeAction()
    data object OnClickConversations : UserHomeAction()
    data object OnClickChatBot : UserHomeAction()
    data object OnClickAppointments : UserHomeAction()
    data object OnClickShowCalendar : UserHomeAction()
}

sealed class UserHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : UserHomeEffect()
    data object NavigateToNotifications : UserHomeEffect()
    data object NavigateToChatbot : UserHomeEffect()
    data object NavigateToConversations : UserHomeEffect()
    data object NavigateToAppointments : UserHomeEffect()
    data object NavigateToActivityCalendar : UserHomeEffect()
}

class UserHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserHomeUiState>(UserHomeUiState.Loading)
    internal val uiState = _uiState.asStateFlow()

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

            UserHomeAction.OnClickChatBot ->
                emitEffect(NavigateToChatbot)

            UserHomeAction.OnClickShowCalendar ->
                emitEffect(NavigateToActivityCalendar)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val account = userManager.user.value as? UserAccount
                    ?: error("Invalid user state")

                val userProfileDeferred = async { userRepository.getUserProfile(account.normalUserId).getOrThrow() }
                val calendarEventsDeferred = async { userRepository.getCalendarEvents().getOrDefault(emptyList()) }
                val appointmentsDeferred = async {
                    userRepository.getUpcomingAppointmentsByUser(account.normalUserId, limit = 3)
                        .getOrDefault(emptyList())
                }

                val userProfile = userProfileDeferred.await()
                val calendarEvents = calendarEventsDeferred.await()
                val appointments = appointmentsDeferred.await()

                val calendarState = UserHomeCalendarState(
                    activeCalendarDates = calendarEvents.map { it.startDate }.toSet()
                )

                val membershipState = userProfile.memberGymId?.let { gymId ->
                    facilityRepository.getFacilityProfile(gymId).getOrNull()?.let { facility ->
                        val duration = userProfile.memberGymJoinedAt?.daysUntil(LocalDate.now()) ?: 0
                        UserHomeMembershipState(facility, duration)
                    }
                }

                val statisticsState = UserHomeStatisticsState(statistics = UserStatistics())
                val dietState = UserHomeDietState(statistics = UserStatistics())
                val featuredContentState = UserHomeFeaturedContentState() // placeholder, fetch later?

                val appointmentState = UserHomeAppointmentsState(
                    appointments.map {
                        UserAppointmentUiData(
                            lessonId = it.lessonId,
                            iconId = it.iconId,
                            title = it.title,
                            time = it.startTime.toString(),
                            location = it.facilityName
                        )
                    }
                )

                _uiState.update(
                    UserHomeUiState.Content(
                        account = account,
                        profile = userProfile,
                        calendarState = calendarState,
                        membershipState = membershipState,
                        statisticsState = statisticsState,
                        dietState = dietState,
                        featuredContentState = featuredContentState,
                        appointmentsState = appointmentState
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