package com.vurgun.skyfit

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.auth.AuthRoute
import com.vurgun.skyfit.feature.auth.authRoutes
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.dashboard.navigation.dashboardRoutes
import com.vurgun.skyfit.feature.onboarding.navigation.OnboardingRoute
import com.vurgun.skyfit.feature.onboarding.navigation.onboardingRoutes
import com.vurgun.skyfit.feature.settings.navigation.SettingsRoute
import com.vurgun.skyfit.feature.settings.navigation.settingsRoutes

@Composable
fun AppNavigationGraph() {
    val navigationController = rememberNavController()
    val selectedRole = UserRole.Facility

    NavHost(
        navController = navigationController,
        startDestination = SettingsRoute.ManageTrainers,
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars) //TOOD: CATCHES ALL SIDES
    ) {

        authRoutes(
            navController = navigationController,
            goToDashboard = {
                navigationController.navigateAndClear(DashboardRoute.Main)
            },
            goToOnboarding = {
                navigationController.navigateAndClear(OnboardingRoute.Main)
            }
        )

        onboardingRoutes(
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            },
            goToDashboard = {
                navigationController.navigateAndClear(DashboardRoute.Main)
            }
        )

        dashboardRoutes(
            userRole = selectedRole,
            goToChatBot = {

            }
        )

        settingsRoutes(
            navController = navigationController,
            role = selectedRole,
            goToLogin = {
                navigationController.navigateAndClear(AuthRoute.Login)
            }
        )
    }
}

fun NavController.navigateAndClear(route: Any) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}
