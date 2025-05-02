//package com.vurgun.skyfit.feature.calendar.navigation
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.toRoute
//import com.vurgun.skyfit.feature.calendar.screen.MobileTrainerAppointmentDetailScreen
//import com.vurgun.skyfit.feature.calendar.screen.MobileUserAppointmentDetailScreen
//import com.vurgun.skyfit.feature.calendar.screen.MobileUserAppointmentListingScreen
//import kotlinx.serialization.Serializable
//
//sealed interface AppointmentRoute {
//
//    @Serializable
//    data object AppointmentListing : AppointmentRoute
//
//    @Serializable
//    data class UserAppointmentDetail(val lpId: Int) : AppointmentRoute
//
//    @Serializable
//    data class TrainerAppointmentDetail(val lessonId: Int) : AppointmentRoute
//}
//
//
//fun NavGraphBuilder.appointmentRoutes(
//    navController: NavController
//) {
//
//    composable<AppointmentRoute.AppointmentListing>() {
//        MobileUserAppointmentListingScreen(
//            goToBack = navController::navigateUp,
//            goToDetails = {
//                navController.navigate(AppointmentRoute.UserAppointmentDetail(it))
//            }
//        )
//    }
//
//    composable<AppointmentRoute.UserAppointmentDetail>() {
//        val lpId = it.toRoute<AppointmentRoute.UserAppointmentDetail>().lpId
//
//        MobileUserAppointmentDetailScreen(
//            lpId = lpId,
//            goToBack = navController::navigateUp,
//        )
//    }
//
//    composable<AppointmentRoute.TrainerAppointmentDetail>() {
//        val lessonId = it.toRoute<AppointmentRoute.TrainerAppointmentDetail>().lessonId
//
//        MobileTrainerAppointmentDetailScreen(
//            lessonId = lessonId,
//            goToBack = navController::navigateUp,
//        )
//    }
//}