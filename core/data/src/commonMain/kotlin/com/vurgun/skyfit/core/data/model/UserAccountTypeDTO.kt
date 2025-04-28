package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAccountTypeDTO(
    val usertypeId: Int,
    val profilePhoto: String?,
    val typeName: String,
    val name: String
)