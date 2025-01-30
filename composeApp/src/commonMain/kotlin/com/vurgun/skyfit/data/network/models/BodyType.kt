package com.vurgun.skyfit.data.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class BodyType(
    val englishName: String,
    val turkishName: String,
    val englishShort: String,
    val turkishShort: String
) {
    ECTOMORPH("Ectomorph", "Ektomorf", "Ecto", "Ekto"),
    MESOMORPH("Mesomorph", "Mezomorf", "Meso", "Mezo"),
    ENDOMORPH("Endomorph", "Endomorf", "Endo", "Endo"),
    NOT_DEFINED("Not Defined", "Tanımlanmamış", "N/A", "N/A");

    companion object {
        fun fromDisplayName(name: String): BodyType {
            return entries.find {
                it.englishName.equals(name, ignoreCase = true) ||
                        it.turkishName.equals(name, ignoreCase = true)
            } ?: NOT_DEFINED
        }
    }
}

fun estimateBodyType(weight: Int?, height: Int?, weightUnit: String = "kg", heightUnit: String = "cm", onBMICalculated: (Double) -> Unit): BodyType {
    // Ensure weight and height are not null and height is non-zero
    if (weight == null || height == null || height == 0) return BodyType.NOT_DEFINED

    // Convert weight to kilograms if necessary
    val weightInKg = when (weightUnit.lowercase()) {
        "lb" -> weight / 2.20462 // Convert pounds to kilograms
        "kg" -> weight.toDouble()
        else -> return BodyType.NOT_DEFINED // Invalid weight unit
    }

    // Convert height to meters if necessary
    val heightInMeters = when (heightUnit.lowercase()) {
        "cm" -> height / 100.0 // Convert centimeters to meters
        "ft" -> height * 0.3048 // Convert feet to meters
        else -> return BodyType.NOT_DEFINED // Invalid height unit
    }

    // Calculate BMI (weight in kg / height in meters squared)
    val bmi = weightInKg / (heightInMeters * heightInMeters)
    onBMICalculated.invoke(bmi)
    // Determine body type based on BMI
    return when {
        bmi < 18.5 -> BodyType.ECTOMORPH
        bmi in 18.5..24.9 -> BodyType.MESOMORPH
        bmi >= 25.0 -> BodyType.ENDOMORPH
        else -> BodyType.NOT_DEFINED
    }
}
