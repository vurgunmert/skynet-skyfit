package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsPaymentHistoriesComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsPaymentHistoryScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = { navigator.popBackStack() })
        }
    ) {
        MobileUserSettingsPaymentHistoriesComponent()
    }
}