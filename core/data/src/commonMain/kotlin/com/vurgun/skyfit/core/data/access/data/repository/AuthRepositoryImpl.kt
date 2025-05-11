package com.vurgun.skyfit.core.data.access.data.repository

import com.vurgun.skyfit.core.data.access.domain.model.AuthLoginResult
import com.vurgun.skyfit.core.data.access.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.core.data.access.domain.model.CreatePasswordResult
import com.vurgun.skyfit.core.data.access.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.core.data.access.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.core.data.shared.domain.model.MissingTokenException
import com.vurgun.skyfit.core.data.access.domain.model.ResetPasswordResult
import com.vurgun.skyfit.core.data.access.domain.model.SendOTPResult
import com.vurgun.skyfit.core.data.persona.domain.model.UserGoal
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutTag
import com.vurgun.skyfit.core.data.access.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.access.data.model.AuthorizationRequest
import com.vurgun.skyfit.core.data.access.data.model.CreatePasswordRequest
import com.vurgun.skyfit.core.data.access.data.model.ResetPasswordRequest
import com.vurgun.skyfit.core.data.access.data.model.VerifyOTPRequest
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String?): AuthLoginResult =
        withContext(dispatchers.io) {
            val request = AuthorizationRequest(phoneNumber, password.takeUnless { it.isNullOrEmpty() })

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
            val request = VerifyOTPRequest(code)

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
        val request = AuthorizationRequest(phoneNumber)

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
            val request = VerifyOTPRequest(code)

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
                storage.get(AuthRepository.UserPhoneNumber) ?: return@withContext SendOTPResult.Error("Phone Number not found")
            val request = AuthorizationRequest(phoneNumber)

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
            val request = CreatePasswordRequest(username, password, againPassword)

            when (val response = apiService.createPassword(request, token)) {
                is ApiResult.Error -> CreatePasswordResult.Error(response.message)
                is ApiResult.Exception -> CreatePasswordResult.Error(response.exception.message)
                is ApiResult.Success -> CreatePasswordResult.Success
            }
        } catch (e: Exception) {
            CreatePasswordResult.Error(e.message)
        }
    }


    override suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            val request = ResetPasswordRequest(password, againPassword)

            when (val response = apiService.resetPassword(request, token)) {
                is ApiResult.Error -> ResetPasswordResult.Error(response.message)
                is ApiResult.Exception -> ResetPasswordResult.Error(response.exception.message)
                is ApiResult.Success -> ResetPasswordResult.Success
            }
        } catch (e: Exception) {
            ResetPasswordResult.Error(e.message)
        }
    }

    override suspend fun getGoals() = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAllGoals(token).mapOrThrow { list -> list.map { UserGoal(it.goalId, it.goalName) } }
    }

    override suspend fun getTags() = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAllTags(token).mapOrThrow { list -> list.map { WorkoutTag(it.tagId, it.tagName) } }
    }
}
