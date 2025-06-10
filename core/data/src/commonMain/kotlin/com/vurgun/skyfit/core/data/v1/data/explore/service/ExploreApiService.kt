package com.vurgun.skyfit.core.data.v1.data.explore.service

import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityProfileDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.TrainerProfileDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class ExploreApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_ALL_FACILITIES = "get/all/gyms"
        const val GET_ALL_TRAINERS = "get/all/trainers"
    }

    suspend fun getFacilities(token: String): ApiResult<List<FacilityProfileDTO>> {
        return apiClient.safeApiCall<List<FacilityProfileDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_ALL_FACILITIES)
        }
    }

    suspend fun getTrainers(token: String): ApiResult<List<TrainerProfileDTO>> {
        return apiClient.safeApiCall<List<TrainerProfileDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_ALL_TRAINERS)
        }
    }
}