package com.vurgun.skyfit.feature_auth.data.service

import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.network.model.EmptyResponse
import com.vurgun.skyfit.feature_auth.data.model.CreatePasswordRequest
import com.vurgun.skyfit.feature_auth.data.model.LoginRequest
import com.vurgun.skyfit.feature_auth.data.model.LoginResponse
import com.vurgun.skyfit.feature_auth.data.model.SendOTPRequest
import com.vurgun.skyfit.feature_auth.data.model.VerifyOTPRequest
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class AuthApiService(private val apiClient: ApiClient) {

    suspend fun login(request: LoginRequest): ApiResult<LoginResponse> {
        return apiClient.safeApiCall<LoginResponse> {
            method = HttpMethod.Post
            url("auth")
            setBody(request)
        }
    }

    suspend fun verifyOTP(request: VerifyOTPRequest, token: String): ApiResult<LoginResponse> {
        return apiClient.safeApiCall<LoginResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("auth/otpverify")
            setBody(request)
        }
    }

    suspend fun sendOTP(request: SendOTPRequest, token: String): ApiResult<LoginResponse> {
        return apiClient.safeApiCall<LoginResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("send/otp")
            setBody(request)
        }
    }

    suspend fun createPassword(request: CreatePasswordRequest, token: String): ApiResult<EmptyResponse> {
        return apiClient.safeApiCall<EmptyResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("auth/createpassword")
            setBody(request)
        }
    }
}
