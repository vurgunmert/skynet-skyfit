package com.vurgun.skyfit.feature.settings.facility.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.settings.payment.FacilityPaymentHistoryViewModel
import com.vurgun.skyfit.feature.settings.payment.PaymentHistoryItemComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader

@Composable
fun MobileFacilitySettingsPaymentHistoryScreen(
    goToBack: () -> Unit
) {
    val viewModel = remember { FacilityPaymentHistoryViewModel() }
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
