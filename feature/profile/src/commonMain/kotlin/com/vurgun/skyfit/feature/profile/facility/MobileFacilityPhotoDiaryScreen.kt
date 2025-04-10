package com.vurgun.skyfit.feature.profile.facility

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader

@Composable
fun MobileFacilityPhotoDiaryScreen(
    goToBack: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(title = "Facility Photos", onClickBack = goToBack)
        }
    ) {

    }
}
