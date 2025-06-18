package com.vurgun.skyfit.core.data.v1.data.auth.repository

import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.auth.model.AuthRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.CreatePasswordRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.ResetPasswordRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.model.VerifyAuthRequestDTO
import com.vurgun.skyfit.core.data.v1.data.auth.service.AuthApiService
import com.vurgun.skyfit.core.data.v1.domain.auth.model.*
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.MissingTokenException
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String?): AuthLoginResult =
        withContext(dispatchers.io) {
            val request = AuthRequestDTO(phoneNumber, password.takeUnless { it.isNullOrEmpty() })

            when (val response = apiService.login(request)) {
                is ApiResult.Error -> AuthLoginResult.Error(response.message)
                is ApiResult.Exception -> AuthLoginResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    storage.writeValue(AuthRepository.UserPhoneNumber, phoneNumber)
                    tokenManager.setToken(response.data.token)
                    tokenManager.waitUntilTokenReady()

                    when {
                        password.isNullOrEmpty() || response.data.isNewUser == true -> AuthLoginResult.OTPVerificationRequired
                        response.data.onboardingComplete == false -> AuthLoginResult.OnboardingRequired
                        else -> AuthLoginResult.Success
                    }
                }
            }
        }

    override suspend fun verifyLoginOTP(code: String): AuthorizationOTPResult = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            val request = VerifyAuthRequestDTO(code)

            when (val response = apiService.verifyOTP(request, token)) {
                is ApiResult.Error -> AuthorizationOTPResult.Error(response.message)
                is ApiResult.Exception -> AuthorizationOTPResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    tokenManager.setToken(response.data.token)
                    tokenManager.waitUntilTokenReady()

                    return@withContext when {
                        response.data.isNewUser == true -> AuthorizationOTPResult.RegistrationRequired
                        response.data.onboardingComplete == false -> AuthorizationOTPResult.OnboardingRequired
                        else -> AuthorizationOTPResult.LoginSuccess
                    }
                }
            }
        } catch (e: Exception) {
            return@withContext AuthorizationOTPResult.Error(e.message)
        }
    }

    override suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult = withContext(dispatchers.io) {
        val request = AuthRequestDTO(phoneNumber)

        when (val response = apiService.forgotPassword(request)) {
            is ApiResult.Error -> ForgotPasswordResult.Error(response.message)
            is ApiResult.Exception -> ForgotPasswordResult.Error(response.exception.message)
            is ApiResult.Success -> {
                storage.writeValue(AuthRepository.UserPhoneNumber, phoneNumber)
                tokenManager.setToken(response.data.token)
                tokenManager.waitUntilTokenReady()
                ForgotPasswordResult.Success
            }
        }
    }

    override suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            val request = VerifyAuthRequestDTO(code)

            when (val response = apiService.forgotPasswordVerifyOTP(request, token)) {
                is ApiResult.Error -> ForgotPasswordOTPResult.Error(response.message)
                is ApiResult.Exception -> ForgotPasswordOTPResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    tokenManager.setToken(response.data.token)
                    tokenManager.waitUntilTokenReady()
                    ForgotPasswordOTPResult.Success
                }
            }
        } catch (e: MissingTokenException) {
            return@withContext ForgotPasswordOTPResult.Error(e.message)
        }
    }


    override suspend fun sendOTP(): SendOTPResult = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            val phoneNumber =
                storage.get(AuthRepository.UserPhoneNumber)
                    ?: return@withContext SendOTPResult.Error("Phone Number not found")
            val request = AuthRequestDTO(phoneNumber)

            when (val response = apiService.sendOTP(request, token)) {
                is ApiResult.Error -> SendOTPResult.Error(response.message)
                is ApiResult.Exception -> SendOTPResult.Error(response.exception.message)
                is ApiResult.Success -> SendOTPResult.Success
            }
        } catch (e: Exception) {
            return@withContext SendOTPResult.Error(e.message)
        }
    }

    override suspend fun createPassword(
        username: String,
        password: String,
        againPassword: String
    ): CreatePasswordResult = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            val request = CreatePasswordRequestDTO(username, password, againPassword)

            when (val response = apiService.createPassword(request, token)) {
                is ApiResult.Error -> CreatePasswordResult.Error(response.message)
                is ApiResult.Exception -> CreatePasswordResult.Error(response.exception.message)
                is ApiResult.Success -> CreatePasswordResult.Success
            }
        } catch (e: Exception) {
            CreatePasswordResult.Error(e.message)
        }
    }


    override suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult =
        withContext(dispatchers.io) {
            try {
                val token = tokenManager.getTokenOrThrow()
                val request = ResetPasswordRequestDTO(password, againPassword)

                when (val response = apiService.resetPassword(request, token)) {
                    is ApiResult.Error -> ResetPasswordResult.Error(response.message)
                    is ApiResult.Exception -> ResetPasswordResult.Error(response.exception.message)
                    is ApiResult.Success -> ResetPasswordResult.Success
                }
            } catch (e: Exception) {
                ResetPasswordResult.Error(e.message)
            }
        }
}