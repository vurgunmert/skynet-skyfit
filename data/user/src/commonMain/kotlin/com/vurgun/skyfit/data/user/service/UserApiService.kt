package com.vurgun.skyfit.data.user.service

import com.vurgun.skyfit.data.user.model.UserDetailDto
import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import io.ktor.client.request.bearerAuth
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
}