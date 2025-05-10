package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutEventDTO(
    @SerialName("calendarEventId") val workoutEventId: Int,
    val eventName: String
)