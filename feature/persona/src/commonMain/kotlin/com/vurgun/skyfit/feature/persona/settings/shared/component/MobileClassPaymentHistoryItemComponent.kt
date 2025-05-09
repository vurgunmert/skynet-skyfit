package com.vurgun.skyfit.feature.persona.settings.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

data class PaymentHistoryItem(
    val date: String,
    val trainer: String,
    val className: String,
    val cost: String,
    val paidBy: String? = null
)

@Composable
fun PaymentHistoryItemComponent(item: PaymentHistoryItem) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = item.date,
                style = SkyFitTypography.bodyMediumSemibold
            )
            if (item.paidBy != null) {
                Row {
                    Text(
                        text = "Ödeme yapan",
                        modifier = Modifier.weight(1f),
                        style = SkyFitTypography.bodyMediumMedium,
                        color = SkyFitColor.text.secondary
                    )

                    Text(
                        text = item.paidBy,
                        style = SkyFitTypography.bodyMediumSemibold
                    )
                }
            }

            Row {
                Text(
                    text = "Egitmen",
                    modifier = Modifier.weight(1f),
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )

                Text(
                    text = item.trainer,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }

            Row {
                Text(
                    text = "Ders",
                    modifier = Modifier.weight(1f),
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )

                Text(
                    text = item.className,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }

            Row{
                Text(
                    text = "Toplam",
                    modifier = Modifier.weight(1f),
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )

                Text(
                    text = item.cost,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
        }
    }
}