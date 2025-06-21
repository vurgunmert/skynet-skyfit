package com.vurgun.skyfit.core.data.v1.usermedia.back.model

import kotlinx.serialization.Serializable

@Serializable
data class UserMediaItemDTO(
    val id: String,
    val userId: String,
    val categoryId: String,             // "exercise", "meal", etc.
    val path: String,
    val uploadedAt: String,
    val description: String? = null,
    val tags: List<String> = emptyList()
)