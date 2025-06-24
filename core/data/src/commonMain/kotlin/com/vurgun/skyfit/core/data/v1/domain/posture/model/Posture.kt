package com.vurgun.skyfit.core.data.v1.domain.posture.model

import kotlinx.serialization.Serializable

@Serializable
data class PostureAnalysisReport(
    val viewAngle: String,
    val deviations: List<PostureDeviation>,
    val landmarksDetected: Boolean,
    val imageQualityScore: Float,
    val expectedConditions: List<String>? = null,
    val accuracyScore: Float,
)

@Serializable
data class PostureDeviation(
    val condition: String,
    val confidence: Float,
    val severity: String,
    val expected: Boolean,
    val explanation: String? = null,
)