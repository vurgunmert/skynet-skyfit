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
fun MobileFacilitySettingsAccountScreen(navigator: Navigator) {

    val showDeleteConfirm: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilitySettingsAccountScreenToolbarComponent()
        },
        bottomBar = {
            MobileFacilitySettingsAccountScreenSaveActionComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilitySettingsAccountScreenInputComponent()
        }
    }
}


@Composable
private fun MobileFacilitySettingsAccountScreenToolbarComponent() {
    TodoBox("MobileFacilitySettingsAccountScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilitySettingsAccountScreenInputComponent() {
    TodoBox("MobileFacilitySettingsAccountScreenInputComponent", Modifier.size(382.dp, 520.dp))
}

@Composable
private fun MobileFacilitySettingsAccountScreenSaveActionComponent() {
    TodoBox("MobileFacilitySettingsAccountScreenSaveActionComponent", Modifier.size(382.dp, 48.dp))
}