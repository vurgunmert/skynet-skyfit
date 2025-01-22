package com.vurgun.skyfit.presentation.mobile.features.facility.settings

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
fun MobileFacilitySettingsHelpScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilitySettingsHelpScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilitySettingsHelpScreenOptionsComponent()
        }
    }
}


@Composable
private fun MobileFacilitySettingsHelpScreenToolbarComponent() {
    TodoBox("MobileFacilitySettingsHelpScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilitySettingsHelpScreenOptionsComponent() {
    TodoBox("MobileFacilitySettingsHelpScreenOptionsComponent", Modifier.size(382.dp, 288.dp))
}