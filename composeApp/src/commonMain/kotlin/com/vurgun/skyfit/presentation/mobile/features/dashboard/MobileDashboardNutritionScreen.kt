package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealsScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardNutritionScreen(rootNavigator: Navigator) {
    MobileUserMealsScreen(rootNavigator)
}