package com.vurgun.skyfit.core.data.v1.data.lesson.model

import kotlinx.serialization.Serializable

@Serializable
data class LessonParticipantDTO(
    val lpId: Int,
    val userId: Int,
    val lessonId: Int,
    val name: String,
    val surname: String,
    val profilePhoto: String?,
    val username: String,
    val trainerEvaluation: String?,
)