package com.vurgun.skyfit.feature_auth.domain.repositories

import com.vurgun.skyfit.feature_auth.data.SignInResponse
import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper

interface AuthRepository {
    suspend fun register(phoneNumber: String, fullName: String, password: String): NetworkResponseWrapper<Unit>
    suspend fun login(phoneNumber: String, password: String): NetworkResponseWrapper<SignInResponse>
    suspend fun requestOtpCode(phoneNumber: String): NetworkResponseWrapper<Boolean>
    suspend fun verifyOtpCode(phoneNumber: String, code: String): NetworkResponseWrapper<SignInResponse>
}
