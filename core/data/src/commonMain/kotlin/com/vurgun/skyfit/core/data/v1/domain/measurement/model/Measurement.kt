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
    val unitId: Int? = null,
    val hint: String
) {
    HEIGHT(1, "Boy", Type.Body, 3, "175 cm"),
    WEIGHT(2, "Kilo", Type.Body, 1, "70 kg"),
    SHOULDER(3, "Omuz", Type.Body, 3, "45 cm"),
    RIGHT_ARM(4, "Sağ Kol", Type.Body, 3, "32 cm"),
    LEFT_ARM(5, "Sol Kol", Type.Body, 3, "31 cm"),
    CHEST(6, "Göğüs", Type.Body, 3, "100 cm"),
    WAIST(7, "Bel", Type.Body, 3, "80 cm"),
    HIP(8, "Kalça", Type.Body, 3, "95 cm"),
    RIGHT_LEG(9, "Sağ Bacak", Type.Body, 3, "54 cm"),
    LEFT_LEG(10, "Sol Bacak", Type.Body, 3, "53 cm"),
    SIT_AND_REACH(11, "Sit and Reach Test", Type.FitnessTest, 3, "28 cm"),
    PUSH_UP(12, "Push Up Test", Type.FitnessTest, null, "20 tekrar"),
    WHR(13, "Bel ve Kalça Oranı", Type.Calculated, null, "0.87"),
    BMI(14, "Vücut Kitle Endeksi", Type.Calculated, null, "23.4");

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