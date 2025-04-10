package com.vurgun.skyfit.data.settings.repository

import com.vurgun.skyfit.data.core.model.EmptyDataResponse
import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.settings.model.AddGymMemberRequest
import com.vurgun.skyfit.data.settings.model.AddGymTrainerRequest
import com.vurgun.skyfit.data.settings.model.DeleteGymMemberRequest
import com.vurgun.skyfit.data.settings.model.DeleteGymTrainerRequest
import com.vurgun.skyfit.data.settings.model.GetGymMembersRequest
import com.vurgun.skyfit.data.settings.model.GetGymTrainersRequest
import com.vurgun.skyfit.data.settings.model.GetPlatformMembersRequest
import com.vurgun.skyfit.data.settings.model.GetPlatformTrainersRequest
import com.vurgun.skyfit.data.settings.model.MemberDto
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class SettingsApiService(private val apiClient: ApiClient) {

    suspend fun addGymMember(gymId: Int, userId: Int, token: String): ApiResult<EmptyDataResponse> {
        val request = AddGymMemberRequest(gymId, userId)
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("add/gym/user")
            setBody(request)
        }
    }

    suspend fun getGymMembers(gymId: Int, token: String): ApiResult<List<MemberDto>> {
        val request = GetGymMembersRequest(gymId)
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/gym/members")
            setBody(request)
        }
    }

    suspend fun getPlatformMembers(gymId: Int, token: String): ApiResult<List<MemberDto>> {
        val request = GetPlatformMembersRequest(gymId)
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/all/platform/members")
            setBody(request)
        }
    }

    suspend fun deleteGymMember(gymId: Int, userId: Int, token: String): ApiResult<EmptyDataResponse> {
        val request = DeleteGymMemberRequest(gymId, userId)
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url("delete/gym/member")
            setBody(request)
        }
    }

    suspend fun addGymTrainer(gymId: Int, userId: Int, token: String): ApiResult<EmptyDataResponse> {
        val request = AddGymTrainerRequest(gymId, userId)
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("add/gym/trainer")
            setBody(request)
        }
    }

    suspend fun getGymTrainers(gymId: Int, token: String): ApiResult<List<MemberDto>> {
        val request = GetGymTrainersRequest(gymId)
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/gym/trainers")
            setBody(request)
        }
    }

    suspend fun deleteGymTrainer(gymId: Int, userId: Int, token: String): ApiResult<EmptyDataResponse> {
        val request = DeleteGymTrainerRequest(gymId, userId)
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url("delete/gym/trainer")
            setBody(request)
        }
    }

    suspend fun getPlatformTrainers(gymId: Int, token: String): ApiResult<List<MemberDto>> {
        val request = GetPlatformTrainersRequest(gymId)
        return apiClient.safeApiCall<List<MemberDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/all/platform/trainers")
            setBody(request)
        }
    }
}