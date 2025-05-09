package com.vurgun.skyfit.core.data.repository

import com.vurgun.skyfit.core.data.model.EmptyDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.data.model.AddGymMemberRequest
import com.vurgun.skyfit.core.data.model.AddGymTrainerRequest
import com.vurgun.skyfit.core.data.model.DeleteGymMemberRequest
import com.vurgun.skyfit.core.data.model.DeleteGymTrainerRequest
import com.vurgun.skyfit.core.data.model.GetGymMembersRequest
import com.vurgun.skyfit.core.data.model.GetGymTrainersRequest
import com.vurgun.skyfit.core.data.model.GetPlatformMembersRequest
import com.vurgun.skyfit.core.data.model.GetPlatformTrainersRequest
import com.vurgun.skyfit.core.data.model.MemberDto
import com.vurgun.skyfit.core.data.model.TrainerDto
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class SettingsApiService(private val apiClient: ApiClient) {

    private object Endpoints {
        const val ADD_GYM_USER = "add/gym/user"
        const val GET_GYM_MEMBERS = "get/gym/members"
        const val GET_ALL_PLATFORM_MEMBERS = "get/all/platform/members"
        const val DELETE_GYM_MEMBER = "delete/gym/member"

        const val ADD_GYM_TRAINER = "add/gym/trainer"
        const val GET_GYM_TRAINERS = "get/gym/trainers"
        const val GET_ALL_PLATFORM_TRAINERS = "get/all/platform/trainers"
        const val DELETE_GYM_TRAINER = "delete/gym/trainer"
    }

    suspend fun addGymMember(request: AddGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_USER)
            setBody(request)
        }
    }

    suspend fun getGymMembers(request: GetGymMembersRequest, token: String): ApiResult<List<MemberDto>> {
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun getPlatformMembers(request: GetPlatformMembersRequest, token: String): ApiResult<List<MemberDto>> {
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun deleteGymMember(request: DeleteGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_MEMBER)
            setBody(request)
        }
    }

    suspend fun addGymTrainer(request: AddGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_TRAINER)
            setBody(request)
        }
    }

    suspend fun getGymTrainers(request: GetGymTrainersRequest, token: String): ApiResult<List<TrainerDto>> {
        return apiClient.safeApiCall<List<TrainerDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun getPlatformTrainers(request: GetPlatformTrainersRequest, token: String): ApiResult<List<TrainerDto>> {
        return apiClient.safeApiCall<List<TrainerDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun deleteGymTrainer(request: DeleteGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_TRAINER)
            setBody(request)
        }
    }
}