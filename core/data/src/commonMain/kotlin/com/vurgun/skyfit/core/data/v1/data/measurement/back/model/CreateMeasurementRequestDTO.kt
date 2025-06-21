package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Request body to create a single measurement entry manually.
 */

@Serializable
data class CreateMeasurementRequestDTO(
    val userId: String, // User ID to whom the measurement belongs

    val typeCode: String, // Measurement type code (e.g., "bmi", "hip", "shoulder")

    val value: Double, // The actual value measured

    val unitOverride: String? = null, // Optional custom unit if different than default

    val groupId: String? = null, // Optional group ID to group multiple measurements together

    val recordedAt: String = "" // Time when the value was measured
)
