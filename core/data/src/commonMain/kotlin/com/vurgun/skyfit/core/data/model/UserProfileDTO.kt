package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDTO(
    val userId: Int,
    val nmId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val height: Int,
    val heightUnit: Int,
    val weight: Int,
    val weightUnit: Int,
    val bodyTypeId: Int,
    val typeName: String,
    val name: String,
    val surname: String,
    val username: String,
    val gymId: Int?,
    val gymJoinDate: String?
)
