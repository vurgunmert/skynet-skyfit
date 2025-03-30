package com.vurgun.skyfit.designsystem.components.calendar.weekdayselector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.utils.getStartOfWeek
import com.vurgun.skyfit.core.utils.getTurkishDayAbbreviation
import com.vurgun.skyfit.core.utils.nextWeek
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.core.utils.previousWeek
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

data class WeekDaySelectorState(
    val selectedDate: LocalDate,
    val weekDays: List<WeekDayUiModel>
)

class WeekDaySelectorViewModel : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    val weekDays: StateFlow<List<WeekDayUiModel>> = _selectedDate.map { generateWeekDays(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, generateWeekDays(LocalDate.now()))

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun loadPreviousWeek() {
        _selectedDate.value = _selectedDate.value.previousWeek()
    }

    fun loadNextWeek() {
        _selectedDate.value = _selectedDate.value.nextWeek()
    }

    private fun generateWeekDays(selectedDate: LocalDate): List<WeekDayUiModel> {
        val startOfWeek = getStartOfWeek(selectedDate)
        return List(7) { i ->
            val date = startOfWeek.plus(i, DateTimeUnit.DAY)
            WeekDayUiModel(
                date = date,
                dayName = getTurkishDayAbbreviation(date),
                isSelected = date == selectedDate
            )
        }
    }
}

