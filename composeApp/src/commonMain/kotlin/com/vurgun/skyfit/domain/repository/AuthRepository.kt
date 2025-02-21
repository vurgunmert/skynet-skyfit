package com.vurgun.skyfit.domain.repository

import com.vurgun.skyfit.data.network.models.OTPResponse
import com.vurgun.skyfit.domain.usecases.auth.AuthLoginResult
import com.vurgun.skyfit.domain.util.ResultWrapper

interface AuthRepository {
    suspend fun authenticatePhoneNumber(phoneNumber: String): AuthLoginResult
    suspend fun registerUser(phoneNumber: String, fullName: String, password: String): ResultWrapper<Unit>
    suspend fun verifyOtp(phoneNumber: String, otp: String): ResultWrapper<OTPResponse>
    suspend fun resendOtp(phoneNumber: String): ResultWrapper<Boolean>
}
