package com.vurgun.skyfit.feature.settings.account

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader

@Composable
fun MobileSettingsManageAccountsScreen(
    goToBack: () -> Unit,
    goToAddAccount: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Hesaplar", onClickBack = goToBack)
        }
    ) {

    }
}