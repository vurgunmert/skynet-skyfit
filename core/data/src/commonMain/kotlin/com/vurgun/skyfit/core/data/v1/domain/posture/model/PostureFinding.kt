package com.vurgun.skyfit.core.data.v1.domain.posture.model

import kotlinx.serialization.Serializable

@Serializable
data class PostureFinding(
    val key: String,
    val detected: Boolean,
    val explanation: String,
)