package com.vurgun.skyfit.core.data.service

import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.data.model.FacilityProfileDto
import com.vurgun.skyfit.core.data.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.model.GetFacilityProfileRequest
import com.vurgun.skyfit.core.data.model.GetFacilityTrainerProfilesRequest
import com.vurgun.skyfit.core.data.model.GetTrainerProfileRequest
import com.vurgun.skyfit.core.data.model.GetUserProfileRequest
import com.vurgun.skyfit.core.data.model.TrainerProfileDTO
import com.vurgun.skyfit.core.data.model.UserProfileDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class ProfileApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_USER_PROFILE = "profile/get/user"
        const val GET_TRAINER_PROFILE = "profile/get/trainer"
        const val GET_FACILITY_PROFILE = "profile/get/gym"
        const val GET_FACILITY_TRAINER_PROFILES = "profile/gym/trainers"
    }

    suspend fun getUserProfile(request: GetUserProfileRequest, token: String): ApiResult<UserProfileDTO> {
        return apiClient.safeApiCall<UserProfileDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_USER_PROFILE)
            setBody(request)
        }
    }

    suspend fun getTrainerProfile(request: GetTrainerProfileRequest, token: String): ApiResult<TrainerProfileDTO> {
        return apiClient.safeApiCall<TrainerProfileDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_TRAINER_PROFILE)
            setBody(request)
        }
    }

    suspend fun getFacilityTrainerProfiles(request: GetFacilityTrainerProfilesRequest, token: String): ApiResult<List<FacilityTrainerProfileDTO>> {
        return apiClient.safeApiCall<List<FacilityTrainerProfileDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_FACILITY_TRAINER_PROFILES)
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