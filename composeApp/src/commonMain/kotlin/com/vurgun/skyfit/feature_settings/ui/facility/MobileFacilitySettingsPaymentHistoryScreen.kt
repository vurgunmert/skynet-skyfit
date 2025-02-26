package com.vurgun.skyfit.feature_settings.ui.facility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.feature_payment.ui.PaymentHistoryItemComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.feature_payment.ui.FacilityPaymentHistoryViewModel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsPaymentHistoryScreen(
    navigator: Navigator
) {
    val viewModel = remember { FacilityPaymentHistoryViewModel() }
    val historyItems by viewModel.paymentHistoryItems.collectAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onClickBack = { navigator.popBackStack() })
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
