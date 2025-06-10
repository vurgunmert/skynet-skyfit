package com.vurgun.skyfit.core.data.v1.domain.lesson.model

import kotlinx.datetime.LocalDateTime

data class LessonCreationInfo(
    val gymId: Int,
    val iconId: Int,
    val title: String,
    val trainerNote: String?,
    val trainerId: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val repetitionType: Int,      // 1-4
    val repetition: List<Int>,    // 0=pazar .. 6=cumartesi
    val quota: Int,
    val lastCancelableHoursBefore: Int,
    val isRequiredAppointment: Boolean,
    val price: Int = 0,
    val participantType: Int = 1  // 1=everyone, 2=members, 3=followers
)

data class LessonUpdateInfo(
    val lessonId: Int,
    val iconId: Int,
    val trainerNote: String?,
    val trainerId: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val quota: Int,
    val lastCancelableHoursBefore: Int,
    val isRequiredAppointment: Boolean,
    val price: Int = 0,
    val participantType: Int = 1,  // 1=everyone, 2=members, 3=followers
    val participantsIds: List<Int> = emptyList()
)