package com.vurgun.skyfit.data.auth.domain.repository

import com.vurgun.skyfit.data.auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.data.auth.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.data.auth.domain.model.CreatePasswordResult
import com.vurgun.skyfit.data.auth.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.data.auth.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.data.auth.domain.model.ResetPasswordResult
import com.vurgun.skyfit.data.auth.domain.model.SendOTPResult
import com.vurgun.skyfit.data.core.storage.Storage

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String?): AuthLoginResult
    suspend fun verifyLoginOTP(code: String): AuthorizationOTPResult
    suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult
    suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult
    suspend fun sendOTP(): SendOTPResult
    suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult
    suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult


    data object UserPhoneNumber : Storage.Key.StringKey("user_phone_number", null)
}
