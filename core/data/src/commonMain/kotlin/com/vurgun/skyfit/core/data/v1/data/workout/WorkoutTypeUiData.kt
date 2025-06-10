package com.vurgun.skyfit.core.data.v1.data.workout

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutTypeUiData(
    val id: Int,
    val name: String,
    val isPopular: Boolean = false,
    val isNew: Boolean = false
) {
    val emojiId: String = when(id) {
        1 -> "\uD83D\uDEB6\u200D♂\uFE0F"
        2 -> "\uD83D\uDEB4\u200D♀\uFE0F"
        3 -> "\uD83E\uDDD8\u200D♀\uFE0F"
        4 -> "\uD83E\uDD38\u200D♀\uFE0F"
        5 -> "\uD83C\uDFC3\u200D♂\uFE0F"
        6 -> "\uD83C\uDFCA\u200D♀\uFE0F"
        7 -> "\uD83E\uDDD7\u200D♂\uFE0F"
        else -> ""
    }

    val categoryId: Int = when (id) {
        1 -> 1 // Yürüyüş -> Cardio
        2 -> 1 // Bisiklet -> Cardio
        3 -> 7 // Yoga -> Mind & Body
        4 -> 4 // Pilates -> Core
        5 -> 1 // Koşma -> Cardio
        6 -> 2 // Yüzme -> Strength
        7 -> 2 // Tırmanma -> Strength
        else -> 1 // Default fallback (you can change this as needed)
    }

}