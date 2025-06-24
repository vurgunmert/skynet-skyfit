package com.vurgun.skyfit.core.data.v1.domain.posture.model

data class SavedPostureReport(
    val postureAnalyzeId: Int,
    val frontImageUrl: String,
    val frontAnalyzeReport: String,
    val backImageUrl: String,
    val backAnalyzeReport: String,
    val leftImageUrl: String,
    val leftAnalyzeReport: String,
    val rightImagUrl: String,
    val rightAnalyzeReport: String
)