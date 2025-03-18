package com.vurgun.skyfit.core.domain.models

data class UserDetail(
    val userId: Int,
    val userType: UserType,
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
