package com.vurgun.skyfit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vurgun.skyfit.feature.calendar.components.navigation.AppointmentRoute
import com.vurgun.skyfit.feature.courses.navigation.FacilityCoursesMainRoute
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.dashboard.screen.DashboardRoot
import com.vurgun.skyfit.feature.profile.facility.MobileFacilityProfileVisitorScreen
import com.vurgun.skyfit.feature.profile.navigation.VisitFacilityProfileCalendarRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitFacilityProfileRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitTrainerProfileRoute
import com.vurgun.skyfit.feature.settings.navigation.SettingsRoute

fun NavGraphBuilder.profileRoutes(navController: NavController) {

    composable<VisitFacilityProfileRoute> {
        val facilityId = it.toRoute<VisitFacilityProfileRoute>().facilityId

        MobileFacilityProfileVisitorScreen(
            facilityId = facilityId,
            goToBack = { navController.popBackStack() },
            goToVisitCalendar = { navController.navigate(VisitFacilityProfileCalendarRoute(facilityId)) },
            goToVisitTrainerProfile = { trainerId -> navController.navigate(VisitTrainerProfileRoute(trainerId)) },
            goToChat = { /*navController.navigate(ChatRoute(facilityId))*/ }
        )
    }
}

fun NavGraphBuilder.dashboardRoutes(
    navController: NavController
) {
    composable<DashboardRoute> {
        DashboardRoot(
            goToChatBot = { navController.popBackStack() },
            goToSettings = {
                navController.navigate(SettingsRoute.Main) {
                    launchSingleTop = true
                }
            },
            goToAppointments = {
                navController.navigate(AppointmentRoute.Listing)
            },
            goToFacilityCourses = {
                navController.navigate(FacilityCoursesMainRoute)
            },
            goToVisitFacility = {
                navController.navigate(VisitFacilityProfileRoute(facilityId = it))
            }
        )
    }
}