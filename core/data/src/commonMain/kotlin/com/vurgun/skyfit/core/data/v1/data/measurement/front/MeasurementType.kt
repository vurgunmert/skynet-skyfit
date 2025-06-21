package com.vurgun.skyfit.core.data.v1.measurement.front;

import com.vurgun.skyfit.core.data.v1.data.measurement.front.MeasurementCategory

/**
 * Represents all supported measurement types in the domain.
 * Used to validate, label, and handle logic for measurement entries.
 */
enum class MeasurementType(
    val code: String,
    val displayName: String,
    val defaultUnit: String?,            // "cm", "kg", "ratio", etc.
    val category: MeasurementCategory,   // grouping logic
    val isManuallyEnterable: Boolean = true
) {
    // Ratio
    WAIST_HIP_RATIO("waist_hip_ratio", "Waist to Hip Ratio", "ratio", MeasurementCategory.RATIO),

    // Flexibility
    SIT_AND_REACH("sit_and_reach", "Sit & Reach", "cm", MeasurementCategory.FLEXIBILITY),

    // Index
    BMI("bmi", "Body Mass Index", null, MeasurementCategory.CALCULATED, isManuallyEnterable = false),

    // Size
    HEIGHT("height", "Height", "cm", MeasurementCategory.BODY_SIZE),
    WEIGHT("weight", "Weight", "kg", MeasurementCategory.BODY_SIZE),

    // Circumference
    SHOULDER("shoulder", "Shoulder Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    CHEST("chest", "Chest Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    WAIST("waist", "Waist Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    HIP("hip", "Hip Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    LEFT_ARM("left_arm", "Left Arm Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    RIGHT_ARM("right_arm", "Right Arm Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    LEFT_LEG("left_leg", "Left Leg Circumference", "cm", MeasurementCategory.CIRCUMFERENCE),
    RIGHT_LEG("right_leg", "Right Leg Circumference", "cm", MeasurementCategory.CIRCUMFERENCE);

    companion object {
        fun fromCode(code: String): MeasurementType? =
            values().find { it.code == code }

        fun byCategory(category: MeasurementCategory): List<MeasurementType> =
            values().filter { it.category == category }
    }
}
