package com.vurgun.skyfit.core.ui.components.schedule.weekly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.vurgun.skyfit.core.data.utility.getStartOfWeek
import com.vurgun.skyfit.core.data.utility.getTurkishDayAbbreviation
import com.vurgun.skyfit.core.data.utility.nextWeek
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.utility.previousWeek
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

data class CalendarWeekDaySelectorState(
    val selectedDate: LocalDate,
    val weekDays: List<CalendarWeekDayItemModel>
)

class CalendarWeekDaySelectorController(
    private val coroutineScope: CoroutineScope
) {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    val weekDays: StateFlow<List<CalendarWeekDayItemModel>> = _selectedDate
        .map { generateWeekDays(it) }
        .stateIn(
            coroutineScope,
            SharingStarted.Eagerly,
            generateWeekDays(LocalDate.now())
        )

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun loadPreviousWeek() {
        _selectedDate.value = _selectedDate.value.previousWeek()
    }

    fun loadNextWeek() {
        _selectedDate.value = _selectedDate.value.nextWeek()
    }

    private fun generateWeekDays(selectedDate: LocalDate): List<CalendarWeekDayItemModel> {
        val startOfWeek = getStartOfWeek(selectedDate)
        return List(7) { i ->
            val date = startOfWeek.plus(i, DateTimeUnit.DAY)
            CalendarWeekDayItemModel(
                date = date,
                dayName = getTurkishDayAbbreviation(date),
                isSelected = date == selectedDate
            )
        }
    }
}

@Composable
fun rememberCalendarWeekDaySelectorController(): CalendarWeekDaySelectorController {
    val scope = rememberCoroutineScope()
    return remember { CalendarWeekDaySelectorController(scope) }
}
