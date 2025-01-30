package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsPaymentHistoryScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödeme Geçmişi", onBackClick = { navigator.popBackStack() })
        }
    ) {
        MobileUserSettingsPaymentHistoriesComponent()
    }
}

@Composable
private fun MobileUserSettingsPaymentHistoriesComponent() {
    var historyItems = listOf(
        MobileClassPaymentInfoItem(
            date = "29/10/2024 16:45",
            trainer = "Micheal Blake",
            className = "Pilates",
            cost = "\$306.00"
        ),
        MobileClassPaymentInfoItem(
            date = "29/10/2024 16:45",
            trainer = "Micheal Blake",
            className = "Pilates",
            cost = "\$306.00"
        ),
        MobileClassPaymentInfoItem(
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
            MobileClassPaymentHistoryItemComponent(it)
        }
    }
}


data class MobileClassPaymentInfoItem(
    val date: String,
    val trainer: String,
    val className: String,
    val cost: String
)


@Composable
private fun MobileClassPaymentHistoryItemComponent(item: MobileClassPaymentInfoItem) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = item.date,
                style = SkyFitTypography.bodyMediumSemibold
            )
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Egitmen",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.trainer,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Ders",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.className,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Toplam",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.cost,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
        }
    }
}