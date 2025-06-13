package com.vurgun.skyfit.core.data.home

import com.vurgun.skyfit.core.data.v1.data.statistics.front.Statistics
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.user.model.Appointment
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import kotlinx.datetime.LocalDate

data class UserHomeCalendarState(
    val activeCalendarDates: Set<LocalDate> = emptySet()
)

data class UserHomeStatisticsState(
    val statistics: Statistics
)

data class UserHomeDietState(
    val statistics: Statistics
)

data class UserHomeFeaturedContentState(
    val featuredTrainers: List<TrainerProfile> = emptyList(),
    val featuredExercises: List<String> = emptyList(),
)

data class HomeAiAssistantState(
    val isMini: Boolean = false,
    val postureEnabled: Boolean = false,
    val nutritionEnabled: Boolean = false,
    val chatRecommendations: List<String> = emptyList(),
)

internal sealed interface UserHomeUiState {
    data object Loading : UserHomeUiState
    data class Error(val message: String?) : UserHomeUiState
    data class Content(
        val account: UserAccount,
        val profile: UserProfile,
        val memberFacility: FacilityProfile? = null,
        val memberDurationDays: Int? = null,
        val characterType: CharacterType,
        val appointments: List<Appointment> = emptyList(),
        val activeCalendarDates: Set<LocalDate> = emptySet(),
        val notificationsEnabled: Boolean = true, // TODO: Remove debug
        val conversationsEnabled: Boolean = true, // TODO: Remove debug
        val requestsEnabled: Boolean = false, // TODO: Remove debug
    ) : UserHomeUiState
}

sealed class UserHomeAction {
    data class OnClickFacility(val id: Int) : UserHomeAction()
    data object OnClickNotifications : UserHomeAction()
    data object OnClickConversations : UserHomeAction()
    data object OnClickChatBot : UserHomeAction()
    data object OnClickAppointments : UserHomeAction()
    data object OnClickShowCalendar : UserHomeAction()
    data class OnChangeSelectedDate(val date: LocalDate) : UserHomeAction()
}

sealed class UserHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : UserHomeEffect()
    data object NavigateToNotifications : UserHomeEffect()
    data object NavigateToChatbot : UserHomeEffect()
    data object NavigateToConversations : UserHomeEffect()
    data object NavigateToAppointments : UserHomeEffect()
    data class NavigateToActivityCalendar(val date: LocalDate) : UserHomeEffect()
}