package com.vurgun.skyfit.feature_auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPRequest(val otpCode: String)