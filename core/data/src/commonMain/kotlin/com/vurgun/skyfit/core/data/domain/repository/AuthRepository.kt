package com.vurgun.skyfit.core.data.domain.repository

import com.vurgun.skyfit.core.data.domain.model.AuthLoginResult
import com.vurgun.skyfit.core.data.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.core.data.domain.model.CreatePasswordResult
import com.vurgun.skyfit.core.data.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.core.data.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.core.data.domain.model.ResetPasswordResult
import com.vurgun.skyfit.core.data.domain.model.SendOTPResult
import com.vurgun.skyfit.core.data.domain.model.UserGoal
import com.vurgun.skyfit.core.data.domain.model.WorkoutTag
import com.vurgun.skyfit.core.data.storage.Storage

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String?): AuthLoginResult
    suspend fun verifyLoginOTP(code: String): AuthorizationOTPResult
    suspend fun forgotPassword(phoneNumber: String): ForgotPasswordResult
    suspend fun verifyForgotPasswordOTP(code: String): ForgotPasswordOTPResult
    suspend fun sendOTP(): SendOTPResult
    suspend fun createPassword(username: String, password: String, againPassword: String): CreatePasswordResult
    suspend fun resetPassword(password: String, againPassword: String): ResetPasswordResult


    data object UserPhoneNumber : Storage.Key.StringKey("user_phone_number", null)

    suspend fun getTags(): Result<List<WorkoutTag>>
    suspend fun getGoals(): Result<List<UserGoal>>
}
