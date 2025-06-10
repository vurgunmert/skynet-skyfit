package com.vurgun.skyfit.core.data.v1.data.global.model

data class TypeDescriptionDTO(
    val id: Int,
    val shortName: String,   // e.g., "M"
    val fullName: String,    // e.g., "Male"
    val localizedName: String // e.g., "Erkek"
)
