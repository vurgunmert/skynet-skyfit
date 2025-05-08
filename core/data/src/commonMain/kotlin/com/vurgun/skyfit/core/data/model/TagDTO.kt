package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val tagId: Int,
    val tagName: String,
)
