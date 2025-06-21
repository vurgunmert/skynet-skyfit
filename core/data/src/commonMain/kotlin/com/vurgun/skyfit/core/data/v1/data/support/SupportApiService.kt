package com.vurgun.skyfit.core.data.v1.data.support

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.support.model.SupportRequestDTO
import com.vurgun.skyfit.core.data.v1.data.support.model.SupportTypeDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SupportApiService(private val apiClient: ApiClient) {

    private companion object Endpoint {
        const val GET_SUPPORT_TYPES = "support/types"
        const val CREATE_SUPPORT_REQUEST = "support/request"
    }

    suspend fun getSupportTypes(token: String): ApiResult<List<SupportTypeDTO>> {
        return apiClient.safeApiCall<List<SupportTypeDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(GET_SUPPORT_TYPES)
        }
    }

    suspend fun createSupportRequest(requestBody: SupportRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(GET_SUPPORT_TYPES)
            setBody(buildMultipartFormData(requestBody))
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildMultipartFormData(request: SupportRequestDTO): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("typeId", request.typeId.toString())
                append("email", request.email)
                append("description", request.description)

                request.file?.let { bytes ->
                    val uuid = Uuid.random()

                    append(
                        "support-attachment", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${uuid}.png")
                        )
                    )
                }
            }
        )
    }
}

