package com.vurgun.skyfit.feature_auth.data

import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_auth.data.model.CreatePasswordRequest
import com.vurgun.skyfit.feature_auth.data.model.LoginRequest
import com.vurgun.skyfit.feature_auth.data.model.SendOTPRequest
import com.vurgun.skyfit.feature_auth.data.model.VerifyOTPRequest
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.model.AuthOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
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
            val request = LoginRequest(phoneNumber, password.takeUnless { it.isNullOrEmpty() })

            when (val response = apiService.login(request)) {
                is ApiResult.Error -> AuthLoginResult.Error(response.message)
                is ApiResult.Exception -> AuthLoginResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.savePhoneNumber(phoneNumber)
                    settingsStore.saveToken(response.data.token)

                    when {
                        response.data.isNewUser -> AuthLoginResult.AwaitingOTPRegister
                        password.isNullOrEmpty() -> AuthLoginResult.AwaitingOTPLogin
                        else -> AuthLoginResult.Success
                    }
                }
            }
        }

    override suspend fun verifyOTP(code: String): AuthOTPResult =
        withContext(dispatchers.io) {
            val token = settingsStore.getToken() ?: return@withContext AuthOTPResult.Error("Token not found")
            val request = VerifyOTPRequest(code)

            when (val response = apiService.verifyOTP(request, token)) {
                is ApiResult.Error -> AuthOTPResult.Error(response.message)
                is ApiResult.Exception -> AuthOTPResult.Error(response.exception.message)
                is ApiResult.Success -> {
                    settingsStore.saveToken(response.data.token)

                    when {
                        response.data.isNewUser -> AuthOTPResult.RegisterSuccess
                        else -> AuthOTPResult.LoginSuccess
                    }
                }
            }
        }


    override suspend fun sendOTP(): SendOTPResult =
        withContext(dispatchers.io) {
            val phoneNumber = settingsStore.getPhoneNumber() ?: return@withContext SendOTPResult.Error("Phone Number not found")
            val token = settingsStore.getToken() ?: return@withContext SendOTPResult.Error("Token not found")
            val request = SendOTPRequest(phoneNumber)

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

}
