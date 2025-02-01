package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
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
                MobileFacilitySettingsSearchUserComponent()
            }
        }
    ) {
        MobileFacilitySettingsSearchResultsComponent()
    }
}