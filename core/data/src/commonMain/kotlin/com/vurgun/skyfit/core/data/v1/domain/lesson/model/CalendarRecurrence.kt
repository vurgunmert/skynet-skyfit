package com.vurgun.skyfit.core.data.v1.domain.lesson.model

import kotlinx.datetime.DayOfWeek

sealed class CalendarRecurrence(val type: CalendarRecurrenceType, val id: Int) {
    data object Never : CalendarRecurrence(CalendarRecurrenceType.NEVER, id = 4)
    data object Daily : CalendarRecurrence(CalendarRecurrenceType.DAILY, id = 1)
    data class SomeDays(val days: List<DayOfWeek>) : CalendarRecurrence(CalendarRecurrenceType.SOMEDAYS, id = 2)
}

enum class CalendarRecurrenceType {
    NEVER, DAILY, SOMEDAYS
}