package com.vurgun.skyfit.core.data.v1.data.global.model

data class UnitDescriptionDTO(
    val id: Int,
    val shortName: String,   // e.g., "kg"
    val fullName: String,    // e.g., "Kilogram"
    val system: String       // e.g., "metric"
)
