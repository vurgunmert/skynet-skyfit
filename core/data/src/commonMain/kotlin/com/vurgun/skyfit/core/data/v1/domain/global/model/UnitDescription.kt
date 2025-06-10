package com.vurgun.skyfit.core.data.v1.domain.global.model

data class UnitDescription(
    val id: Int,
    val shortName: String,   // e.g., "kg"
    val fullName: String,    // e.g., "Kilogram"
    val system: String       // e.g., "metric"
)