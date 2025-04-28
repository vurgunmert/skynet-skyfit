package com.vurgun.skyfit.feature.profile.facility.gallery

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader

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
