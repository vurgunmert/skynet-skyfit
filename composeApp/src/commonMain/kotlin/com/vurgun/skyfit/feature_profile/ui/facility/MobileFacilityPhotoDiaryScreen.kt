package com.vurgun.skyfit.feature_profile.ui.facility

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityPhotoDiaryScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(NavigationRoute.FacilityPhotoDiary, NavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
