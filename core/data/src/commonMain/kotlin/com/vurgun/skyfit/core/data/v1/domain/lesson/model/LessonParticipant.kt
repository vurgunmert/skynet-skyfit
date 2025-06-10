package com.vurgun.skyfit.core.data.v1.domain.lesson.model

data class LessonParticipant(
    val lpId: Int,
    val userId: Int,
    val lessonId: Int,
    val firstName: String,
    val lastName: String,
    val profileImageUrl: String?,
    val username: String,
    val trainerEvaluation: String?,
) {
    val evaluated: Boolean = trainerEvaluation != null
}