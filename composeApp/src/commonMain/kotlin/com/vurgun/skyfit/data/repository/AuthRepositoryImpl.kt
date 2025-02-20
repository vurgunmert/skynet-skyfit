package com.vurgun.skyfit.data.repository

import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.util.ResultWrapper
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun authenticatePhoneNumber(phoneNumber: String): ResultWrapper<Boolean> {
        // Simulate API call
        delay(2000) // Simulating network delay
        return if (phoneNumber.startsWith("+905")) {
            ResultWrapper.Success(true) // Simulated success for Turkish numbers
        } else {
            ResultWrapper.Error("Unsupported phone number.")
        }
    }
}
