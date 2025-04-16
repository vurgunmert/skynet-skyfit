package com.vurgun.skyfit.data.courses.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val lessonId: Int,
    val iconId: Int,
    val title: String,

    // Core Logic Fields
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val lastCancelableAt: Instant,

    // For UI display
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,

    // Info
    val capacityRatio: String,
    val trainerId: Int,
    val trainerFullName: String,
    val facilityName: String,
    val trainerNote: String? = null,

    // Payment & Status
    val price: Int,
    val status: Int,
    val statusName: String,
)
