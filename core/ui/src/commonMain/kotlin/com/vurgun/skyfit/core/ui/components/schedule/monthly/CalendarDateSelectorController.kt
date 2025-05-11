package com.vurgun.skyfit.core.ui.components.schedule.monthly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.vurgun.skyfit.core.data.utility.generateDaysInMonth
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.utility.withDayOfMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

data class DateRangeSelectorState(
    val selectedStartDate: LocalDate?,
    val selectedEndDate: LocalDate?,
    val visibleMonth: LocalDate,
    val days: List<DatePickerCalendar.CalendarMonthDaySelectorItemModel>
)

enum class CalendarSelectionMode {
    Single,
    Range
}

class CalendarDateSelectorController(
    initialStartDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    initialVisibleMonth: LocalDate,
    private val selectionMode: CalendarSelectionMode = CalendarSelectionMode.Range,
    private val coroutineScope: CoroutineScope
) {
    private val _startDate = MutableStateFlow(initialStartDate)
    private val _endDate = MutableStateFlow(initialEndDate)
    private val _visibleMonth = MutableStateFlow(initialVisibleMonth)

    val state: StateFlow<DateRangeSelectorState> = combine(
        _startDate, _endDate, _visibleMonth
    ) { start, end, month ->
        val days = generateMonthDays(month, start, end)
        DateRangeSelectorState(start, end, month, days)
    }.stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        DateRangeSelectorState(LocalDate.now(), null, LocalDate.now(), emptyList())
    )

    fun onDayClick(date: LocalDate) {
        when (selectionMode) {
            CalendarSelectionMode.Single -> {
                _startDate.value = date
                _endDate.value = null
            }

            CalendarSelectionMode.Range -> {
                val currentStart = _startDate.value
                val currentEnd = _endDate.value

                when {
                    currentStart == null -> _startDate.value = date
                    currentEnd == null && date > currentStart -> _endDate.value = date
                    else -> {
                        _startDate.value = date
                        _endDate.value = null
                    }
                }
            }
        }
    }

    fun loadPreviousMonth() {
        _visibleMonth.value = _visibleMonth.value.minus(1, DateTimeUnit.MONTH)
    }

    fun loadNextMonth() {
        _visibleMonth.value = _visibleMonth.value.plus(1, DateTimeUnit.MONTH)
    }

    fun reset() {
        _startDate.value = LocalDate.now()
        _endDate.value = null
    }
}

fun generateMonthDays(
    month: LocalDate,
    start: LocalDate?,
    end: LocalDate?
): List<DatePickerCalendar.CalendarMonthDaySelectorItemModel> {
    val allDays = generateDaysInMonth(month) // Includes full 6x7 grid

    return allDays.map { date ->
        val isSelected = date == start || date == end
        val isInRange = start != null && end != null && date > start && date < end
        val isOutOfMonth = date.monthNumber != month.monthNumber

        val state = when {
            isSelected -> DatePickerCalendar.CalendarMonthDayItemState.Filter.Selected
            isInRange -> DatePickerCalendar.CalendarMonthDayItemState.Filter.Selected
            isOutOfMonth -> DatePickerCalendar.CalendarMonthDayItemState.Filter.OutMonth
            else -> DatePickerCalendar.CalendarMonthDayItemState.Filter.InMonth
        }

        DatePickerCalendar.CalendarMonthDaySelectorItemModel(
            label = date.dayOfMonth.toString(),
            date = date,
            state = state
        )
    }
}

@Composable
fun rememberNonEmptyCalendarSelectorController(
    selectionMode: CalendarSelectionMode = CalendarSelectionMode.Range,
    initialStartDate: LocalDate = LocalDate.now(),
    initialEndDate: LocalDate? = null
): CalendarDateSelectorController {
    val scope = rememberCoroutineScope()
    return remember(selectionMode, initialStartDate, initialEndDate) {
        CalendarDateSelectorController(
            initialStartDate = initialStartDate,
            initialEndDate = initialEndDate,
            initialVisibleMonth = initialStartDate.withDayOfMonth(1),
            selectionMode = selectionMode,
            coroutineScope = scope
        )
    }
}

@Composable
fun rememberEmptySelectCalendarSelectorController(
    selectionMode: CalendarSelectionMode = CalendarSelectionMode.Single,
    initialStartDate: LocalDate? = null,
): CalendarDateSelectorController {
    val scope = rememberCoroutineScope()
    return remember(selectionMode) {
        CalendarDateSelectorController(
            initialStartDate = initialStartDate,
            initialEndDate = null,
            initialVisibleMonth = LocalDate.now().withDayOfMonth(1),
            selectionMode = selectionMode,
            coroutineScope = scope
        )
    }
}

data class EventCalendarState(
    val selectedDate: LocalDate?,
    val visibleMonth: LocalDate,
    val days: List<EventCalendar.EventCalendarMonthDaySelectorItemModel>
)

class EventCalendarController(
    initialSelectedDate: LocalDate? = null,
    initialVisibleMonth: LocalDate,
    private val coroutineScope: CoroutineScope,
    private val activatedDatesProvider: suspend () -> Set<LocalDate>,
    private val completedDatesProvider: suspend () -> Set<LocalDate>
) {
    private val _selectedDate = MutableStateFlow<LocalDate?>(initialSelectedDate ?: LocalDate.now())
    private val _visibleMonth = MutableStateFlow(initialVisibleMonth)
    private val _activatedDates = MutableStateFlow(emptySet<LocalDate>())
    private val _completedDates = MutableStateFlow(emptySet<LocalDate>())

    val state: StateFlow<EventCalendarState> = combine(
        _visibleMonth, _activatedDates, _completedDates, _selectedDate
    ) { month, activated, completed, selected ->
        val days = generateEventCalendarDays(month, activated, completed, selected)
        EventCalendarState(selected, month, days)
    }.stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        EventCalendarState(null, initialVisibleMonth, emptyList())
    )

    suspend fun refreshEvents() {
        _activatedDates.value = activatedDatesProvider()
        _completedDates.value = completedDatesProvider()
    }

    fun onDateClick(date: LocalDate) {
        _selectedDate.value = date
    }

    fun loadPreviousMonth() {
        _visibleMonth.value = _visibleMonth.value.minus(1, DateTimeUnit.MONTH)
    }

    fun loadNextMonth() {
        _visibleMonth.value = _visibleMonth.value.plus(1, DateTimeUnit.MONTH)
    }
}

fun generateEventCalendarDays(
    month: LocalDate,
    activatedDates: Set<LocalDate>,
    completedDates: Set<LocalDate>,
    selectedDate: LocalDate?
): List<EventCalendar.EventCalendarMonthDaySelectorItemModel> {
    val allDays = generateDaysInMonth(month)

    return allDays.map { date ->
        val state = when {
            date == selectedDate -> EventCalendar.EventCalendarMonthDayItemState.Filter.Selected
            date in activatedDates -> EventCalendar.EventCalendarMonthDayItemState.Filter.Activated
            date in completedDates -> EventCalendar.EventCalendarMonthDayItemState.Filter.Completed
            date.monthNumber != month.monthNumber -> EventCalendar.EventCalendarMonthDayItemState.Filter.OutMonth
            else -> EventCalendar.EventCalendarMonthDayItemState.Filter.InMonth
        }


        EventCalendar.EventCalendarMonthDaySelectorItemModel(
            label = date.dayOfMonth.toString(),
            date = date,
            state = state
        )
    }
}

@Composable
fun rememberEventCalendarController(
    activatedDatesProvider: suspend () -> Set<LocalDate>,
    completedDatesProvider: suspend () -> Set<LocalDate>,
    initialSelectedDate: LocalDate = LocalDate.now(),
    initialMonth: LocalDate = LocalDate.now().withDayOfMonth(1)
): EventCalendarController {
    val scope = rememberCoroutineScope()
    return remember(
        activatedDatesProvider,
        completedDatesProvider,
        initialSelectedDate,
        initialMonth
    ) {
        EventCalendarController(
            initialSelectedDate = initialSelectedDate,
            initialVisibleMonth = initialMonth,
            coroutineScope = scope,
            activatedDatesProvider = activatedDatesProvider,
            completedDatesProvider = completedDatesProvider
        )
    }
}