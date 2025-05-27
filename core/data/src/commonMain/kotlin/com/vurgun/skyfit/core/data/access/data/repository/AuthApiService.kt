package com.vurgun.skyfit.core.data.access.data.repository

import com.vurgun.skyfit.core.data.access.data.model.AuthorizationRequest
import com.vurgun.skyfit.core.data.access.data.model.AuthAccessDTO
import com.vurgun.skyfit.core.data.access.data.model.CreatePasswordRequest
import com.vurgun.skyfit.core.data.access.data.model.ResetPasswordRequest
import com.vurgun.skyfit.core.data.access.data.model.VerifyOTPRequest
import com.vurgun.skyfit.core.data.access.data.repository.AuthApiService.Endpoint.GET_ALL_TAGS
import com.vurgun.skyfit.core.data.shared.data.model.EmptyDTO
import com.vurgun.skyfit.core.data.shared.data.model.GoalDTO
import com.vurgun.skyfit.core.data.shared.data.model.TagDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class AuthApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val AUTH = "auth"
        const val AUTH_VERIFY_OTP = "auth/otpverify"
        const val AUTH_FORGOT_VERIFY_OTP = "forgot/verify"
        const val AUTH_SEND_OTP = "send/otp"
        const val AUTH_CREATE_PASSWORD = "auth/createpassword"
        const val AUTH_FORGOT_PASSWORD_RESET = "forgot/change/password"
        const val GET_ALL_GOALS = "get/goals"
        const val GET_ALL_TAGS = "get/tags"
    }

    suspend fun login(request: AuthorizationRequest): ApiResult<AuthAccessDTO> {
        return apiClient.safeApiCall<AuthAccessDTO> {
            method = HttpMethod.Post
            url(Endpoint.AUTH)
            setBody(request)
        }
    }

    suspend fun verifyOTP(request: VerifyOTPRequest, token: String): ApiResult<AuthAccessDTO> {
        return apiClient.safeApiCall<AuthAccessDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.AUTH_VERIFY_OTP)
            setBody(request)
        }
    }

    suspend fun forgotPassword(request: AuthorizationRequest) = login(request)

    suspend fun forgotPasswordVerifyOTP(request: VerifyOTPRequest, token: String): ApiResult<AuthAccessDTO> {
        return apiClient.safeApiCall<AuthAccessDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.AUTH_FORGOT_VERIFY_OTP)
            setBody(request)
        }
    }

    suspend fun sendOTP(request: AuthorizationRequest, token: String): ApiResult<AuthAccessDTO> {
        return apiClient.safeApiCall<AuthAccessDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.AUTH_SEND_OTP)
            setBody(request)
        }
    }

    suspend fun createPassword(request: CreatePasswordRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.AUTH_CREATE_PASSWORD)
            setBody(request)
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoint.AUTH_FORGOT_PASSWORD_RESET)
            setBody(request)
        }
    }

    suspend fun getAllGoals(token: String): ApiResult<List<GoalDTO>> {
        return apiClient.safeApiCall<List<GoalDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_ALL_GOALS)
        }
    }

    suspend fun getAllTags(token: String): ApiResult<List<TagDTO>> {
        return apiClient.safeApiCall<List<TagDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(GET_ALL_TAGS)
        }
    }
}
