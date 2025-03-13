package com.vurgun.skyfit.core.domain.model

sealed class HeightUnitType(val id: Int, val description: String, val label: String) {
    data object CM : HeightUnitType(1, "Santimetre (cm)", "cm")
    data object FT : HeightUnitType(2, "Feet (ft)", "ft")

    companion object {
        fun from(id: Int): HeightUnitType? {
            return when (id) {
                1 -> CM
                2 -> FT
                else -> null
            }
        }

        fun getAllUnits(): List<HeightUnitType> = listOf(CM, FT)
    }
}
