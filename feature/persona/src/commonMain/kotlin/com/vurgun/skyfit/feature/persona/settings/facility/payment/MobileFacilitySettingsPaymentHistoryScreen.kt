package com.vurgun.skyfit.feature.persona.settings.facility.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItemComponent

class FacilitySettingsPaymentHistoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityPaymentHistoryViewModel>()

        MobileFacilitySettingsPaymentHistoryScreen(
            goToBack = { navigator.pop() },
            viewModel = viewModel
        )
    }
}


@Composable
fun MobileFacilitySettingsPaymentHistoryScreen(
    goToBack: () -> Unit,
    viewModel: FacilityPaymentHistoryViewModel
) {
    val historyItems by viewModel.paymentHistoryItems.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = goToBack)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(historyItems) { item ->
                PaymentHistoryItemComponent(item)
            }
        }
    }
}
