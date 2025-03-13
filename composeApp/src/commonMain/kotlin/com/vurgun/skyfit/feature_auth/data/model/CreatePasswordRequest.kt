package com.vurgun.skyfit.feature_auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePasswordRequest(
    val username: String,
    val password: String,
    val againPassword: String
)