package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsScreen(navigator: Navigator) {

    SkyFitScaffold {

        Column {
            MobileFacilitySettingsScreenToolbarComponent()
            Spacer(Modifier.height(24.dp))
            MobileFacilitySettingsScreenOptionsComponent()
            Spacer(Modifier.weight(1f))
            MobileFacilitySettingsScreenSingOutActionsComponent()
        }
    }
}


@Composable
private fun MobileFacilitySettingsScreenToolbarComponent() {
    TodoBox("MobileFacilitySettingsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilitySettingsScreenOptionsComponent() {
    TodoBox("MobileFacilitySettingsScreenOptionsComponent", Modifier.size(430.dp, 424.dp))
}

@Composable
private fun MobileFacilitySettingsScreenSingOutActionsComponent() {
    TodoBox("MobileFacilitySettingsScreenSingOutActionsComponent", Modifier.size(430.dp, 230.dp))
}