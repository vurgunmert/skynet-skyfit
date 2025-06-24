package com.vurgun.skyfit.core.data.v1.data.measurement.service

import com.vurgun.skyfit.core.data.v1.data.measurement.model.AddMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.DeleteMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.GetMeasurementHistoryRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.MeasurementCategoryDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.MeasurementDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.UpdateMeasurementRequestDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class MeasurementApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_MEASUREMENT_CATEGORIES = "measurements/all-types"
        const val ADD_MEASUREMENT_RECORD = "measurements/add"
        const val UPDATE_MEASUREMENT_RECORD = "measurements/update"
        const val DELETE_MEASUREMENT_RECORD = "measurements/delete"
        const val GET_MEASUREMENT_HISTORY = "measurements/list"
    }

    suspend fun getMeasurementCategories(token: String): ApiResult<List<MeasurementCategoryDTO>> {
        return apiClient.safeApiCall<List<MeasurementCategoryDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_MEASUREMENT_CATEGORIES)
        }
    }

    suspend fun addMeasurement(
        request: AddMeasurementRequestDTO,
        token: String
    ): ApiResult<Unit> {
        return apiClient.safeApiCall<Unit> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.ADD_MEASUREMENT_RECORD)
            setBody(request)
        }
    }

    suspend fun updateMeasurement(
        request: UpdateMeasurementRequestDTO,
        token: String
    ): ApiResult<Unit> {
        return apiClient.safeApiCall<Unit> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.UPDATE_MEASUREMENT_RECORD)
            setBody(request)
        }
    }

    suspend fun deleteMeasurement(
        request: DeleteMeasurementRequestDTO,
        token: String
    ): ApiResult<Unit> {
        return apiClient.safeApiCall {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoint.DELETE_MEASUREMENT_RECORD)
            setBody(request)
        }
    }

    suspend fun getMeasurementHistory(
        request: GetMeasurementHistoryRequestDTO,
        token: String
    ): ApiResult<List<MeasurementDTO>> {
        return apiClient.safeApiCall<List<MeasurementDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_MEASUREMENT_HISTORY)
            setBody(request)
        }
    }
}
