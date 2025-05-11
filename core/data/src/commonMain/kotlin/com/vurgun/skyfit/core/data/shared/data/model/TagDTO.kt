package com.vurgun.skyfit.core.data.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val tagId: Int,
    val tagName: String,
)
