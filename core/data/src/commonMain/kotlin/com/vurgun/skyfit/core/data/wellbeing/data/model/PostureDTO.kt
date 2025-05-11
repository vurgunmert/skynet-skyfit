package com.vurgun.skyfit.core.data.wellbeing.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PostureAnalysisRequest(
    val image: String, //base64
    val orientation: String // "left", "front", "back", "right"
)