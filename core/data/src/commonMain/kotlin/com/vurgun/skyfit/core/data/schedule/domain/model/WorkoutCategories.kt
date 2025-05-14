package com.vurgun.skyfit.core.data.schedule.domain.model

data class WorkoutCategory(
    val id: Int,
    val key: String,
    val displayName: Map<String, String>
)

object WorkoutCategories {

    val ALL: List<WorkoutCategory> = listOf(
        WorkoutCategory(
            id = 1,
            key = "cardio",
            displayName = mapOf("en" to "Cardio", "tr" to "Kardiyo")
        ),
        WorkoutCategory(
            id = 2,
            key = "strength",
            displayName = mapOf("en" to "Strength", "tr" to "Kuvvet")
        ),
        WorkoutCategory(
            id = 3,
            key = "mobility",
            displayName = mapOf("en" to "Mobility", "tr" to "Hareket Kabiliyeti")
        ),
        WorkoutCategory(
            id = 4,
            key = "core",
            displayName = mapOf("en" to "Core", "tr" to "Merkez Bölge")
        ),
        WorkoutCategory(
            id = 5,
            key = "flexibility",
            displayName = mapOf("en" to "Flexibility", "tr" to "Esneklik")
        ),
        WorkoutCategory(
            id = 6,
            key = "balance",
            displayName = mapOf("en" to "Balance", "tr" to "Denge")
        ),
        WorkoutCategory(
            id = 7,
            key = "mindbody",
            displayName = mapOf("en" to "Mind & Body", "tr" to "Zihin & Beden")
        ),
        WorkoutCategory(
            id = 8,
            key = "recovery",
            displayName = mapOf("en" to "Recovery", "tr" to "İyileşme")
        )
    )

    fun find(id: Int): WorkoutCategory {
        return ALL.first { it.id == id }
    }
}
