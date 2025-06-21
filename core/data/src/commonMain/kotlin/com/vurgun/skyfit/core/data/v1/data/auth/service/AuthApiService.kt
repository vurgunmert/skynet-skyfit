package com.vurgun.skyfit.core.data.v1.data.auth.service

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.AuthRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.AuthResponseDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.ChangePasswordRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.CreatePasswordRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.ResetPasswordRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.VerifyAuthRequestDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class AuthApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val LOGIN = "auth/login"
        const val LOGIN_VERIFY_OTP = "auth/login/verify"
        const val FORGOT_VERIFY_OTP = "auth/forgot-password/verify"
        const val RESEND_OTP = "auth/create-password"
        const val AUTH_CREATE_PASSWORD = "auth/createpassword"
        const val AUTH_FORGOT_PASSWORD_RESET = "forgot/change/password"
        const val AUTH_CHANGE_PASSWORD = "auth/change-password"
    }

    suspend fun login(request: AuthRequestDTO): ApiResult<AuthResponseDTO> {
        return apiClient.safeApiCall<AuthResponseDTO> {
            method = HttpMethod.Companion.Post
            url(Endpoint.LOGIN)
            setBody(request)
        }
    }

    suspend fun verifyOTP(request: VerifyAuthRequestDTO, token: String): ApiResult<AuthResponseDTO> {
        return apiClient.safeApiCall<AuthResponseDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoint.LOGIN_VERIFY_OTP)
            setBody(request)
        }
    }

    suspend fun forgotPassword(request: AuthRequestDTO) = login(request)

    suspend fun forgotPasswordVerifyOTP(request: VerifyAuthRequestDTO, token: String): ApiResult<AuthResponseDTO> {
        return apiClient.safeApiCall<AuthResponseDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoint.FORGOT_VERIFY_OTP)
            setBody(request)
        }
    }

    suspend fun sendOTP(request: AuthRequestDTO, token: String): ApiResult<AuthResponseDTO> {
        return apiClient.safeApiCall<AuthResponseDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoint.RESEND_OTP)
            setBody(request)
        }
    }

    suspend fun createPassword(request: CreatePasswordRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoint.AUTH_CREATE_PASSWORD)
            setBody(request)
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Put
            bearerAuth(token)
            url(Endpoint.AUTH_FORGOT_PASSWORD_RESET)
            setBody(request)
        }
    }

    suspend fun changePassword(request: ChangePasswordRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Put
            bearerAuth(token)
            url(Endpoint.AUTH_CHANGE_PASSWORD)
            setBody(request)
        }
    }
}