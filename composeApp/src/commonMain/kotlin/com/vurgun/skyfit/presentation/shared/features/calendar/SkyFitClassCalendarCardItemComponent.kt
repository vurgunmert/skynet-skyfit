package com.vurgun.skyfit.presentation.shared.features.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

data class SkyFitClassCalendarCardItem(
    val title: String,
    val date: String? = null,
    val hours: String? = null,
    val category: String? = null,
    val location: String? = null,
    val trainer: String? = null,
    val capacity: String? = null,
    val cost: String? = null,
    val note: String? = null,
    val enabled: Boolean = false,
    val selected: Boolean = false,
    val booked: Boolean = false
)

@Composable
fun SkyFitClassCalendarCardItemComponent(item: SkyFitClassCalendarCardItem, onClick: () -> Unit) {

    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = SkyFitColor.border.secondaryButton,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = item.title,
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "(${item.capacity})",
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            item.date?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }

            item.hours?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }

            item.location?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }

            item.trainer?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }

            item.category?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }

            item.note?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it)
            }
        }
    }
}

@Composable
private fun SkyFitClassCalendarCardItemRowComponent(value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = SkyFitColor.icon.secondary
        )
        Text(
            text = value,
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier.weight(1f),
            color = SkyFitColor.text.secondary
        )
    }
}