package com.vurgun.skyfit.core.data.schedule.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

sealed interface StatusType {
    val code: Int

   data object Active : StatusType { override val code = 1 }
   data object Deleted : StatusType { override val code = 2 }
   data object Blocked : StatusType { override val code = 3 }
   data object Inactive : StatusType { override val code = 4 }
   data object Cancelled : StatusType { override val code = 5 }
   data object Completed : StatusType { override val code = 6 }
   data object Missing : StatusType { override val code = 7 }

    companion object {
        fun fromCode(code: Int): StatusType = when (code) {
            1 -> Active
            2 -> Deleted
            3 -> Blocked
            4 -> Inactive
            5 -> Cancelled
            6 -> Completed
            7 -> Missing
            else -> throw IllegalArgumentException("Unknown status code: $code")
        }
    }
}

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
) {
    val statusType: StatusType = StatusType.fromCode(status)
}

@Serializable
data class ScheduledLessonDetail(
    val lessonId: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val trainerFullName: String,
    val facilityName: String,
    val trainerNote: String? = null,
    val participantCount: Int,
    val status: Int,
    val statusName: String,
)