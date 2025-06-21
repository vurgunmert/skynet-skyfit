package com.vurgun.skyfit.core.data.v1.data.account.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequestDTO(
    val currentPassword: String,
    val newPassword: String,
    val newPasswordAgain: String
)