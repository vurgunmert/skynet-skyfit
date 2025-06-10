package com.vurgun.skyfit.core.data.v1.data.posture.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PostureAnalyseRequestDTO(
    val image: String, //base64
    val orientation: String // "left", "front", "back", "right"
)