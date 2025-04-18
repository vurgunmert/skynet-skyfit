package com.vurgun.skyfit.data.courses.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AppointmentDTO(
    val lessonId: Int,
    val lessonIcon: Int,
    val typeName: String,
    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,
    val trainerId: Int,
    val name: String,
    val surname: String,
    val gymName: String,
    val trainerNote: String? = null,
    val lastCancelTime: String,
    val price: Int,
    val lessonStatus: Int,
    val participantStatus: Int,
    val statusName: String,
    val joinedAt: String,
)
