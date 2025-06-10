package com.vurgun.skyfit.core.data.v1.domain.global.model

data class MediaCategory(
    val id: String,                    // Original ID from API
    val nameEn: String,
    val nameTr: String,
    val type: MediaCategoryType        // Enum representation
)
