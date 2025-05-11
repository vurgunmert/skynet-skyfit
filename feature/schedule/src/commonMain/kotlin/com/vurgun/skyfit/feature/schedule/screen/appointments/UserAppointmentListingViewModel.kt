package com.vurgun.skyfit.feature.schedule.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.model.UserDetail
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.schedule.domain.model.Appointment
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingTab.Active
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingTab.Cancelled
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingTab.Completed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed interface UserAppointmentListingAction {
    data object NavigateToBack : UserAppointmentListingAction
    data object ShowFilter : UserAppointmentListingAction
    data class ChangeTab(val tab: UserAppointmentListingTab) : UserAppointmentListingAction
    data class CancelAppointment(val appointment: Appointment) : UserAppointmentListingAction
    data class NavigateToDetail(val lpId: Int) : UserAppointmentListingAction
    data class RemoveTitleFilter(val title: String) : UserAppointmentListingAction
    data class RemoveTimeFilter(val time: LocalTime) : UserAppointmentListingAction
    data class RemoveDateFilter(val date: LocalDate) : UserAppointmentListingAction
    data class RemoveTrainerFilter(val trainer: TrainerProfile) : UserAppointmentListingAction
}

sealed interface UserAppointmentListingEffect {
    data object NavigateToBack : UserAppointmentListingEffect
    data object ShowFilter : UserAppointmentListingEffect
    data class ShowCancelError(val message: String) : UserAppointmentListingEffect
    data class NavigateToDetail(val lpId: Int) : UserAppointmentListingEffect
}

data class UserAppointmentListingFilter(
    val selectedDates: Set<LocalDate> = emptySet(),
    val selectedHours: Set<LocalTime> = emptySet(),
    val selectedTitles: Set<String> = emptySet(),
    val selectedTrainers: Set<TrainerProfile> = emptySet(),
) {
    val hasAny get() = selectedDates.isNotEmpty() || selectedHours.isNotEmpty() || selectedTitles.isNotEmpty() || selectedTrainers.isNotEmpty()
}

sealed class UserAppointmentListingTab(val statusTypes: List<Int>, val label: String) {
    data object Active : UserAppointmentListingTab(listOf(1), "Aktif")
    data object Cancelled : UserAppointmentListingTab(listOf(3, 4, 5), "İptal")
    data object Completed : UserAppointmentListingTab(listOf(6, 7), "Devamsızlık")
}

sealed class UserAppointmentListingUiState {
    data object Loading : UserAppointmentListingUiState()
    data class Error(val message: String) : UserAppointmentListingUiState()
    data class Content(
        val activeTab: UserAppointmentListingTab = Active,
        val appointments: List<Appointment> = emptyList(),
        val filteredAppointments: List<Appointment> = emptyList(),
        val tabTitles: List<String> = emptyList(),
        val currentFilter: UserAppointmentListingFilter = UserAppointmentListingFilter()
    ) : UserAppointmentListingUiState()
}

class UserAppointmentListingViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserAppointmentListingUiState>(UserAppointmentListingUiState.Loading)
    val uiState: StateFlow<UserAppointmentListingUiState> = _uiState

    private val _effect = SingleSharedFlow<UserAppointmentListingEffect>()
    val effect: SharedFlow<UserAppointmentListingEffect> = _effect

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")
    private val allTabs: List<UserAppointmentListingTab> = listOf(Active, Cancelled, Completed)

    init {
        refreshData()
    }

    fun onAction(action: UserAppointmentListingAction) {
        when (action) {
            UserAppointmentListingAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, UserAppointmentListingEffect.NavigateToBack)

            UserAppointmentListingAction.ShowFilter ->
                _effect.emitIn(screenModelScope, UserAppointmentListingEffect.ShowFilter)

            is UserAppointmentListingAction.CancelAppointment ->
                cancelAppointment(action.appointment)

            is UserAppointmentListingAction.NavigateToDetail ->
                _effect.emitIn(screenModelScope, UserAppointmentListingEffect.NavigateToDetail(action.lpId))

            is UserAppointmentListingAction.ChangeTab -> {
                val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
                val newTab = action.tab
                applyFilter(current.currentFilter, tab = newTab)
            }

            is UserAppointmentListingAction.RemoveDateFilter -> {
                val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedDates = current.currentFilter.selectedDates - action.date
                )
                applyFilter(updatedFilter)
            }

            is UserAppointmentListingAction.RemoveTimeFilter -> {
                val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedHours = current.currentFilter.selectedHours - action.time
                )
                applyFilter(updatedFilter)
            }

            is UserAppointmentListingAction.RemoveTitleFilter -> {
                val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedTitles = current.currentFilter.selectedTitles - action.title
                )
                applyFilter(updatedFilter)
            }

            is UserAppointmentListingAction.RemoveTrainerFilter -> {
                val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedTrainers = current.currentFilter.selectedTrainers - action.trainer
                )
                applyFilter(updatedFilter)
            }
        }
    }

    private fun refreshData() {
        screenModelScope.launch {
            try {
                val result = courseRepository.getAppointmentsByUser(user.normalUserId).getOrThrow()
                val currentState = _uiState.value as? UserAppointmentListingUiState.Content
                val currentTab = currentState?.activeTab ?: Active
                val currentFilter = currentState?.currentFilter ?: UserAppointmentListingFilter()
                val tabCounts = allTabs.map { tab ->
                    tab.label + " (" + result.count { it.status in tab.statusTypes } + ")"
                }

                val filtered = result
                    .filter { it.status in currentTab.statusTypes }
                    .filter {
                        (currentFilter.selectedTitles.isEmpty() || it.title in currentFilter.selectedTitles) &&
                                (currentFilter.selectedDates.isEmpty() || it.startDate in currentFilter.selectedDates) &&
                                (currentFilter.selectedHours.isEmpty() || it.startTime in currentFilter.selectedHours) &&
                                (currentFilter.selectedTrainers.isEmpty() || currentFilter.selectedTrainers.any { t -> t.trainerId == it.trainerId })
                    }

                _uiState.value = UserAppointmentListingUiState.Content(
                    activeTab = currentTab,
                    appointments = result,
                    filteredAppointments = filtered,
                    tabTitles = tabCounts,
                    currentFilter = currentFilter
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = UserAppointmentListingUiState.Error(e.message ?: "Veri alınamadı")
            }
        }
    }

    private fun cancelAppointment(appointment: Appointment) {
        screenModelScope.launch {
            try {
                courseRepository.cancelAppointment(appointment.lessonId, appointment.lpId).getOrThrow()
                refreshData()
            } catch (e: Exception) {
                _effect.emitIn(screenModelScope, UserAppointmentListingEffect.ShowCancelError(e.message ?: "Randevu iptal hatası"))
            }
        }
    }

    fun applyFilter(
        filter: UserAppointmentListingFilter,
        tab: UserAppointmentListingTab? = null
    ) {
        val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return
        val selectedTab = tab ?: current.activeTab

        val filteredAll = current.appointments.filter {
            (filter.selectedTitles.isEmpty() || it.title in filter.selectedTitles) &&
                    (filter.selectedDates.isEmpty() || it.startDate in filter.selectedDates) &&
                    (filter.selectedHours.isEmpty() || it.startTime in filter.selectedHours) &&
                    (filter.selectedTrainers.isEmpty() || filter.selectedTrainers.any { t -> t.trainerId == it.trainerId })
        }

        val tabTitlesFromFiltered = allTabs.map { eachTab ->
            eachTab.label + " (" + filteredAll.count { it.status in eachTab.statusTypes } + ")"
        }

        val filteredForSelectedTab = filteredAll.filter { it.status in selectedTab.statusTypes }

        _uiState.value = current.copy(
            activeTab = selectedTab,
            currentFilter = filter,
            filteredAppointments = filteredForSelectedTab,
            tabTitles = tabTitlesFromFiltered
        )
    }

    fun resetFilter() {
        val current = _uiState.value as? UserAppointmentListingUiState.Content ?: return

        val updatedFilter = UserAppointmentListingFilter()
        val filtered = current.appointments
            .filter { it.status in current.activeTab.statusTypes }

        val updatedTabTitles = allTabs.map { tab ->
            tab.label + " (" + current.appointments.count { it.status in tab.statusTypes } + ")"
        }

        _uiState.value = current.copy(
            currentFilter = updatedFilter,
            filteredAppointments = filtered,
            tabTitles = updatedTabTitles
        )
    }
}