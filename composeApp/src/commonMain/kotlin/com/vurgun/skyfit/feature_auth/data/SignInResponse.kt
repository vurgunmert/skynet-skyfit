package com.vurgun.skyfit.feature_auth.data

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val verified: Boolean = false,
    val registered: Boolean = false
)