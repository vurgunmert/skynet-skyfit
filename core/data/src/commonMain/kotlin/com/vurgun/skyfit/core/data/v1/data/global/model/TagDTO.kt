package com.vurgun.skyfit.core.data.v1.data.global.model

import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val tagId: String,
    val tagName: String,
)
