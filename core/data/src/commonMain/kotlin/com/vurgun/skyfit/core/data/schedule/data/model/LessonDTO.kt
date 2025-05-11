package com.vurgun.skyfit.core.data.schedule.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LessonDTO(
    val lessonId: Int,
    val lessonIcon: Int,
    val typeName: String,
    val startDate: String,
    val capacity: String,
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
    val status: Int,
    val statusName: String,
)

@Serializable
internal data class ScheduledLessonDetailDTO(
    val lessonId: Int,
    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,
    val typeName: String,
    val status: Int,
    val statusName: String,
    val trainerName: String,
    val trainerSurname: String,
    val trainerNote: String?,
    val gymName: String,
    val totalParticipants: Int,
)
