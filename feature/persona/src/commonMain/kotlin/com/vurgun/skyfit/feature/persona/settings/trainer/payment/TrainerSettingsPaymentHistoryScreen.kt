package com.vurgun.skyfit.feature.persona.settings.trainer.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItem
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItemComponent

class TrainerSettingsPaymentHistoryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MobileTrainerSettingsPaymentHistoryScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
fun MobileTrainerSettingsPaymentHistoryScreen(goToBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = goToBack)
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