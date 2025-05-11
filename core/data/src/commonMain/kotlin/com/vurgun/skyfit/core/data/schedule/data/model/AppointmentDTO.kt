package com.vurgun.skyfit.core.data.schedule.data.model

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

@Serializable
data class AppointmentDetailDTO(
    val lpId: Int,
    val lessonId: Int,
    val nmId: Int,
    val status: Int,
    val statusName: String,
    val typeName: String,
    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,
    val trainerName: String,
    val trainerSurname: String,
    val trainerNote: String?,
    val gymName: String,
    val totalParticipants: Int,
)
