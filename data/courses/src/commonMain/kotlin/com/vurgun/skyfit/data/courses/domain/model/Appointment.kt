package com.vurgun.skyfit.data.courses.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Appointment(
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
    val lessonStatus: Int,
    val participantStatus: Int,
    val statusName: String
)
