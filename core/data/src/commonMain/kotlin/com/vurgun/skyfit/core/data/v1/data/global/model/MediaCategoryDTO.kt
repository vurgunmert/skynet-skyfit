package com.vurgun.skyfit.core.data.v1.data.global.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaCategoryDTO(
    val id: String,         // "exercise", "meal", etc.
    val nameEn: String,     // "Exercise"
    val nameTr: String      // "Egzersiz"
)