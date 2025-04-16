package com.vurgun.skyfit.data.courses.domain.model

import kotlinx.datetime.LocalDateTime

data class NewLesson(
    val gymId: Int,
    val iconId: Int,
    val title: String,
    val trainerNote: String,
    val trainerId: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val repetitionType: Int,      // 1-4
    val repetition: List<Int>,    // 1=pazar .. 7=cumartesi
    val quota: Int,
    val lastCancelableHoursBefore: Int,
    val isRequiredAppointment: Boolean,
    val price: Int = 0,
    val participantType: Int = 1  // 1=everyone, 2=members, 3=followers
)
