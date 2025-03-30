package com.vurgun.skyfit.designsystem.components.calendar.monthly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.utils.generateDaysInMonth
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.core.utils.withDayOfMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

data class DateRangeSelectorState(
    val selectedStartDate: LocalDate,
    val selectedEndDate: LocalDate?,
    val visibleMonth: LocalDate,
    val days: List<CalendarMonthDaySelectorItemModel>
)

enum class CalendarSelectionMode {
    Single,
    Range
}

class CalendarDateSelectorViewModel(private val selectionMode: CalendarSelectionMode = CalendarSelectionMode.Range) : ViewModel() {
    private val _startDate = MutableStateFlow<LocalDate>(LocalDate.now())
    private val _endDate = MutableStateFlow<LocalDate?>(null)
    private val _visibleMonth = MutableStateFlow(LocalDate.now().withDayOfMonth(1))

    val state: StateFlow<DateRangeSelectorState> = combine(
        _startDate, _endDate, _visibleMonth
    ) { start, end, month ->
        val days = generateMonthDays(month, start, end)
        DateRangeSelectorState(start, end, month, days)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DateRangeSelectorState(LocalDate.now(), null, LocalDate.now(), emptyList()))

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
}

fun generateMonthDays(
    month: LocalDate,
    start: LocalDate?,
    end: LocalDate?
): List<CalendarMonthDaySelectorItemModel> {
    val allDays = generateDaysInMonth(month) // Includes full 6x7 grid

    return allDays.map { date ->
        val isSelected = date == start || date == end
        val isInRange = start != null && end != null && date > start && date < end
        val isOutOfMonth = date.monthNumber != month.monthNumber

        val state = when {
            isSelected -> CalendarMonthDayItemState.Filter.Selected
            isInRange -> CalendarMonthDayItemState.Filter.Selected
            isOutOfMonth -> CalendarMonthDayItemState.Filter.OutMonth
            else -> CalendarMonthDayItemState.Filter.InMonth
        }

        CalendarMonthDaySelectorItemModel(
            label = date.dayOfMonth.toString(),
            date = date,
            state = state
        )
    }
}

