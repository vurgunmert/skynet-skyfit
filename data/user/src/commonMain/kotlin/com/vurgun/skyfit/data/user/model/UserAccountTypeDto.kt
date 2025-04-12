package com.vurgun.skyfit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAccountTypeDto(
    val usertypeId: Int,
    val profilePhoto: String?,
    val typeName: String,
    val name: String
)