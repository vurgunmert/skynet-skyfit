package com.vurgun.skyfit.core.data.domain.model

import kotlinx.datetime.LocalDateTime

data class CalendarEvent(
    val calendarEventId: Int,
    val userId: Int,
    val name: String,
    val workoutEventId: Int? = null,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val lessonId: Int? = null,
    val lessonIcon: Int? = null,
    val trainerNote: String? = null,
    val trainerFullName: String? = null,
    val gymName: String? = null,
    val username: String? = null,
) {
    val isLesson: Boolean = lessonId != null
}