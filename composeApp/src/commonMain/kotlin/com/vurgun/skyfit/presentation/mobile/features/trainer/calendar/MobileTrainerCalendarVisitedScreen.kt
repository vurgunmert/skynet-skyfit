package com.vurgun.skyfit.presentation.mobile.features.trainer.calendar

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
fun MobileTrainerCalendarVisitedScreen(navigator: Navigator) {

    val showCreateAction: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileTrainerCalendarVisitedScreenToolbarComponent()
        },
        bottomBar = {
            if (showCreateAction) {
                MobileTrainerCalendarVisitedScreenCreateActionComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileTrainerCalendarVisitedScreenInfoComponent()
            MobileTrainerCalendarVisitedScreenCalendarGridComponent()
            MobileTrainerCalendarVisitedScreenPrivateClassesComponent()
        }
    }
}


@Composable
private fun MobileTrainerCalendarVisitedScreenToolbarComponent() {
    TodoBox("MobileTrainerCalendarVisitedScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileTrainerCalendarVisitedScreenInfoComponent() {
    TodoBox("MobileTrainerCalendarVisitedScreenInfoComponent", Modifier.size(398.dp, 132.dp))
}

@Composable
private fun MobileTrainerCalendarVisitedScreenCalendarGridComponent() {
    TodoBox("MobileTrainerCalendarVisitedScreenCalendarGridComponent", Modifier.size(398.dp, 416.dp))
}

@Composable
private fun MobileTrainerCalendarVisitedScreenPrivateClassesComponent() {
    TodoBox("MobileTrainerCalendarVisitedScreenPrivateClassesComponent", Modifier.size(398.dp, 548.dp))
}

@Composable
private fun MobileTrainerCalendarVisitedScreenCreateActionComponent() {
    TodoBox("MobileTrainerCalendarVisitedScreenCreateActionComponent", Modifier.size(430.dp, 48.dp))
}
