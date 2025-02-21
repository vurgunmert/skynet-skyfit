package com.vurgun.skyfit.domain.usecases.auth

import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.util.DispatcherProvider
import com.vurgun.skyfit.domain.util.ResultWrapper
import kotlinx.coroutines.withContext

class RegisterUserUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(
        phoneNumber: String,
        fullName: String,
        password: String
    ): ResultWrapper<Unit> {
        return withContext(dispatchers.io) {
            authRepository.registerUser("+90$phoneNumber", fullName, password)
        }
    }
}
