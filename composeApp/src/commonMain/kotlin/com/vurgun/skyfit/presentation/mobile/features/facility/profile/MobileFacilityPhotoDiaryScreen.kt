package com.vurgun.skyfit.presentation.mobile.features.facility.profile

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityPhotoDiaryScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(SkyFitNavigationRoute.FacilityPhotoDiary, SkyFitNavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
