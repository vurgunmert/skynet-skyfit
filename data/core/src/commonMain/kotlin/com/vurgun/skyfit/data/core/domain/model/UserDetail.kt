package com.vurgun.skyfit.data.core.domain.model


data class UserDetail(
    val userId: Int,
    val userRole: UserRole,
    val characterType: CharacterType?,
    val username: String,
    val phone: String,
    val birthday: String?,
    val gymName: String?,
    val gymAddress: String?,
    val bio: String?,
    val backgroundImagePath: String?,
    val email: String? // Renamed `mail` to `email` for consistency
)
