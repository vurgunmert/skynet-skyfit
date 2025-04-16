package com.vurgun.skyfit.feature.home.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.home.screen.MobileFacilityHomeScreen
import com.vurgun.skyfit.feature.home.screen.MobileTrainerHomeScreen
import com.vurgun.skyfit.feature.home.screen.MobileUserHomeScreen
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen

@Composable
fun HomeRoot(
    userRole: UserRole,
    goToNotifications: () -> Unit,
    goToMessages: () -> Unit,
    goToExplore: () -> Unit,
    goToSocial: () -> Unit,
    goToProfile: () -> Unit,
    goToActivityCalendar: () -> Unit,
    goToAppointments: () -> Unit,
    goToFacilityCourses: () -> Unit,
) {
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
            goToCourses = goToFacilityCourses,
            goToNotifications = { },
            goToMessages = { }
        )

        else -> UnauthorizedAccessScreen()
    }
}