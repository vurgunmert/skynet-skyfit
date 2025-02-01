package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileDashboardScreen(
    rootNavigator: Navigator,
    initialRoute: SkyFitNavigationRoute = SkyFitNavigationRoute.DashboardExplore
) {
    val dashboardNavigator = rememberNavigator()

    NavHost(
        navigator = dashboardNavigator,
        initialRoute = initialRoute.route
    ) {
        scene(SkyFitNavigationRoute.DashboardHome.route) { MobileDashboardHomeScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExplore.route) { MobileDashboardExploreScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardSocial.route) { MobileDashboardSocialScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardNutrition.route) { MobileDashboardNutritionScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardProfile.route) { MobileDashboardProfileScreen(rootNavigator) }
    }
}

