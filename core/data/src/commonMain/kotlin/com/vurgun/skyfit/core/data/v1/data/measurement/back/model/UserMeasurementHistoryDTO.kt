package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Represents all measurements taken by a user across all types.
 */

@Serializable
data class UserMeasurementHistoryDTO(
    val userId: String, // ID of the user whose history this is

    val measurements: List<MeasurementDTO> // Full list of measurements recorded
)
