package com.vurgun.skyfit.feature.calendar.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class TrainerAppointmentListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileTrainerAppointmentsScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
private fun MobileTrainerAppointmentsScreen(goToBack: () -> Unit) {


}
