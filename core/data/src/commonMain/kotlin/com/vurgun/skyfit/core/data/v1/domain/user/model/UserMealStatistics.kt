package com.vurgun.skyfit.core.data.v1.domain.user.model

data class UserMealStatistics(
    val items: List<MealItem>
) {
    data class MealItem(
        val name: String,
        val mealName: String,
        val imageUrl: String,
        val completed: Boolean
    )
}