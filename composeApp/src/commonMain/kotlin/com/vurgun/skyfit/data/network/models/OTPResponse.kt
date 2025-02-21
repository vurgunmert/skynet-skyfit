package com.vurgun.skyfit.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class OTPResponse(val registered: Boolean)