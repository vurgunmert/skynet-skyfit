package com.vurgun.skyfit.core.data.v1.data.global.model

enum class ChallengeCategoryDTO(val id: Int, val nameEn: String, val nameTr: String) {
    FITNESS(1, "Fitness", "Fitness"),
    NUTRITION(2, "Nutrition", "Beslenme"),
    LIFESTYLE(3, "Lifestyle", "Yaşam Tarzı")
}

enum class ChallengeTypeDTO(val id: Int, val nameEn: String, val nameTr: String) {
    STEPS(1, "Steps", "Adımlar"),
    CALORIES(2, "Calories", "Kaloriler"),
    DISTANCE(3, "Distance", "Mesafe"),
    CUSTOM(4, "Custom", "Özel")
}
