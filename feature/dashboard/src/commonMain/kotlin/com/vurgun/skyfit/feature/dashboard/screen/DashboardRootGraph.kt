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
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.home.navigation.HomeRoot
import com.vurgun.skyfit.feature.profile.navigation.ProfileRoot
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun DashboardRootGraph(
    startRoute: DashboardRoute = DashboardRoute.Home,
    goToChatBot: () -> Unit,
    goToSettings: () -> Unit,
    goToAppointments: () -> Unit,
    goToFacilityCourses: () -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()

    val internalNavController = rememberNavController()
    val currentBackStackEntry by internalNavController.currentBackStackEntryAsState()

    val currentRoute = currentBackStackEntry?.destination?.route ?: startRoute

    SkyFitMobileScaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onClickHome = {
                    internalNavController.navigate(DashboardRoute.Home) {
                        popUpTo(internalNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onClickProfile = {
                    internalNavController.navigate(DashboardRoute.Profile) {
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
            startDestination = startRoute
        ) {
            composable<DashboardRoute.Home> {
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

            composable<DashboardRoute.Profile> {
                ProfileRoot(
                    userRole = userRole,
                    goToFacilityCourses = goToFacilityCourses,
                    goToAppointments = goToAppointments,
                    goToSettings = goToSettings,
                )
            }
        }
    }
}