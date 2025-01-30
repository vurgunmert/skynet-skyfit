package com.vurgun.skyfit.presentation.shared.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography


data class PaymentHistoryItem(
    val date: String,
    val trainer: String,
    val className: String,
    val cost: String
)

@Composable
fun PaymentHistoryItemComponent(item: PaymentHistoryItem) {
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