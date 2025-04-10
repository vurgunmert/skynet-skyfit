package com.vurgun.skyfit.data.core.domain.model

import kotlinx.datetime.DayOfWeek

sealed class CalendarRecurrence(val type: CalendarRecurrenceType) {
    data object Never : CalendarRecurrence(CalendarRecurrenceType.NEVER)
    data object Daily : CalendarRecurrence(CalendarRecurrenceType.DAILY)
    data class SomeDays(val days: List<DayOfWeek>) : CalendarRecurrence(CalendarRecurrenceType.SOMEDAYS)
}

enum class CalendarRecurrenceType {
    NEVER, DAILY, SOMEDAYS
}