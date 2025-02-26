package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealsScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardNutritionScreen(rootNavigator: Navigator) {
    MobileUserMealsScreen(rootNavigator)
}