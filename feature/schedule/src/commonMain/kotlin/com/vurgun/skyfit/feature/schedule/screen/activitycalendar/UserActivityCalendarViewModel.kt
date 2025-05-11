package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.model.CalendarEvent
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.utility.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed class UserActivityCalendarUiState {
    data object Loading : UserActivityCalendarUiState()
    data class Error(val message: String?) : UserActivityCalendarUiState()
    data object Content : UserActivityCalendarUiState()
}

sealed class UserActivityCalendarAction {
    data object OnClickBack : UserActivityCalendarAction()
    data object OnChangeCalendarMonth : UserActivityCalendarAction()
    data class OnChangeSelectedDate(val date: LocalDate) : UserActivityCalendarAction()
    data object OnClickAdd : UserActivityCalendarAction()
    data object OnClickEvent : UserActivityCalendarAction()
}

sealed class UserActivityCalendarEffect {
    data object NavigateToBack : UserActivityCalendarEffect()
    data class NavigateToCalendarSearch(val date: LocalDate) : UserActivityCalendarEffect()
}

class UserActivityCalendarViewModel(
    private val calendarRepository: UserCalendarRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserActivityCalendarUiState>(UserActivityCalendarUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserActivityCalendarEffect>()
    val effect: SharedFlow<UserActivityCalendarEffect> = _effect

    private val _activeDays = MutableStateFlow<Set<LocalDate>>(emptySet())
    val activeDays: StateFlow<Set<LocalDate>> = _activeDays

    private val _completedDays = MutableStateFlow<Set<LocalDate>>(emptySet())
    val completedDays: StateFlow<Set<LocalDate>> = _completedDays

    private val _allEvents = MutableStateFlow<List<CalendarEvent>>(emptyList())
    val allEvents: StateFlow<List<CalendarEvent>> = _allEvents

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    val filteredEvents: StateFlow<List<CalendarEvent>> = combine(
        _allEvents,
        selectedDate
    ) { events, selected ->
        events.filter { it.startDate == selected }
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun onAction(action: UserActivityCalendarAction) {
        when (action) {
            UserActivityCalendarAction.OnClickBack -> emitEffect(UserActivityCalendarEffect.NavigateToBack)
            is UserActivityCalendarAction.OnChangeSelectedDate -> {
                _selectedDate.value = action.date
            }

            UserActivityCalendarAction.OnChangeCalendarMonth -> TODO()
            UserActivityCalendarAction.OnClickAdd ->
                emitEffect(UserActivityCalendarEffect.NavigateToCalendarSearch(_selectedDate.value))

            UserActivityCalendarAction.OnClickEvent -> TODO()
        }
    }

    fun loadData(initialDate: LocalDate? = null) {
        _selectedDate.value = initialDate ?: LocalDate.now()

        screenModelScope.launch {
            runCatching {
                val events = calendarRepository.getCalendarEvents().getOrDefault(emptyList())
                val active = events.map { it.startDate }.toSet()
                val completed = emptySet<LocalDate>() //events.filter { it.isBeforeNow }.map { it.startDate }.toSet()
                _activeDays.value = active
                _completedDays.value = completed
                _allEvents.value = events

                _uiState.update(UserActivityCalendarUiState.Content)
            }.onFailure { error ->
                _uiState.update(UserActivityCalendarUiState.Error(error.message))
            }
        }
    }

    private fun emitEffect(effect: UserActivityCalendarEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}