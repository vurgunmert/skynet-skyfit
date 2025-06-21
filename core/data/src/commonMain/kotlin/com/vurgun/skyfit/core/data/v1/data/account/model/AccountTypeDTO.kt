package com.vurgun.skyfit.core.data.v1.data.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountTypeDTO(
    val usertypeId: Int,
    val profilePhoto: String?,
    val typeName: String,
    val name: String
)