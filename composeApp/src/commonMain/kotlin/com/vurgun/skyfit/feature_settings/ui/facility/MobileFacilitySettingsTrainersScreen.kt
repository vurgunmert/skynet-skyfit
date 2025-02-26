package com.vurgun.skyfit.feature_settings.ui.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsTrainersScreen(navigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            Column {
                MobileFacilitySettingsSearchUserToolbarComponent(
                    title = "Egitmenler",
                    onClickBack = { navigator.popBackStack() },
                    onClickAdd = { }
                )
            }
        }
    ) {

    }
}