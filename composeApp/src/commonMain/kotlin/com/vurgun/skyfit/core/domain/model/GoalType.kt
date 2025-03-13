package com.vurgun.skyfit.core.domain.model

sealed class GoalType(val id: Int, val label: String) {
    data object LOSE_WEIGHT : GoalType(1, "Kilo Vermek")
    data object STAY_FIT : GoalType(2, "Fit Olmak")
    data object GAIN_MUSCLE : GoalType(3, "Kas Yapmak")

    companion object {
        fun from(id: Int): GoalType? {
            return when (id) {
                1 -> LOSE_WEIGHT
                2 -> STAY_FIT
                3 -> GAIN_MUSCLE
                else -> null
            }
        }

        fun getAllGoals(): List<GoalType> = listOf(LOSE_WEIGHT, STAY_FIT, GAIN_MUSCLE)
    }
}
