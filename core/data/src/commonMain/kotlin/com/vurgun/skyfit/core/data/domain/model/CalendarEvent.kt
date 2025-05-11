package com.vurgun.skyfit.core.data.domain.model

import com.vurgun.skyfit.core.data.utility.isAfterNow
import com.vurgun.skyfit.core.data.utility.isBeforeNow
import kotlinx.datetime.LocalDateTime

data class CalendarEvent(
    val calendarEventId: Int,
    val userId: Int,
    val name: String,
    val workoutEventId: Int? = null,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val lessonId: Int? = null,
    val lessonIcon: Int? = null,
    val trainerNote: String? = null,
    val trainerFullName: String? = null,
    val gymName: String? = null,
    val username: String? = null,
) {
    val isLesson: Boolean = lessonId != null
    val startDate = startDateTime.date
    val startTime = startDateTime.time
    val endDate = endDateTime.date
    val endTime = endDateTime.time
    val isAfterNow = startDateTime.isAfterNow()
    val isBeforeNow = startDateTime.isBeforeNow()
}