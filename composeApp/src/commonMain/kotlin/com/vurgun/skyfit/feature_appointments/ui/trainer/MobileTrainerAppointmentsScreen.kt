package com.vurgun.skyfit.feature_appointments.ui.trainer

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerAppointmentsScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(NavigationRoute.TrainerAppointments, NavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
