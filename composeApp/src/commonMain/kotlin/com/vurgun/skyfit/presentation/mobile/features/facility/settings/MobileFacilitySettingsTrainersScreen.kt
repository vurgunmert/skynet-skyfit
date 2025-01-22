package com.vurgun.skyfit.presentation.mobile.features.facility.settings

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
fun MobileFacilitySettingsTrainersScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilitySettingsTrainersScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilitySettingsTrainersScreenSearchComponent()
            MobileFacilitySettingsTrainersComponent()
        }
    }
}

@Composable
private fun MobileFacilitySettingsTrainersScreenToolbarComponent() {
    TodoBox("MobileFacilitySettingsTrainersScreenToolbarComponent", Modifier.size(430.dp, 52.dp))
}


@Composable
private fun MobileFacilitySettingsTrainersScreenSearchComponent() {
    TodoBox("MobileFacilitySettingsTrainersScreenSearchComponent", Modifier.size(398.dp, 44.dp))
}


@Composable
private fun MobileFacilitySettingsTrainersComponent() {
    TodoBox("MobileFacilitySettingsTrainersComponent", Modifier.size(398.dp, 476.dp))
}