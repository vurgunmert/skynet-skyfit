package com.vurgun.skyfit.core.data.v1.domain.measurement.model

import kotlinx.datetime.LocalDateTime

data class Measurement(
    val measurementId: Int,
    val categoryId: Int,
    val value: Int,
    val unitId: Int? = null,
    val unitName: String? = null,
    val createdDate: LocalDateTime,
)

enum class MeasurementCategory(
    val id: Int,
    val title: String,
    val type: Type,
    val unitId: Int? = null
) {
    HEIGHT(1, "Boy", Type.Body, 3),
    WEIGHT(2, "Kilo", Type.Body, 1),
    SHOULDER(3, "Omuz", Type.Body, 3),
    RIGHT_ARM(4, "Sağ Kol", Type.Body, 3),
    LEFT_ARM(5, "Sol Kol", Type.Body, 3),
    CHEST(6, "Göğüs", Type.Body, 3),
    WAIST(7, "Bel", Type.Body, 3),
    HIP(8, "Kalça", Type.Body, 3),
    RIGHT_LEG(9, "Sağ Bacak", Type.Body, 3),
    LEFT_LEG(10, "Sol Bacak", Type.Body, 3),
    SIT_AND_REACH(11, "Sit and Reach Test", Type.FitnessTest, 3),
    PUSH_UP(12, "Push Up Test", Type.FitnessTest, null), // Tekrar
    WHR(13, "Bel ve Kalça Oranı", Type.Calculated, null), // Oran
    BMI(14, "Vücut Kitle Endeksi", Type.Calculated, null); // Unitless

    enum class Type {
        Body, FitnessTest, Calculated
    }

    companion object {
        fun from(id: Int): MeasurementCategory? = entries.find { it.id == id }
        fun all(): List<MeasurementCategory> = entries
        fun bodyMeasurements() = entries.filter { it.type == Type.Body }
        fun fitnessTests() = entries.filter { it.type == Type.FitnessTest }
        fun calculated() = entries.filter { it.type == Type.Calculated }
    }
}
