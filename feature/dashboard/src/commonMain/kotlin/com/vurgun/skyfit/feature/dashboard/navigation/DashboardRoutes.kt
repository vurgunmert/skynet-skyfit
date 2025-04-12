package com.vurgun.skyfit.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.dashboard.screen.DashboardRootGraph
import kotlinx.serialization.Serializable

sealed interface DashboardRoute {

    @Serializable
    data object Home : DashboardRoute

    @Serializable
    data object Profile : DashboardRoute
}

fun NavGraphBuilder.dashboardRoutes(
    goToChatBot: () -> Unit,
    goToSettings: () -> Unit,
) {
    composable<DashboardRoute.Home> {
        DashboardRootGraph(
            startRoute = DashboardRoute.Home,
            goToChatBot = goToChatBot,
            goToSettings = goToSettings
        )
    }
    composable<DashboardRoute.Profile> {
        DashboardRootGraph(
            startRoute = DashboardRoute.Profile,
            goToChatBot = goToChatBot,
            goToSettings = goToSettings
        )
    }
}
