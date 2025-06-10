package com.vurgun.skyfit.core.data.v1.auth.back

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDTO(
    val phone: String,
    val password: String? = null
)

@Serializable
data class AuthResponseDTO(
    val token: String,
    val isNewUser: Boolean? = null,
    val onboardingComplete: Boolean? = null
)

@Serializable
data class VerifyAuthRequestDTO(val otpCode: String)

@Serializable
data class CreatePasswordRequestDTO(
    val username: String,
    val password: String,
    val againPassword: String
)

@Serializable
data class ResetPasswordRequestDTO(
    val password: String,
    val againPassword: String
)