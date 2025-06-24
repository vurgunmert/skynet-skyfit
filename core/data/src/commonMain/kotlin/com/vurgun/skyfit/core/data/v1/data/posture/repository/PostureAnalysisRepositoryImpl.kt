package com.vurgun.skyfit.core.data.v1.data.posture.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.posture.mapper.PostureDataMapper.toDomainReports
import com.vurgun.skyfit.core.data.v1.data.posture.mapper.PostureDataMapper.toDomainResponsetoDomainResponse
import com.vurgun.skyfit.core.data.v1.data.posture.model.DeletePostureReportDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.SavePostureReportDTO
import com.vurgun.skyfit.core.data.v1.data.posture.service.PostureAnalysisApiService
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureAnalysisReport
import com.vurgun.skyfit.core.data.v1.domain.posture.model.SavedPostureReport
import com.vurgun.skyfit.core.data.v1.domain.posture.repository.PostureAnalysisRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class PostureAnalysisRepositoryImpl(
    private val apiService: PostureAnalysisApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : PostureAnalysisRepository {

    override suspend fun analyzeImage(byteArray: ByteArray, viewAngle: String): PostureAnalysisReport {
        return apiService.analyzePostureImage(byteArray).toDomainResponsetoDomainResponse()
    }

    override suspend fun saveReport(
        frontImage: ByteArray, frontReport: String,
        backImage: ByteArray, backReport: String,
        leftImage: ByteArray, leftReport: String,
        rightImage: ByteArray, rightReport: String
    ) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = SavePostureReportDTO(
            frontImage = frontImage,
            frontAnalyzeReport = frontReport,
            backImage = backImage,
            backAnalyzeReport = backReport,
            leftImage = leftImage,
            leftAnalyzeReport = leftReport,
            rightImage = rightImage,
            rightAnalyzeReport = rightReport
        )
        apiService.savePostureReports(request, token)
    }

    override suspend fun getSavedReports(): Result<List<SavedPostureReport>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getPostureResults(token).mapOrThrow { it.toDomainReports() }
    }

    override suspend fun deleteReport(reportId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeletePostureReportDTO(reportId)
        apiService.deletePostureResult(request, token).mapOrThrow { it }
    }
}