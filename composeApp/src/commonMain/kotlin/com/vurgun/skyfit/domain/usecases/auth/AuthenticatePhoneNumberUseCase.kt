package com.vurgun.skyfit.domain.usecases.auth

import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.util.DispatcherProvider
import com.vurgun.skyfit.domain.util.ResultWrapper
import kotlinx.coroutines.withContext

class AuthenticatePhoneNumberUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(phoneNumber: String): ResultWrapper<Boolean> {
        if (phoneNumber.length != 10) {
            return ResultWrapper.Error("Invalid phone number format.")
        }

        return withContext(dispatchers.io) {
            authRepository.authenticatePhoneNumber("+90$phoneNumber")
        }
    }
}
