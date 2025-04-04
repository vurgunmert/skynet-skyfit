package com.vurgun.skyfit.feature_lessons.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityCalendarScreen(navigator: Navigator) {

    Button({
        navigator.jumpAndTakeover(MobileNavRoute.FacilityCalendar, MobileNavRoute.Dashboard)
    }, content = { Text("Dashboard") })
}
