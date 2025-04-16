package com.vurgun.skyfit.data.courses.domain.model

import kotlinx.datetime.LocalDateTime

data class UpdatedLesson(
    val lessonId: Int,
    val iconId: Int,
    val trainerNote: String,
    val trainerId: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val quota: Int,
    val lastCancelableAt: LocalDateTime,
    val isRequiredAppointment: Boolean,
    val price: Int = 0,
    val participantType: Int = 1,
    val participants: List<Int>
)
