package com.vurgun.skyfit.core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val userId: String,
    val usertype: String
)
