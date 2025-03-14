package com.vurgun.skyfit.feature_auth.domain.repositories

import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.ResetPasswordResult
import com.vurgun.skyfit.feature_auth.domain.model.SendOTPResult

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String?): AuthLoginResult
    suspend fun verifyAuthOTP(code: String): AuthorizationOTPResult
    suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult
    suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult
    suspend fun sendOTP(): SendOTPResult
    suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult
    suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult
}
