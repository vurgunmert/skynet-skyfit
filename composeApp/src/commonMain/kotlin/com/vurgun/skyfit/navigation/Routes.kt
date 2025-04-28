package com.vurgun.skyfit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vurgun.skyfit.feature.calendar.navigation.AppointmentRoute
import com.vurgun.skyfit.feature.courses.navigation.FacilityCoursesMainRoute
import com.vurgun.skyfit.feature.dashboard.navigation.DashboardRoute
import com.vurgun.skyfit.feature.dashboard.screen.DashboardRoot
import com.vurgun.skyfit.feature.profile.facility.schedule.MobileFacilityProfileScheduleScreen
import com.vurgun.skyfit.feature.profile.facility.visitor.MobileFacilityProfileVisitorScreen
import com.vurgun.skyfit.feature.profile.navigation.VisitFacilityProfileRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitFacilityProfileScheduleRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitTrainerProfileRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitTrainerProfileScheduleRoute
import com.vurgun.skyfit.feature.profile.navigation.VisitUserProfileRoute
import com.vurgun.skyfit.feature.profile.trainer.schedule.MobileTrainerProfileScheduleScreen
import com.vurgun.skyfit.feature.profile.trainer.visitor.MobileTrainerProfileVisitorScreen
import com.vurgun.skyfit.feature.profile.user.visitor.MobileUserProfileVisitorScreen
import com.vurgun.skyfit.feature.settings.navigation.SettingsRoute

fun NavGraphBuilder.profileRoutes(navController: NavController) {

    composable<VisitFacilityProfileRoute> {
        val facilityId = it.toRoute<VisitFacilityProfileRoute>().facilityId

        MobileFacilityProfileVisitorScreen(
            facilityId = facilityId,
            goToBack = { navController.popBackStack() },
            goToVisitCalendar = { navController.navigate(VisitFacilityProfileScheduleRoute(facilityId)) },
            goToVisitTrainerProfile = { trainerId -> navController.navigate(VisitTrainerProfileRoute(trainerId)) },
            goToChat = { /*navController.navigate(ChatRoute(facilityId))*/ }
        )
    }

    composable<VisitTrainerProfileRoute> {
        val trainerId = it.toRoute<VisitTrainerProfileRoute>().trainerId

        MobileTrainerProfileVisitorScreen(
            trainerId = trainerId,
            goToBack = navController::navigateUp,
            goToChat = { /*TODO()*/ },
            goToSchedule = { navController.navigate(VisitTrainerProfileScheduleRoute(trainerId)) }
        )
    }

    composable<VisitUserProfileRoute> {
        val normalUserId = it.toRoute<VisitUserProfileRoute>().normalUserId

        MobileUserProfileVisitorScreen(
            normalUserId = normalUserId,
            goToBack = navController::navigateUp
        )
    }

    composable<VisitFacilityProfileScheduleRoute> {
        val facilityId = it.toRoute<VisitFacilityProfileScheduleRoute>().facilityId

        MobileFacilityProfileScheduleScreen(
            facilityId = facilityId,
            goToBack = navController::navigateUp,
            goToAppointmentDetail = { lpId ->
                navController.navigate(AppointmentRoute.UserAppointmentDetail(lpId))
            },
        )
    }

    composable<VisitTrainerProfileScheduleRoute> {
        val trainerId = it.toRoute<VisitTrainerProfileScheduleRoute>().trainerId

        MobileTrainerProfileScheduleScreen(
            trainerId = trainerId,
            goToBack = navController::navigateUp,
            goToAppointmentDetail = { lpId ->
                navController.navigate(AppointmentRoute.UserAppointmentDetail(lpId))
            }
        )
    }
}

fun NavGraphBuilder.dashboardRoutes(
    navController: NavController,
    goToPostureAnalysis: () -> Unit
) {
    composable<DashboardRoute> {
        DashboardRoot(
            goToChatBot = goToPostureAnalysis,
            goToSettings = {
                navController.navigate(SettingsRoute.Main) {
                    launchSingleTop = true
                }
            },
            goToAppointments = {
                navController.navigate(AppointmentRoute.AppointmentListing)
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