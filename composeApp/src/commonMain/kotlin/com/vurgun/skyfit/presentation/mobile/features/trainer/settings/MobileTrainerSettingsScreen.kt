package com.vurgun.skyfit.presentation.mobile.features.trainer.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerSettingsScreen(navigator: Navigator) {

    SkyFitScaffold {

        Column {
            MobileTrainerSettingsScreenToolbarComponent()
            Spacer(Modifier.height(24.dp))
            MobileTrainerSettingsScreenOptionsComponent()
            Spacer(Modifier.weight(1f))
            MobileTrainerSettingsScreenSingOutActionsComponent()
        }
    }
}


@Composable
private fun MobileTrainerSettingsScreenToolbarComponent() {
    TodoBox("MobileTrainerSettingsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}
@Composable
private fun MobileTrainerSettingsScreenOptionsComponent() {
    TodoBox("MobileTrainerSettingsScreenOptionsComponent", Modifier.size(430.dp, 480.dp))
}
@Composable
private fun MobileTrainerSettingsScreenSingOutActionsComponent() {
    TodoBox("MobileTrainerSettingsScreenSingOutActionsComponent", Modifier.size(430.dp, 230.dp))
}