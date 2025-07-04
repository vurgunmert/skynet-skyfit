package com.vurgun.skyfit.settings.user.payment

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
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItem
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItemComponent

class UserSettingsPaymentHistoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MobileUserSettingsPaymentHistoryScreen(
            goToBack = navigator::pop
        )
    }

}

@Composable
private fun MobileUserSettingsPaymentHistoryScreen(goToBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar("Ödeme Geçmişi", onClickBack = goToBack)
        }
    ) {
        MobileUserSettingsPaymentHistoriesComponent()
    }
}

@Composable
private fun MobileUserSettingsPaymentHistoriesComponent() {
    val historyItems = listOf(
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