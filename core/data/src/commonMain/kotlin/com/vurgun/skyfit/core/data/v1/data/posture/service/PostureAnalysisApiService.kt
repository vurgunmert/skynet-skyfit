package com.vurgun.skyfit.core.data.v1.data.posture.service

import com.vurgun.skyfit.core.data.utility.randomUUID
import com.vurgun.skyfit.core.data.v1.data.posture.model.AnalyzePostureResponseDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.DeletePostureReportDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.SavePostureReportDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.SavedPostureReportDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class PostureAnalysisApiService(
    private val apiClient: ApiClient
) {

    private companion object Endpoint {
        const val SEND_POSTURE = "https://railwayposture-production.up.railway.app/analyze-posture"
        const val SAVE_REPORT = "posture/report/add"
        const val DELETE_REPORT = "posture/report/delete"
        const val GET_REPORTS = "posture/report/all"
    }

    suspend fun analyzePostureImage(
        byteArray: ByteArray
    ): AnalyzePostureResponseDTO {
        val response: HttpResponse = commonHttpClient.post(SEND_POSTURE) {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file",
                            byteArray,
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=image.png")
                            }
                        )
                    }
                )
            )
        }

        val responseText = response.bodyAsText()
        return Json.decodeFromString(responseText)
    }


    suspend fun savePostureReports(request: SavePostureReportDTO, token: String): ApiResult<Unit> {
        return apiClient.safeApiCall<Unit> {
            method = HttpMethod.Post
            url(SAVE_REPORT)
            bearerAuth(token)
            setBody(buildMultipartReportData(request))
        }
    }

    suspend fun deletePostureResult(request: DeletePostureReportDTO, token: String): ApiResult<Unit> {
        return apiClient.safeApiCall<Unit> {
            method = HttpMethod.Delete
            url(DELETE_REPORT)
            bearerAuth(token)
            setBody(request)
        }
    }

    suspend fun getPostureResults(token: String): ApiResult<List<SavedPostureReportDTO>> {
        return apiClient.safeApiCall<List<SavedPostureReportDTO>> {
            method = HttpMethod.Post
            url(GET_REPORTS)
            bearerAuth(token)
        }
    }

    private fun buildMultipartReportData(request: SavePostureReportDTO): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {

                //region Front
                append(
                    "frontImage", request.frontImage,
                    headersOf(
                        HttpHeaders.ContentType to listOf("image/png"),
                        HttpHeaders.ContentDisposition to listOf("filename=${randomUUID()}.png")
                    )
                )
                append("frontAnalyzeReport", request.frontAnalyzeReport)
                //endregion Front

                //region Back
                append(
                    "backImage", request.backImage,
                    headersOf(
                        HttpHeaders.ContentType to listOf("image/png"),
                        HttpHeaders.ContentDisposition to listOf("filename=${randomUUID()}.png")
                    )
                )
                append("backAnalyzeReport", request.backAnalyzeReport)
                //endregion Back

                //region Left
                append(
                    "leftImage", request.leftImage,
                    headersOf(
                        HttpHeaders.ContentType to listOf("image/png"),
                        HttpHeaders.ContentDisposition to listOf("filename=${randomUUID()}.png")
                    )
                )
                append("leftAnalyzeReport", request.leftAnalyzeReport)
                //endregion Left

                //region Right
                append(
                    "rightImage", request.rightImage,
                    headersOf(
                        HttpHeaders.ContentType to listOf("image/png"),
                        HttpHeaders.ContentDisposition to listOf("filename=${randomUUID()}.png")
                    )
                )
                append("rightAnalyzeReport", request.rightAnalyzeReport)
                //endregion Right
            }
        )
    }
}
