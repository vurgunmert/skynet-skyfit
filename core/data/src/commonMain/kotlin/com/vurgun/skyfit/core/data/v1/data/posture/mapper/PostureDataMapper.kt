package com.vurgun.skyfit.core.data.v1.data.posture.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.v1.data.posture.model.AnalyzePostureResponseDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureDeviationDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.SavedPostureReportDTO
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureAnalysisReport
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureDeviation
import com.vurgun.skyfit.core.data.v1.domain.posture.model.SavedPostureReport

object PostureDataMapper {

    fun SavedPostureReportDTO.toDomainReport(): SavedPostureReport {
        return SavedPostureReport(
            postureAnalyzeId = postureAnalyzeId,
            frontImageUrl = serverImageFromPath(frontImagePath),
            frontAnalyzeReport = frontAnalyzeReport,
            backImageUrl = serverImageFromPath(backImagePath),
            backAnalyzeReport = backAnalyzeReport,
            leftImageUrl = serverImageFromPath(leftImagePath),
            leftAnalyzeReport = leftAnalyzeReport,
            rightImagUrl = serverImageFromPath(rightAnalyzeReport),
            rightAnalyzeReport = rightAnalyzeReport
        )
    }

    fun List<SavedPostureReportDTO>.toDomainReports(): List<SavedPostureReport> {
        return this.map { it.toDomainReport() }
    }

    fun PostureDeviationDTO.toDomainDeviation() : PostureDeviation {
        return PostureDeviation(
            condition = condition,
            confidence = confidence,
            severity = severity,
            expected = expected,
            explanation = explanation.tr
        )
    }

    fun List<PostureDeviationDTO>.toDomainDeviations(): List<PostureDeviation> {
        return this.map { it.toDomainDeviation() }
    }

    fun AnalyzePostureResponseDTO.toDomainResponsetoDomainResponse() : PostureAnalysisReport {
        return PostureAnalysisReport(
            viewAngle = viewAngle,
            deviations = deviations.toDomainDeviations(),
            landmarksDetected = landmarksDetected,
            imageQualityScore = imageQualityScore,
            expectedConditions = expectedConditions,
            accuracyScore = accuracyScore
        )
    }
}