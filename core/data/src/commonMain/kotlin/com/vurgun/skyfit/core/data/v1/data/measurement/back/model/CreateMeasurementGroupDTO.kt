package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Used to create a group of measurements with a shared timestamp (e.g. full-body scan).
 */

@Serializable
data class CreateMeasurementGroupDTO(
    val userId: String, // The user who owns the group of measurements

    val groupName: String? = null, // Optional display name for the group (e.g., "June Body Check")

    val recordedAt: String = "", // Shared timestamp for all entries

    val measurements: List<CreateMeasurementRequestDTO> // List of individual measurement inputs
)
