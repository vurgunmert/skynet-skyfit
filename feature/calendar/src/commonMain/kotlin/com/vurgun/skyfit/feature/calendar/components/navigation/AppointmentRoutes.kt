package com.vurgun.skyfit.feature.calendar.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserAppointmentsScreen
import com.vurgun.skyfit.feature.calendar.components.screen.UserAppointmentDetailViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

sealed interface AppointmentRoute {

    @Serializable
    data object Listing : AppointmentRoute

    @Serializable
    data object Detail : AppointmentRoute
}


fun NavGraphBuilder.appointmentRoutes(
    navController: NavController
) {

    composable<AppointmentRoute.Listing>() {
        MobileUserAppointmentsScreen(
            goToBack = navController::navigateUp,
            goToDetails = {
                //TODO: 'it' is Appointment, we need to tell detail which appointment
                navController.navigate(AppointmentRoute.Detail)
            }
        )
    }

    composable<AppointmentRoute.Detail>() {
        val viewModel: UserAppointmentDetailViewModel = koinViewModel()
        //TODO: maybe use viewmodel more global and loadData on goToDetail?
        MobileUserAppointmentDetailScreen(
            goToBack = navController::navigateUp,
            viewModel = viewModel
        )
    }
}