package com.vurgun.skyfit.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.home.screen.MobileFacilityHomeScreen
import com.vurgun.skyfit.feature.home.screen.MobileTrainerHomeScreen
import com.vurgun.skyfit.feature.home.screen.MobileUserHomeScreen
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen
import kotlinx.serialization.Serializable

sealed interface HomeRoute {

    @Serializable
    data object Main
}

fun NavGraphBuilder.homeRoutes(
    userRole: UserRole,
    goToNotifications: () -> Unit,
    goToMessages: () -> Unit,
    goToExplore: () -> Unit,
    goToSocial: () -> Unit,
    goToProfile: () -> Unit,
    goToActivityCalendar: () -> Unit,
    goToAppointments: () -> Unit,
) {
    composable<HomeRoute.Main> {
        when (userRole) {
            is UserRole.User -> MobileUserHomeScreen(
                goToNotifications = goToNotifications,
                goToMessages = goToMessages,
                goToExplore = goToExplore,
                goToSocial = goToSocial,
                goToProfile = goToProfile,
                goToActivityCalendar = goToActivityCalendar,
                goToAppointments = goToAppointments
            )

            is UserRole.Trainer -> MobileTrainerHomeScreen(
                goToNotifications = { },
                goToMessages = { },
                goToProfile = { }
            )

            is UserRole.Facility -> MobileFacilityHomeScreen(
                goToCourses = { },
                goToNotifications = { },
                goToMessages = { }
            )

            else -> UnauthorizedAccessScreen()
        }
    }
}