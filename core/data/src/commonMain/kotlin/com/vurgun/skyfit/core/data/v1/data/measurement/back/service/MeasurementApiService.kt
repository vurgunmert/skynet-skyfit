package com.vurgun.skyfit.core.data.v1.measurement.back.service

import com.vurgun.skyfit.core.data.v1.measurement.back.model.CreateMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.measurement.back.model.MeasurementDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class MeasurementApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val CREATE_MEASUREMENT = "measurement/create"
        const val GET_USER_MEASUREMENTS = "measurement/user"
        const val DELETE_MEASUREMENT = "measurement/delete"
    }

    suspend fun createMeasurement(
        request: CreateMeasurementRequestDTO,
        token: String
    ): ApiResult<Unit> {
        return apiClient.safeApiCall<Unit> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.CREATE_MEASUREMENT)
            setBody(request)
        }
    }

    suspend fun getUserMeasurements(
        userId: String,
        token: String
    ): ApiResult<List<MeasurementDTO>> {
        return apiClient.safeApiCall {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_USER_MEASUREMENTS)
            setBody(mapOf("userId" to userId))
        }
    }

    suspend fun deleteMeasurement(
        measurementId: String,
        token: String
    ): ApiResult<Unit> {
        return apiClient.safeApiCall {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.DELETE_MEASUREMENT)
            setBody(mapOf("measurementId" to measurementId))
        }
    }
}
