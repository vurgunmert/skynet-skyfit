package com.vurgun.skyfit.feature_auth.data

import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_auth.data.model.AuthorizationRequest
import com.vurgun.skyfit.feature_auth.data.model.CreatePasswordRequest
import com.vurgun.skyfit.feature_auth.data.model.ResetPasswordRequest
import com.vurgun.skyfit.feature_auth.data.model.VerifyOTPRequest
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.ResetPasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.SendOTPResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val settingsStore: LocalSettingsStore,
    private val apiService: AuthApiService,
    private val dispatchers: DispatcherProvider
) : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String?): AuthLoginResult =
        withContext(dispatchers.io) {
            val request = AuthorizationRequest(phoneNumber, password.takeUnless { it.isNullOrEmpty() })

            when (val response = apiService.login(request)) {
                is ApiResult.Error -> AuthLoginResult.Error(response.message)
                is ApiResult.Exception -> AuthLoginResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.savePhoneNumber(phoneNumber)
                    settingsStore.saveToken(response.data.token)

                    when {
                        response.data.isNewUser == true -> AuthLoginResult.OTPVerificationRequired
                        password.isNullOrEmpty() -> AuthLoginResult.OTPVerificationRequired
                        else -> AuthLoginResult.Success
                    }
                }
            }
        }

    override suspend fun verifyAuthOTP(code: String): AuthorizationOTPResult =
        withContext(dispatchers.io) {
            val token = settingsStore.getToken() ?: return@withContext AuthorizationOTPResult.Error("Token not found")
            val request = VerifyOTPRequest(code)

            when (val response = apiService.verifyOTP(request, token)) {
                is ApiResult.Error -> AuthorizationOTPResult.Error(response.message)
                is ApiResult.Exception -> AuthorizationOTPResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.saveToken(response.data.token)

                    when {
                        response.data.isNewUser == true -> AuthorizationOTPResult.RegistrationRequired
                        else -> AuthorizationOTPResult.LoginSuccess
                    }
                }
            }
        }

    override suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult =
        withContext(dispatchers.io) {
            val request = AuthorizationRequest(phoneNumber)

            when (val response = apiService.forgotPassword(request)) {
                is ApiResult.Error -> ForgotPasswordResult.Error(response.message)
                is ApiResult.Exception -> ForgotPasswordResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.savePhoneNumber(phoneNumber)
                    settingsStore.saveToken(response.data.token)
                    ForgotPasswordResult.Success
                }
            }
        }

    override suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult =
        withContext(dispatchers.io) {
            val token = settingsStore.getToken() ?: return@withContext ForgotPasswordOTPResult.Error("Token not found")
            val request = VerifyOTPRequest(code)

            when (val response = apiService.forgotPasswordVerifyOTP(request, token)) {
                is ApiResult.Error -> ForgotPasswordOTPResult.Error(response.message)
                is ApiResult.Exception -> ForgotPasswordOTPResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.saveToken(response.data.token)
                    ForgotPasswordOTPResult.Success
                }
            }
        }


    override suspend fun sendOTP(): SendOTPResult =
        withContext(dispatchers.io) {
            val phoneNumber = settingsStore.getPhoneNumber() ?: return@withContext SendOTPResult.Error("Phone Number not found")
            val token = settingsStore.getToken() ?: return@withContext SendOTPResult.Error("Token not found")
            val request = AuthorizationRequest(phoneNumber)

            when (val response = apiService.sendOTP(request, token)) {
                is ApiResult.Error -> SendOTPResult.Error(response.message)
                is ApiResult.Exception -> SendOTPResult.Error(response.exception.message)
                is ApiResult.Success -> SendOTPResult.Success
            }
        }

    override suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult =
        withContext(dispatchers.io) {
            val token = settingsStore.getToken() ?: return@withContext CreatePasswordResult.Error("Token not found")
            val request = CreatePasswordRequest(username, password, againPassword)

            when (val response = apiService.createPassword(request, token)) {
                is ApiResult.Error -> CreatePasswordResult.Error(response.message)
                is ApiResult.Exception -> CreatePasswordResult.Error(response.exception.message)
                is ApiResult.Success -> CreatePasswordResult.Success
            }
        }


    override suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult =
        withContext(dispatchers.io) {
            val token = settingsStore.getToken() ?: return@withContext ResetPasswordResult.Error("Token not found")
            val request = ResetPasswordRequest(password, againPassword)

            when (val response = apiService.resetPassword(request, token)) {
                is ApiResult.Error -> ResetPasswordResult.Error(response.message)
                is ApiResult.Exception -> ResetPasswordResult.Error(response.exception.message)
                is ApiResult.Success -> ResetPasswordResult.Success
            }
        }

}
