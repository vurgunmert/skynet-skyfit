package com.vurgun.skyfit.core.domain.models

sealed class GoalType(val id: Int, val label: String) {
    data object Slimming : GoalType(1, "Incelme")
    data object Tightening : GoalType(2, "Sıkılaşma")
    data object Strengthening : GoalType(3, "Güçlenme")
    data object FixPosture : GoalType(4, "Postür Düzenlemek")
    data object GainPerformance : GoalType(5, "Performans Geliştirme")

    companion object {
        fun from(id: Int): GoalType? {
            return when (id) {
                1 -> Slimming
                2 -> Tightening
                3 -> Strengthening
                4 -> FixPosture
                5 -> GainPerformance
                else -> null
            }
        }

        fun getAllGoals(): List<GoalType> = listOf(Slimming, Strengthening, FixPosture, GainPerformance)
    }
}
