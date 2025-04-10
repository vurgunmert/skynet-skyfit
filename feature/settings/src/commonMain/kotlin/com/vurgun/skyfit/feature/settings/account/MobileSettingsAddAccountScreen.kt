package com.vurgun.skyfit.feature.settings.account

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader

@Composable
fun MobileSettingsAddAccountScreen(
    goToBack: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Hesap Ekle", onClickBack = goToBack)
        }
    ) {

    }
}