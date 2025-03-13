package com.vurgun.skyfit.feature_auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPResponse(
    val token: String,
    val isNewUser: Boolean
)