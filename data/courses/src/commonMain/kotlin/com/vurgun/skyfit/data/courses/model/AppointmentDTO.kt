package com.vurgun.skyfit.data.courses.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AppointmentDTO(
    val lpId: Int,
    val lessonId: Int,
    val lessonIcon: Int,
    val typeName: String,
    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,
    val quotaInfo: String,
    val trainerId: Int,
    val trainerEvaluation: String? = null,
    val trainerNote: String? = null,
    val name: String,
    val surname: String,
    val gymName: String,
    val lastCancelTime: String,
    val price: Int,
    val status: Int,
    val statusName: String,
    val lessonStatus: Int,
    val participantStatus: Int,
    val joinedAt: String,
)
