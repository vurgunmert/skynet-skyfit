package com.vurgun.skyfit.core.data.v1.domain.posture.repository

import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureAnalysisReport
import com.vurgun.skyfit.core.data.v1.domain.posture.model.SavedPostureReport
import com.vurgun.skyfit.core.network.ApiResult

interface PostureAnalysisRepository {

    suspend fun analyzeImage(byteArray: ByteArray, viewAngle: String): PostureAnalysisReport
    suspend fun getSavedReports(): Result<List<SavedPostureReport>>
    suspend fun deleteReport(reportId: Int): Result<Unit>
    suspend fun saveReport(
        frontImage: ByteArray,
        frontReport: String,
        backImage: ByteArray,
        backReport: String,
        leftImage: ByteArray,
        leftReport: String,
        rightImage: ByteArray,
        rightReport: String
    ): Result<ApiResult<Unit>>
}
