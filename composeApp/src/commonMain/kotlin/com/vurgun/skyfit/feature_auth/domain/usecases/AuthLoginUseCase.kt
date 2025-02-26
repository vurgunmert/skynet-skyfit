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
    suspend fun execute(phoneNumber: String, otp: String? = null): AuthLoginResult {
        return withContext(dispatchers.io) {
            when (val response = authRepository.login("+90$phoneNumber", otp)) {
                is NetworkResponseWrapper.Error -> AuthLoginResult.Error(response.message)
                is NetworkResponseWrapper.Success -> AuthLoginResult.AwaitingOTPLogin //TODO: How register status detected
            }
        }
    }
}
