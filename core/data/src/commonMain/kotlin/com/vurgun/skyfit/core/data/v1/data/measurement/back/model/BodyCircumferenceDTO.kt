package com.vurgun.skyfit.core.data.v1.measurement.back.model

import kotlinx.serialization.Serializable

/**
 * Helper class to input all body circumference metrics in one go.
 */

@Serializable
data class BodyCircumferenceDTO(
    val shoulder: Double? = null,  // Shoulder width (cm)

    val chest: Double? = null,     // Chest circumference (cm)

    val waist: Double? = null,     // Waist circumference (cm)

    val hip: Double? = null,       // Hip circumference (cm)

    val leftArm: Double? = null,   // Left arm circumference (cm)

    val rightArm: Double? = null,  // Right arm circumference (cm)

    val leftLeg: Double? = null,   // Left leg circumference (cm)

    val rightLeg: Double? = null   // Right leg circumference (cm)
)
