package com.vurgun.skyfit.presentation.mobile.features.trainer.appointments

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerAppointmentDetailScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(SkyFitNavigationRoute.TrainerAppointmentDetail, SkyFitNavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
