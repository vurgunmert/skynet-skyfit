package com.vurgun.skyfit.core.data.schedule.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutType(
    val id: Int,
    val name: Map<String, String>,
    val categoryId: Int,
    val iconId: Int? = null,
    val emojiId: String,
    val isPopular: Boolean = false,
    val isNew: Boolean = false
)

val workoutTypes = listOf(
    // Cardio
    WorkoutType(1, mapOf("en" to "Walking", "tr" to "Yürüyüş"), 1, emojiId = "🚶‍♂️", isPopular = true),
    WorkoutType(2, mapOf("en" to "Jogging", "tr" to "Koşu"), 1, emojiId = "🏃‍♂️"),
    WorkoutType(3, mapOf("en" to "Cycling", "tr" to "Bisiklet"), 1, emojiId = "🚴‍♂️"),
    WorkoutType(4, mapOf("en" to "Swimming", "tr" to "Yüzme"), 1, emojiId = "🏊‍♂️"),
    WorkoutType(5, mapOf("en" to "Dance Fitness", "tr" to "Dans Antrenmanı"), 1, emojiId = "🕺"),

    // Strength
    WorkoutType(6, mapOf("en" to "Weight Lifting", "tr" to "Ağırlık Kaldırma"), 2, emojiId = "🏋️‍♂️"),
    WorkoutType(7, mapOf("en" to "Bodyweight", "tr" to "Vücut Ağırlığı"), 2, emojiId = "🤸‍♂️"),
    WorkoutType(8, mapOf("en" to "Glutes & Legs", "tr" to "Kalça ve Bacak"), 2, emojiId = "🦵"),
    WorkoutType(9, mapOf("en" to "Push & Pull", "tr" to "İtiş ve Çekiş"), 2, emojiId = "🧲"),
    WorkoutType(10, mapOf("en" to "Core Strength", "tr" to "Merkez Gücü"), 2, emojiId = "💪"),

    // Mobility
    WorkoutType(11, mapOf("en" to "Joint Mobility", "tr" to "Eklem Mobilitesi"), 3, emojiId = "🔄"),
    WorkoutType(12, mapOf("en" to "Back Mobility", "tr" to "Sırt Hareketliliği"), 3, emojiId = "🔁"),
    WorkoutType(13, mapOf("en" to "Hip Mobility", "tr" to "Kalça Hareketliliği"), 3, emojiId = "🧘‍♂️"),
    WorkoutType(14, mapOf("en" to "Shoulder Loosen", "tr" to "Omuz Gevşetme"), 3, emojiId = "🌀"),
    WorkoutType(15, mapOf("en" to "Full-Body Flow", "tr" to "Tüm Vücut Akışı"), 3, emojiId = "🌊"),

    // Core
    WorkoutType(16, mapOf("en" to "Plank", "tr" to "Plank"), 4, emojiId = "📏"),
    WorkoutType(17, mapOf("en" to "Crunches", "tr" to "Mekik"), 4, emojiId = "💢"),
    WorkoutType(18, mapOf("en" to "Side Plank", "tr" to "Yan Plank"), 4, emojiId = "🧍‍♂️"),
    WorkoutType(19, mapOf("en" to "Leg Raises", "tr" to "Bacak Kaldırma"), 4, emojiId = "🦿"),
    WorkoutType(20, mapOf("en" to "Core Circuit", "tr" to "Merkez Devresi"), 4, emojiId = "⚙️"),

    // Flexibility
    WorkoutType(21, mapOf("en" to "Stretching", "tr" to "Esneme"), 5, emojiId = "🧘"),
    WorkoutType(22, mapOf("en" to "Hamstring Stretch", "tr" to "Arka Bacak Esnetme"), 5, emojiId = "🦵"),
    WorkoutType(23, mapOf("en" to "Neck & Shoulder", "tr" to "Boyun & Omuz"), 5, emojiId = "🧖‍♂️"),
    WorkoutType(24, mapOf("en" to "Morning Stretch", "tr" to "Sabah Esnemesi"), 5, emojiId = "🌅"),
    WorkoutType(25, mapOf("en" to "Bedtime Stretch", "tr" to "Uyku Öncesi Esneme"), 5, emojiId = "🌙"),

    // Balance
    WorkoutType(26, mapOf("en" to "Single Leg Stand", "tr" to "Tek Ayakta Duruş"), 6, emojiId = "🦶"),
    WorkoutType(27, mapOf("en" to "Tree Pose", "tr" to "Ağaç Pozu"), 6, emojiId = "🌳"),
    WorkoutType(28, mapOf("en" to "Wall Sit", "tr" to "Duvar Oturuşu"), 6, emojiId = "🪑"),
    WorkoutType(29, mapOf("en" to "Slow Lunges", "tr" to "Yavaş Lunge"), 6, emojiId = "🦵"),
    WorkoutType(30, mapOf("en" to "Stability Challenge", "tr" to "Denge Egzersizi"), 6, emojiId = "⚖️"),

    // Mind & Body
    WorkoutType(31, mapOf("en" to "Yoga", "tr" to "Yoga"), 7, emojiId = "🧘‍♂️", isPopular = true),
    WorkoutType(32, mapOf("en" to "Meditation", "tr" to "Meditasyon"), 7, emojiId = "🕊️"),
    WorkoutType(33, mapOf("en" to "Tai Chi", "tr" to "Tai Chi"), 7, emojiId = "🌊"),
    WorkoutType(34, mapOf("en" to "Breathwork", "tr" to "Nefes Egzersizi"), 7, emojiId = "🌬️"),
    WorkoutType(35, mapOf("en" to "Mindful Movement", "tr" to "Farkındalık Hareketi"), 7, emojiId = "💫"),

    // Recovery
    WorkoutType(36, mapOf("en" to "Active Walk", "tr" to "Aktif Yürüyüş"), 8, emojiId = "🚶‍♂️"),
    WorkoutType(37, mapOf("en" to "Recovery Stretch", "tr" to "İyileşme Esnetmesi"), 8, emojiId = "🧘‍♀️"),
    WorkoutType(38, mapOf("en" to "Legs on Wall", "tr" to "Bacaklar Duvara"), 8, emojiId = "🧱"),
    WorkoutType(39, mapOf("en" to "Cold Shower", "tr" to "Soğuk Duş"), 8, emojiId = "🚿"),
    WorkoutType(40, mapOf("en" to "Relax & Reset", "tr" to "Rahatla ve Yenilen"), 8, emojiId = "🔄"),
)
