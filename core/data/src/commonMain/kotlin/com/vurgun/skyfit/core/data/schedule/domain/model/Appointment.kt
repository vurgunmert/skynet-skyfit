package com.vurgun.skyfit.core.data.schedule.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val lpId: Int,
    val lessonId: Int,
    val iconId: Int,
    val title: String,

    // Core Date & Time
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val lastCancelableAt: Instant,

    // Split fields for display or logic
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,

    // Trainer & Facility
    val trainerId: Int,
    val trainerFullName: String,
    val facilityName: String,
    val trainerNote: String? = null,

    // Participation & Status
    val joinedAt: Instant,
    val price: Int,
    val status: Int,
    val statusName: String,
    val lessonStatus: Int,
    val participantStatus: Int,
    val quotaInfo: String
)

data class AppointmentDetail(
    val lpId: Int,
    val lessonId: Int,
    val nmId: Int,
    val status: Int,
    val statusName: String,
    val title: String,
    val startDate: LocalDate,
    val startTime: LocalTime,
    val endDate: LocalDate,
    val endTime: LocalTime,
    val trainerFullName: String,
    val trainerNote: String?,
    val gymName: String,
    val participantCount: Int,
)