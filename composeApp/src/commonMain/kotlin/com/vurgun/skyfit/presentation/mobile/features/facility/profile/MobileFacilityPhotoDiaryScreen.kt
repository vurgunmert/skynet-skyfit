package com.vurgun.skyfit.presentation.mobile.features.facility.profile

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityPhotoDiaryScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(NavigationRoute.FacilityPhotoDiary, NavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
