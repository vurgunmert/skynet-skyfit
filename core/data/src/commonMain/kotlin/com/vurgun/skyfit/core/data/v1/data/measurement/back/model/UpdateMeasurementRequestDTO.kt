package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Request to update an existing measurement entry.
 */

@Serializable
data class UpdateMeasurementRequestDTO(
    val measurementId: String, // ID of the measurement to be updated

    val newValue: Double? = null, // New value (if updated)

    val newUnit: String? = null, // Optional new unit override

    val newRecordedAt: String? = null // Optional new timestamp
)
