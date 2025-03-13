package com.vurgun.skyfit.core.domain.model

data class User(
    val userId: String,
    val otpCode: String,
    val username: String,
    val role: UserType
)