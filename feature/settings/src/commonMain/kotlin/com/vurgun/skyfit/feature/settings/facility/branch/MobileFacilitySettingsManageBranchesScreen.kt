package com.vurgun.skyfit.feature.settings.facility.branch

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader

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