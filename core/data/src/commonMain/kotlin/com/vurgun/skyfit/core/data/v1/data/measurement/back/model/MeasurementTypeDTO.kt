package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Defines metadata for each supported measurement type.
 */

@Serializable
data class MeasurementTypeDTO(
    val id: Int, // Unique internal ID of the measurement type

    val code: String, // Unique code to reference this measurement type ("waist", "sit_and_reach")

    val name: String, // Human-readable name of the measurement ("Waist Circumference")

    val unit: String?, // Default unit used for this type (e.g., "cm", "kg", "ratio")

    val category: String, // Logical grouping ("circumference", "flexibility", "calculated")

    val isManualEntryAllowed: Boolean = true // Whether this measurement can be manually entered by the user
)
