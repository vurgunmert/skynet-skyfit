package com.vurgun.skyfit.feature_auth.domain.repositories

import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.model.AuthOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.SendOTPResult

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String?): AuthLoginResult
    suspend fun verifyOTP(code: String): AuthOTPResult
    suspend fun sendOTP(): SendOTPResult
    suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult
}
