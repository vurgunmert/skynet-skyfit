package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityClassDetailVisitedScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(SkyFitNavigationRoute.FacilityClassDetailVisited, SkyFitNavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
