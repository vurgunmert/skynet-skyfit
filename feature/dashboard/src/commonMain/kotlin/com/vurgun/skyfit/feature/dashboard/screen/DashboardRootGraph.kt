package com.vurgun.skyfit.feature.dashboard.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar
import com.vurgun.skyfit.feature.home.navigation.HomeRoot
import com.vurgun.skyfit.feature.profile.navigation.ProfileOwnerRoot
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import org.koin.compose.viewmodel.koinViewModel

internal sealed class DashboardTab(val route: String) {
    data object Home : DashboardTab("dashboard/home")
    data object Profile : DashboardTab("dashboard/profile")
}

@Composable
fun DashboardRoot(
    goToChatBot: () -> Unit,
    goToSettings: () -> Unit,
    goToAppointments: () -> Unit,
    goToFacilityCourses: () -> Unit,
    goToVisitFacility: (facilityId: Int) -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()
    val internalNavController = rememberNavController()
    val currentBackStackEntry by internalNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: DashboardTab.Home.route

    SkyFitMobileScaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onClickHome = {
                    internalNavController.navigate(DashboardTab.Home.route) {
                        popUpTo(internalNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onClickProfile = {
                    internalNavController.navigate(DashboardTab.Profile.route) {
                        popUpTo(internalNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onClickChatBot = goToChatBot
            )
        }
    ) {
        NavHost(
            navController = internalNavController,
            startDestination = DashboardTab.Home.route
        ) {
            composable(DashboardTab.Home.route) {
                HomeRoot(
                    userRole = userRole,
                    goToNotifications = { },
                    goToMessages = { },
                    goToExplore = { },
                    goToSocial = { },
                    goToProfile = { },
                    goToActivityCalendar = { },
                    goToAppointments = goToAppointments,
                    goToFacilityCourses = goToFacilityCourses
                )
            }

            composable(DashboardTab.Profile.route) {
                ProfileOwnerRoot(
                    userRole = userRole,
                    goToFacilityCourses = goToFacilityCourses,
                    goToAppointments = goToAppointments,
                    goToSettings = goToSettings,
                    goToVisitFacility = goToVisitFacility
                )
            }
        }
    }
}
