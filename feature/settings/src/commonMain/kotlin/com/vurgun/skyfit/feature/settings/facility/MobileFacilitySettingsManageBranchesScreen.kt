package com.vurgun.skyfit.feature.settings.facility

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader

@Composable
fun MobileFacilitySettingsManageBranchesScreen(
    goToBack: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Şubeler", onClickBack = goToBack)
        }
    ) {

    }
}