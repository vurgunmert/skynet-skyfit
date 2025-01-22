package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsPaymentHistoryScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSettingsPaymentHistoryScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSettingsPaymentHistoriesComponent()
        }
    }
}


@Composable
private fun MobileUserSettingsPaymentHistoryScreenToolbarComponent() {
    TodoBox("MobileUserSettingsPaymentHistoryScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserSettingsPaymentHistoriesComponent() {
    TodoBox("MobileUserSettingsPaymentHistoriesComponent", Modifier.size(382.dp, 512.dp))
}