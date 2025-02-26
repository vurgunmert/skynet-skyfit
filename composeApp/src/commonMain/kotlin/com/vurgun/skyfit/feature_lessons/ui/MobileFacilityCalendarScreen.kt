package com.vurgun.skyfit.feature_lessons.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityCalendarScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(NavigationRoute.FacilityCalendar, NavigationRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
