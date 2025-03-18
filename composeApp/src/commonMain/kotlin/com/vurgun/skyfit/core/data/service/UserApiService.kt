package com.vurgun.skyfit.core.data.service

import com.vurgun.skyfit.core.data.models.UserDetailDto
import com.vurgun.skyfit.core.network.client.ApiClient
import com.vurgun.skyfit.core.network.model.ApiResult
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