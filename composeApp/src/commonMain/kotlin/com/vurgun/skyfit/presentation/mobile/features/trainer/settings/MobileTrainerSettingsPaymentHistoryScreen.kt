package com.vurgun.skyfit.presentation.mobile.features.trainer.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.settings.PaymentHistoryItem
import com.vurgun.skyfit.presentation.shared.features.settings.PaymentHistoryItemComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerSettingsPaymentHistoryScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = { navigator.popBackStack() })
        }
    ) {
        MobileTrainerSettingsPaymentHistoryScreenComponent()
    }
}

@Composable
private fun MobileTrainerSettingsPaymentHistoryScreenComponent() {
    var historyItems = listOf(
        PaymentHistoryItem(
            date = "29/10/2024 16:45",
            trainer = "Micheal Blake",
            className = "Pilates",
            cost = "\$306.00"
        ),
        PaymentHistoryItem(
            date = "29/10/2024 16:45",
            trainer = "Micheal Blake",
            className = "Pilates",
            cost = "\$306.00"
        ),
        PaymentHistoryItem(
            date = "29/10/2024 16:45",
            trainer = "Micheal Blake",
            className = "Pilates",
            cost = "\$306.00"
        ),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(historyItems) {
            PaymentHistoryItemComponent(it)
        }
    }
}