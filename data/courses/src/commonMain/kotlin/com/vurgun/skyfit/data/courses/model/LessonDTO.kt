package com.vurgun.skyfit.data.courses.model

import kotlinx.serialization.Serializable

@Serializable
data class LessonDTO(
    val lessonId: Int,
    val lessonIcon: Int,
    val typeName: String,
    val startDate: String,
    val capacity: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,
    val trainerId: Long,
    val name: String,
    val surname: String,
    val gymName: String,
    val trainerNote: String,
    val lastCancelTime: String,
    val price: Long? = null,
    val status: Int,
    val statusName: String,
)
