package com.vurgun.skyfit.core.domain.models

data class User(
    val userId: String,
    val otpCode: String,
    val username: String,
    val role: UserRole
)

enum class UserRole {
    GUEST, USER, TRAINER, FACILITY_MANAGER, ADMIN
}
