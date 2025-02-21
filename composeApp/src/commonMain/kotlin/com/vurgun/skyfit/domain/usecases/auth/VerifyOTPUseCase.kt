package com.vurgun.skyfit.domain.usecases.auth

import com.vurgun.skyfit.data.network.models.OTPResponse
import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.util.ResultWrapper
import com.vurgun.skyfit.domain.util.DispatcherProvider
import kotlinx.coroutines.withContext

class VerifyOTPUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(phoneNumber: String, otp: String): ResultWrapper<OTPResponse> {
        return withContext(dispatchers.io) {
            authRepository.verifyOtp("+90$phoneNumber", otp)
        }
    }
}
