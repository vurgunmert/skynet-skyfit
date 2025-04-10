package com.vurgun.skyfit.feature.settings.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.settings.payment.PaymentHistoryItem
import com.vurgun.skyfit.feature.settings.payment.PaymentHistoryItemComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.styling.SkyFitColor

@Composable
fun MobileUserSettingsPaymentHistoryScreen(goToBack: () -> Unit) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = goToBack)
        }
    ) {
        MobileUserSettingsPaymentHistoriesComponent()
    }
}

@Composable
fun MobileUserSettingsPaymentHistoriesComponent() {
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