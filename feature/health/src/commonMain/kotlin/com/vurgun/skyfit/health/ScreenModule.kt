package com.vurgun.skyfit.health

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.health.nutrition.NutritionScreen
import com.vurgun.skyfit.health.posture.PostureAnalysisScreen

val screenHealthModule = screenModule {
    register<SharedScreen.PostureAnalysis> { PostureAnalysisScreen() }
    register<SharedScreen.Nutrition> { NutritionScreen() }
}