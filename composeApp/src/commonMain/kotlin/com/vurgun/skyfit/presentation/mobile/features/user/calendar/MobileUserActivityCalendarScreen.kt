package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserActivityCalendarScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserActivityCalendarScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserActivityGridCalendarComponent()
            MobileUserActivityHourlyCalendarComponent()
        }
    }
}


@Composable
private fun MobileUserActivityCalendarScreenToolbarComponent() {
    TodoBox("MobileUserActivityCalendarScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserActivityGridCalendarComponent() {
    TodoBox("MobileUserActivityGridCalendarComponent", Modifier.size(430.dp, 384.dp))
}

@Composable
private fun MobileUserActivityHourlyCalendarComponent() {
    TodoBox("MobileUserActivityHourlyCalendarComponent", Modifier.size(430.dp, 400.dp))
}
