package com.vurgun.skyfit.feature.settings.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold

@Composable
fun MobileFacilitySettingsManageTrainersScreen(
    goToBack: () -> Unit,
    goToAddTrainer: () -> Unit,
) {

    SkyFitScaffold(
        topBar = {
            Column {
                MobileFacilitySettingsSearchUserToolbarComponent(
                    title = "Egitmenler",
                    onClickBack = goToBack,
                    onClickAdd = goToAddTrainer
                )
            }
        }
    ) {

    }
}