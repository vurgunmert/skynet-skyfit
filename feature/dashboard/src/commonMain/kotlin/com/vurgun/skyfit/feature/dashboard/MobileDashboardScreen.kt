package com.vurgun.skyfit.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.explore.navigation.ExploreRoute
import com.vurgun.skyfit.feature.explore.navigation.exploreRoutes
import com.vurgun.skyfit.feature.home.navigation.HomeRoute
import com.vurgun.skyfit.feature.home.navigation.homeRoutes
import com.vurgun.skyfit.feature.profile.navigation.ProfileRoute
import com.vurgun.skyfit.feature.profile.navigation.profileRoutes
import com.vurgun.skyfit.feature.social.navigation.SocialMediaRoute
import com.vurgun.skyfit.feature.social.navigation.socialMediaRoutes
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold

@Composable
fun MobileDashboardScreen(
    userRole: UserRole,
    startRoute: DashboardRoute,
    goToChatBot: () -> Unit,
) {
    val dashboardNavController = rememberNavController()
    val currentBackStackEntry by dashboardNavController.currentBackStackEntryAsState()

    val initialRoute: Any = when (startRoute) {
        DashboardRoute.Main -> HomeRoute.Main
        DashboardRoute.Home -> HomeRoute.Main
        DashboardRoute.Explore -> ExploreRoute.Main
        DashboardRoute.Social -> SocialMediaRoute.Main
        DashboardRoute.Profile -> ProfileRoute.Main
    }

    val currentRoute = currentBackStackEntry?.destination?.route ?: initialRoute

    SkyFitMobileScaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onClickHome = { dashboardNavController.navigateSingleTopTo(HomeRoute.Main) },
                onClickExplore = { dashboardNavController.navigateSingleTopTo(ExploreRoute.Main) },
                onClickSocial = { dashboardNavController.navigateSingleTopTo(SocialMediaRoute.Main) },
                onClickAddPost = { },
                onClickProfile = { dashboardNavController.navigateSingleTopTo(ProfileRoute.Main) },
                onClickChatBot = goToChatBot
            )
        }
    ) {
        NavHost(
            navController = dashboardNavController,
            startDestination = initialRoute
        ) {
            homeRoutes(
                userRole = UserRole.User,
                goToNotifications = { },
                goToMessages = { },
                goToExplore = { },
                goToSocial = { },
                goToProfile = { },
                goToActivityCalendar = { },
                goToAppointments = { }
            )

            exploreRoutes()

            socialMediaRoutes()

            profileRoutes(
                userRole = UserRole.User //TODO: ?
            )
        }
    }
}


private fun NavController.navigateSingleTopTo(route: Any) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}