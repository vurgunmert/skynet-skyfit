package com.vurgun.skyfit.data.user.service

import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.user.model.FacilityProfileDto
import com.vurgun.skyfit.data.user.model.GetFacilityProfileRequest
import com.vurgun.skyfit.data.user.model.GetTrainerProfileRequest
import com.vurgun.skyfit.data.user.model.GetUserProfileRequest
import com.vurgun.skyfit.data.user.model.TrainerProfileDto
import com.vurgun.skyfit.data.user.model.UserProfileDto
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class ProfileApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_USER_PROFILE = "profile/get/user"
        const val GET_TRAINER_PROFILE = "profile/get/trainer"
        const val GET_FACILITY_PROFILE = "profile/get/gym"
    }

    suspend fun getUserProfile(request: GetUserProfileRequest, token: String): ApiResult<UserProfileDto> {
        return apiClient.safeApiCall<UserProfileDto> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_USER_PROFILE)
            setBody(request)
        }
    }

    suspend fun getTrainerProfile(request: GetTrainerProfileRequest, token: String): ApiResult<TrainerProfileDto> {
        return apiClient.safeApiCall<TrainerProfileDto> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_TRAINER_PROFILE)
            setBody(request)
        }
    }

    suspend fun getFacilityProfile(request: GetFacilityProfileRequest, token: String): ApiResult<FacilityProfileDto> {
        return apiClient.safeApiCall<FacilityProfileDto> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_FACILITY_PROFILE)
            setBody(request)
        }
    }
}