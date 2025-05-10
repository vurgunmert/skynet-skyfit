package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCalendarEventsRequest(
    val startDate: String? = null,
    val endDate: String? = null
)

@Serializable
data class AddCalendarEventRequest(
    val eventId: Int,
    val eventName: String,
    @SerialName(value = "startDate") val startDateTime: String,
    @SerialName(value = "endDate") val endDateTime: String
)