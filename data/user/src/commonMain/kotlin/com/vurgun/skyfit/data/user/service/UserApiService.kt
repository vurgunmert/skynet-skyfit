package com.vurgun.skyfit.data.user.service

import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.user.model.SelectUserTypeRequest
import com.vurgun.skyfit.data.user.model.SelectUserTypeResponse
import com.vurgun.skyfit.data.user.model.UserAccountTypeDto
import com.vurgun.skyfit.data.user.model.UserDetailDto
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class UserApiService(private val apiClient: ApiClient) {

    suspend fun getDetails(token: String): ApiResult<UserDetailDto> {
        return apiClient.safeApiCall<UserDetailDto> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("user/detail")
        }
    }

    suspend fun getAccountTypes(token: String): ApiResult<List<UserAccountTypeDto>> {
        return apiClient.safeApiCall<List<UserAccountTypeDto>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/usertypes")
        }
    }

    suspend fun selectUserType(typeId: Int, token: String): ApiResult<SelectUserTypeResponse> {
        val request = SelectUserTypeRequest(typeId)
        return apiClient.safeApiCall<SelectUserTypeResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("select/usertype")
            setBody(request)
        }
    }
}