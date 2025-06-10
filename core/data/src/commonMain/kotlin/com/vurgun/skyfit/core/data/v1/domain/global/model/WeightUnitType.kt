package com.vurgun.skyfit.core.data.v1.domain.global.model

sealed class WeightUnitType(val id: Int, val description: String, val shortLabel: String) {
    data object KG : WeightUnitType(1, "Kilogram (kg)", "kg")
    data object LB : WeightUnitType(2, "Pound (lb)", "lb")

    companion object {
        fun from(id: Int): WeightUnitType {
            return when (id) {
                2 -> LB
                else -> KG
            }
        }

        fun getAllUnits(): List<WeightUnitType> = listOf(KG, LB)
    }
}
