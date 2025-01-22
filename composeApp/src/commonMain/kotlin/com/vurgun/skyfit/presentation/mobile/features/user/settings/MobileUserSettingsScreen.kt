package com.vurgun.skyfit.presentation.mobile.features.user.settings

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
fun MobileUserSettingsScreen(navigator: Navigator) {

    SkyFitScaffold {

        Column {
            MobileUserSettingsScreenToolbarComponent()
            Spacer(Modifier.height(24.dp))
            MobileUserSettingsScreenOptionsComponent()
            Spacer(Modifier.weight(1f))
            MobileUserSettingsScreenSingOutActionsComponent()
        }
    }
}


@Composable
private fun MobileUserSettingsScreenToolbarComponent() {
    TodoBox("MobileUserSettingsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}
@Composable
private fun MobileUserSettingsScreenOptionsComponent() {
    TodoBox("MobileUserSettingsScreenOptionsComponent", Modifier.size(430.dp, 480.dp))
}
@Composable
private fun MobileUserSettingsScreenSingOutActionsComponent() {
    TodoBox("MobileUserSettingsScreenSingOutActionsComponent", Modifier.size(430.dp, 230.dp))
}