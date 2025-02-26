package com.vurgun.skyfit.feature_auth.domain.usecases

import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext

class AuthRegisterUseCase(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider
) {
    suspend fun execute(
        phoneNumber: String,
        fullName: String,
        password: String
    ): NetworkResponseWrapper<Unit> {
        return withContext(dispatchers.io) {
            authRepository.register("+90$phoneNumber", fullName, password)
        }
    }
}
