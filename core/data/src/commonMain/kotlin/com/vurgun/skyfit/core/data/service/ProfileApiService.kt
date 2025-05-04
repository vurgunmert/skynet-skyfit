package com.vurgun.skyfit.core.data.service

import com.vurgun.skyfit.core.data.model.DeleteProfileTagRequest
import com.vurgun.skyfit.core.data.model.EmptyDTO
import com.vurgun.skyfit.core.data.model.FacilityProfileDto
import com.vurgun.skyfit.core.data.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.model.GetFacilityProfileRequest
import com.vurgun.skyfit.core.data.model.GetFacilityTrainerProfilesRequest
import com.vurgun.skyfit.core.data.model.GetTrainerProfileRequest
import com.vurgun.skyfit.core.data.model.GetUserProfileRequest
import com.vurgun.skyfit.core.data.model.TrainerProfileDTO
import com.vurgun.skyfit.core.data.model.UpdateFacilityProfileRequest
import com.vurgun.skyfit.core.data.model.UpdateTrainerProfileRequest
import com.vurgun.skyfit.core.data.model.UpdateUserProfileRequest
import com.vurgun.skyfit.core.data.model.UserProfileDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headersOf

class ProfileApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_USER_PROFILE = "profile/get/user"
        const val GET_TRAINER_PROFILE = "profile/get/trainer"
        const val GET_FACILITY_PROFILE = "profile/get/gym"
        const val GET_FACILITY_TRAINER_PROFILES = "profile/gym/trainers"
        const val UPDATE_FACILITY_PROFILE = "profile/update/gym"
        const val UPDATE_TRAINER_PROFILE = "profile/update/trainer"
        const val UPDATE_USER_PROFILE = "profile/update/user"
        const val DELETE_A_TAG = "profile/delete/tag"
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

    suspend fun getFacilityTrainerProfiles(
        request: GetFacilityTrainerProfilesRequest,
        token: String
    ): ApiResult<List<FacilityTrainerProfileDTO>> {
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

    suspend fun updateUserProfile(request: UpdateUserProfileRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.UPDATE_USER_PROFILE)
            setBody(buildUserProfileFormData(request))
        }
    }

    suspend fun updateTrainerProfile(request: UpdateTrainerProfileRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.UPDATE_TRAINER_PROFILE)
            setBody(request)
        }
    }

    suspend fun updateFacilityProfile(request: UpdateFacilityProfileRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.UPDATE_FACILITY_PROFILE)
            setBody(request)
        }
    }

    suspend fun deleteProfileTag(request: DeleteProfileTagRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.DELETE_A_TAG)
            setBody(request)
        }
    }

    private fun buildUserProfileFormData(request: UpdateUserProfileRequest): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("username", request.username)
                append("name", request.name)
                append("surname", request.surname)
                append("weight", request.weight.toString())
                append("height", request.height.toString())
                append("bodyTypeId", request.bodyTypeId.toString())

                request.profilePhoto?.let { bytes ->
                    append(
                        "profilePhoto", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.userId}-profile-image.png")
                        )
                    )
                }

                request.backgroundImage?.let { bytes ->
                    append(
                        "backgroundImage", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.userId}-background-image.png")
                        )
                    )
                }
            }
        )
    }
}