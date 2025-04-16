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
import com.vurgun.skyfit.feature.courses.navigation.FacilityCoursesMainRoute
import com.vurgun.skyfit.feature.courses.navigation.courseLessonsRoutes
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.dashboard.navigation.dashboardRoutes
import com.vurgun.skyfit.feature.onboarding.navigation.Onboarding
import com.vurgun.skyfit.feature.onboarding.navigation.onboardingRoutes
import com.vurgun.skyfit.feature.settings.navigation.SettingsRoute
import com.vurgun.skyfit.feature.settings.navigation.settingsRoutes

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
                navigationController.navigateAndClear(DashboardRoute.Home)
            },
            goToOnboarding = {
                navigationController.navigateAndClear(Onboarding)
            }
        )

        onboardingRoutes(
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            },
            goToDashboard = {
                navigationController.navigateAndClear(DashboardRoute.Home)
            }
        )

        dashboardRoutes(
            goToChatBot = {

            },
            goToSettings = {
                navigationController.navigate(SettingsRoute.Main)
            },
            goToFacilityCourses = {
                navigationController.navigate(FacilityCoursesMainRoute)
            }
        )

        settingsRoutes(
            navController = navigationController,
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            }
        )

        courseLessonsRoutes(
            onExit = navigationController::popBackStack,
            onHome = { navigationController.navigateAndClear(DashboardRoute.Home) }
        )
    }
}

fun NavController.navigateAndClear(route: Any) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}
