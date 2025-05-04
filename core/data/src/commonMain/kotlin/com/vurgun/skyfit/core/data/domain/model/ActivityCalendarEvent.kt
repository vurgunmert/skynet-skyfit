package com.vurgun.skyfit.core.data.domain.model

import kotlinx.serialization.Serializable

data class ActivityCalendarEvent(
    val calendarId: Int,
    val userId: Int,
    val name: String,
    val activityId: Int
)

@Serializable
data class ActivityCalendarEventDTO(
    val calendarId: Int,
    val userId: Int,
    val eventName: String,
    val eventId: Int? = null,
    val startDate: String,
    val lessonId: Int? = null,
    val lessonIcon: Int? = null,
    val trainerNote: String? = null,
    val trainerName: String? = null,
    val trainerSurname: String? = null,
    val gymName: String? = null,
    val username: String? = null,
)
