package com.vurgun.skyfit.data.core.domain.model

sealed class WeightUnitType(val id: Int, val description: String, val shortLabel: String) {
    data object KG : WeightUnitType(1, "Kilogram (kg)", "kg")
    data object LB : WeightUnitType(2, "Pound (lb)", "lb")

    companion object {
        fun from(id: Int): WeightUnitType? {
            return when (id) {
                1 -> KG
                2 -> LB
                else -> null
            }
        }

        fun getAllUnits(): List<WeightUnitType> = listOf(KG, LB)
    }
}
