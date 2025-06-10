package com.vurgun.skyfit.core.data.v1.data.user.model

import kotlinx.serialization.SerialName
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

@Serializable
data class GetCalendarEventsRequestDTO(
    val startDate: String? = null,
    val endDate: String? = null
)

@Serializable
data class AddCalendarEventRequestDTO(
    @SerialName(value = "eventId") val workoutId: Int? = null,
    @SerialName(value = "eventName") val eventName: String,
    @SerialName(value = "startDate") val startDateTime: String,
    @SerialName(value = "endDate") val endDateTime: String
)

@Serializable
data class CalendarWorkoutEventDTO(
    @SerialName("calendarEventId") val workoutEventId: Int,
    val eventName: String
)