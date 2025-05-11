package com.vurgun.skyfit.core.data.schedule.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CalendarEventDTO(
    val calendarEventId: Int,
    val userId: Int,
    val eventName: String,
    val eventId: Int? = null,
    val startDate: String,
    val endDate: String,
    val lessonId: Int? = null,
    val lessonIcon: Int? = null,
    val trainerNote: String? = null,
    val trainerName: String? = null,
    val trainerSurname: String? = null,
    val gymName: String? = null,
    val username: String? = null,
)
