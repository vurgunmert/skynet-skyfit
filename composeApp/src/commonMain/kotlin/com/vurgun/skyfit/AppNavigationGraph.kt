package com.vurgun.skyfit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.feature.auth.AuthRoute
import com.vurgun.skyfit.feature.auth.authRoutes
import com.vurgun.skyfit.feature.bodyanalysis.navigation.postureAnalysisRoutes
import com.vurgun.skyfit.feature.calendar.navigation.appointmentRoutes
import com.vurgun.skyfit.feature.courses.navigation.courseLessonsRoutes
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.onboarding.navigation.Onboarding
import com.vurgun.skyfit.feature.onboarding.navigation.onboardingRoutes
import com.vurgun.skyfit.feature.settings.navigation.settingsRoutes
import com.vurgun.skyfit.navigation.dashboardRoutes
import com.vurgun.skyfit.navigation.profileRoutes

@Composable
fun AppNavigationGraph() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = AuthRoute.Splash,
        modifier = Modifier.fillMaxSize()
    ) {

        postureAnalysisRoutes(
            onExit = {

            }
        )

        authRoutes(
            navController = navigationController,
            goToDashboard = {
                navigationController.navigateAndClear(DashboardRoute)
            },
            goToOnboarding = {
                navigationController.navigateAndClear(Onboarding(isAddition = false))
            }
        )

        onboardingRoutes(
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            },
            goToDashboard = {
                navigationController.navigateAndClear(DashboardRoute)
            }
        )

        dashboardRoutes(navigationController)

        settingsRoutes(
            onExitSettings = {
                if (!navigationController.popBackStackSafe()) {
                    navigationController.navigate(DashboardRoute)
                }
            },
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            },
            goToAddAccount = {
                navigationController.navigate(Onboarding(isAddition = true))
            }
        )

        courseLessonsRoutes(
            onExit = navigationController::popBackStack,
            onHome = { navigationController.navigateAndClear(DashboardRoute) }
        )

        appointmentRoutes(navigationController)

        profileRoutes(navigationController)
    }
}

fun NavController.navigateAndClear(route: Any, startRoute: Any) {
    navigate(route) {
        popUpTo(startRoute) { inclusive = true }
        launchSingleTop = true
    }
}


fun NavController.navigateAndClear(route: Any) {
    val startRoute = this.graph.startDestinationRoute
    navigate(route) {
        if (startRoute != null) {
            popUpTo(startRoute) { inclusive = true }
        }
        launchSingleTop = true
    }
}

fun NavController.popBackStackSafe(): Boolean {
    return if (this.currentBackStack.value.size > 1) {
        popBackStack()
        true
    } else {
        false
    }
}
