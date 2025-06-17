package com.vurgun.skyfit.profile.facility.gallery

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar

@Composable
fun MobileFacilityPhotoDiaryScreen(
    goToBack: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar(title = "Facility Photos", onClickBack = goToBack)
        }
    ) {

    }
}
