package com.vurgun.skyfit.feature_auth.domain.usecases

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext

class AuthRequestOTPCodeUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(phoneNumber: String): NetworkResponseWrapper<Boolean> {
        return withContext(dispatchers.io) {
            authRepository.requestOtpCode("+90$phoneNumber")
        }
    }
}
