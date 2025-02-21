package com.vurgun.skyfit.domain.usecases.auth

import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.util.ResultWrapper
import com.vurgun.skyfit.domain.util.DispatcherProvider
import kotlinx.coroutines.withContext

class ResendOTPUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(phoneNumber: String): ResultWrapper<Boolean> {
        return withContext(dispatchers.io) {
            authRepository.resendOtp("+90$phoneNumber")
        }
    }
}
