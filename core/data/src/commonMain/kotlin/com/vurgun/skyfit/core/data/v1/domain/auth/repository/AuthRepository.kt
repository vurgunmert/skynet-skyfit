package com.vurgun.skyfit.core.data.v1.domain.auth.repository

import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.v1.domain.auth.model.*

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String?): AuthLoginResult
    suspend fun verifyLoginOTP(code: String): AuthorizationOTPResult
    suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult
    suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult
    suspend fun sendOTP(): SendOTPResult
    suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult
    suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult

    data object UserPhoneNumber : Storage.Key.StringKey("user_phone_number", null)

    suspend fun changePassword(old: String, new: String, again: String): Result<Unit>
}