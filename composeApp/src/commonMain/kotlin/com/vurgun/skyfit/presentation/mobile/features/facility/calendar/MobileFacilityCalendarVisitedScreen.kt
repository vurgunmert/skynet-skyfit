package com.vurgun.skyfit.presentation.mobile.features.facility.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityCalendarVisitedScreen(navigator: Navigator) {

    val showCreateAction: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilityCalendarVisitedScreenToolbarComponent()
        },
        bottomBar = {
            if (showCreateAction) {
                MobileFacilityCalendarVisitedScreenCreateActionComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilityCalendarVisitedScreenCalendarGridComponent()
            MobileFacilityCalendarVisitedScreenPrivateClassesComponent()
        }
    }
}


@Composable
private fun MobileFacilityCalendarVisitedScreenToolbarComponent() {
    TodoBox("MobileFacilityCalendarVisitedScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilityCalendarVisitedScreenCalendarGridComponent() {
    TodoBox("MobileFacilityCalendarVisitedScreenCalendarGridComponent", Modifier.size(398.dp, 416.dp))
}

@Composable
private fun MobileFacilityCalendarVisitedScreenPrivateClassesComponent() {
    TodoBox("MobileFacilityCalendarVisitedScreenPrivateClassesComponent", Modifier.size(398.dp, 548.dp))
}

@Composable
private fun MobileFacilityCalendarVisitedScreenCreateActionComponent() {
    TodoBox("MobileFacilityCalendarVisitedScreenCreateActionComponent", Modifier.size(430.dp, 48.dp))
}
