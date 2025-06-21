package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Represents a single recorded measurement entry for a user.
 */

@Serializable
data class MeasurementDTO(
    val id: String, // Unique ID of the measurement entry (UUID or DB ID)

    val userId: String, // ID of the user who recorded the measurement

    val typeCode: String, // Unique identifier for the measurement type (e.g., "waist", "bmi")

    val value: Double, // Recorded numeric value of the measurement

    val unit: String?, // Optional unit override (e.g., "cm", "kg", "ratio")

    val groupId: String? = null, // Optional ID of the group this measurement belongs to

    val recordedAt: String // ISO 8601 formatted timestamp when the measurement was taken
)
