package com.vurgun.skyfit.data.auth.service

import com.vurgun.skyfit.data.auth.model.AuthorizationRequest
import com.vurgun.skyfit.data.auth.model.AuthorizationResponse
import com.vurgun.skyfit.data.auth.model.CreatePasswordRequest
import com.vurgun.skyfit.data.auth.model.ResetPasswordRequest
import com.vurgun.skyfit.data.auth.model.VerifyOTPRequest
import com.vurgun.skyfit.data.core.model.EmptyDataResponse
import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class AuthApiService(private val apiClient: ApiClient) {

    suspend fun login(request: AuthorizationRequest): ApiResult<AuthorizationResponse> {
        return apiClient.safeApiCall<AuthorizationResponse> {
            method = HttpMethod.Post
            url("auth")
            setBody(request)
        }
    }

    suspend fun verifyOTP(request: VerifyOTPRequest, token: String): ApiResult<AuthorizationResponse> {
        return apiClient.safeApiCall<AuthorizationResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("auth/otpverify")
            setBody(request)
        }
    }

    suspend fun forgotPassword(request: AuthorizationRequest) = login(request)

    suspend fun forgotPasswordVerifyOTP(request: VerifyOTPRequest, token: String): ApiResult<AuthorizationResponse> {
        return apiClient.safeApiCall<AuthorizationResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("forgot/verify")
            setBody(request)
        }
    }

    suspend fun sendOTP(request: AuthorizationRequest, token: String): ApiResult<AuthorizationResponse> {
        return apiClient.safeApiCall<AuthorizationResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("send/otp")
            setBody(request)
        }
    }

    suspend fun createPassword(request: CreatePasswordRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("auth/createpassword")
            setBody(request)
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Put
            bearerAuth(token)
            url("forgot/change/password")
            setBody(request)
        }
    }
}
