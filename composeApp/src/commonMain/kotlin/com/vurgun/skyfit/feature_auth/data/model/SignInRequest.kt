package com.vurgun.skyfit.feature_auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val phone: String,
    val password: String? = null
)
