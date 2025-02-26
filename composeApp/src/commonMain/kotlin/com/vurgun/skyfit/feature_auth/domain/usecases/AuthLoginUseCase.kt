package com.vurgun.skyfit.feature_auth.domain.usecases

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext

class AuthLoginUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(phoneNumber: String, password: String): AuthLoginResult {
        return withContext(dispatchers.io) {
            when (val response = authRepository.login("+90$phoneNumber", password)) {
                is NetworkResponseWrapper.Error -> AuthLoginResult.Error(response.message)
                is NetworkResponseWrapper.Success -> {
                    if (response.data.verified && response.data.registered) {
                        AuthLoginResult.Success
                    } else if (!response.data.verified && response.data.registered) {
                        AuthLoginResult.AwaitingOTPLogin
                    } else if (!response.data.verified && !response.data.registered) {
                        AuthLoginResult.AwaitingOTPRegister
                    } else {
                        AuthLoginResult.Error(response.message)
                    }
                }
            }
        }
    }
}
