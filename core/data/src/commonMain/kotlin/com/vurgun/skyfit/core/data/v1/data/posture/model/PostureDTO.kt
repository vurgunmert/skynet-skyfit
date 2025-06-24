package com.vurgun.skyfit.core.data.v1.data.posture.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnalyzePostureRequestDTO(
    val image: String, //base64
)

@Serializable
data class AnalyzePostureResponseDTO(
    @SerialName("view_angle") val viewAngle: String,
    @SerialName("deviations") val deviations: List<PostureDeviationDTO>,
    @SerialName("landmarks_detected") val landmarksDetected: Boolean,
    @SerialName("image_quality_score") val imageQualityScore: Float,
    @SerialName("expected_conditions") val expectedConditions: List<String>? = null,
    @SerialName("accuracy_score") val accuracyScore: Float,
)

@Serializable
data class PostureDeviationDTO(
    val condition: String,
    val confidence: Float,
    val severity: String,
    val expected: Boolean,
    val explanation: ExplanationDTO,
) {
    @Serializable
    data class ExplanationDTO(
        val en: String,
        val tr: String
    )
}

@Serializable
data class SavePostureReportDTO(
    val frontImage: ByteArray,
    val frontAnalyzeReport: String,
    val backImage: ByteArray,
    val backAnalyzeReport: String,
    val leftImage: ByteArray,
    val leftAnalyzeReport: String,
    val rightImage: ByteArray,
    val rightAnalyzeReport: String
)

@Serializable
data class SavedPostureReportDTO(
    val postureAnalyzeId: Int,
    val frontImagePath: String,
    val frontAnalyzeReport: String,
    val backImagePath: String,
    val backAnalyzeReport: String,
    val leftImagePath: String,
    val leftAnalyzeReport: String,
    val rightImagPath: String,
    val rightAnalyzeReport: String
)


@Serializable
data class DeletePostureReportDTO(
    val postureAnalyzeId: Int
)

