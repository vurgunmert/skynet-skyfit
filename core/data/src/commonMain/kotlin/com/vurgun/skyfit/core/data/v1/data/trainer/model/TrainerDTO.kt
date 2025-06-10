package com.vurgun.skyfit.core.data.v1.data.trainer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainerDTO(
    val userId: Int,
    val trainerId: Int,
    @SerialName(value = "profilePhoto") val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String
)

@Serializable
internal data class GetTrainerLessonsRequest(
    val trainerId: Int,
    val startFilterDate: String? = null, // datetime türünde gönderilecek
    val endFilterDate: String? = null
)

@Serializable
internal data class GetUpcomingTrainerLessonsRequest(
    val trainerId: Int,
    val limit: Int
)

@Serializable
internal data class EvaluateParticipantsRequest(
    val lessonId: Int,
    val participants: List<EvaluatedParticipant>
) {
    @Serializable
    data class EvaluatedParticipant(
        val lpId: Int,
        val trainerEvaluation: String?,
    )
}