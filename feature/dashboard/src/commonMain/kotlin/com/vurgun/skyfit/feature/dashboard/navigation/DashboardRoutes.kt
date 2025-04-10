package com.vurgun.skyfit.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.dashboard.MobileDashboardScreen
import kotlinx.serialization.Serializable

sealed interface DashboardRoute {

    @Serializable
    data object Main: DashboardRoute

    @Serializable
    data object Home: DashboardRoute

    @Serializable
    data object Explore: DashboardRoute

    @Serializable
    data object Social: DashboardRoute

    @Serializable
    data object Profile: DashboardRoute
}

fun NavGraphBuilder.dashboardRoutes(
    userRole: UserRole,
    goToChatBot: () -> Unit,
) {
    composable<DashboardRoute.Main> {
        MobileDashboardScreen(
            userRole = userRole,
            startRoute = DashboardRoute.Home,
            goToChatBot = goToChatBot
        )
    }
}
