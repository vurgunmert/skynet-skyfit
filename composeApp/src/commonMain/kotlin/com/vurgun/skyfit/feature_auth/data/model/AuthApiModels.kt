package com.vurgun.skyfit.feature_auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationRequest(
    val phone: String,
    val password: String? = null
)

@Serializable
data class VerifyOTPRequest(val otpCode: String)

@Serializable
data class AuthorizationResponse(
    val token: String,
    val isNewUser: Boolean? = null
)

@Serializable
data class CreatePasswordRequest(
    val username: String,
    val password: String,
    val againPassword: String
)

@Serializable
data class ResetPasswordRequest(
    val password: String,
    val againPassword: String
)