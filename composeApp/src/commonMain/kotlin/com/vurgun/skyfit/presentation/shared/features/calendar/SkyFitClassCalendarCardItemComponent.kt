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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_dashboard
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_location_pin
import skyfit.composeapp.generated.resources.ic_note
import skyfit.composeapp.generated.resources.ic_profile_fill

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
    val booked: Boolean = false,
    val iconId: String = ""
)

@Composable
fun SkyFitClassCalendarCardItemComponent(
    item: SkyFitClassCalendarCardItem,
    onClick: (SkyFitClassCalendarCardItem) -> Unit
) {
    val textColor = if (item.enabled) SkyFitColor.text.default else SkyFitColor.text.disabled
    val subTextColor = if (item.enabled) SkyFitColor.text.secondary else SkyFitColor.text.disabled
    val iconColor = if (item.enabled) SkyFitColor.icon.default else SkyFitColor.icon.disabled

    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .then(
                if (item.selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = SkyFitColor.border.secondaryButton,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .clickable(onClick = { onClick(item) })
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = SkyFitIcon.getIconResourcePainter(item.iconId, defaultRes = Res.drawable.ic_exercises),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
                Text(
                    text = item.title,
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "(${item.capacity})",
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor
                )
            }

            item.date?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
            }

            item.hours?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
            }

            item.location?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_location_pin, subTextColor, iconColor)
            }

            item.trainer?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_profile_fill, subTextColor, iconColor)
            }

            item.category?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_dashboard, subTextColor, iconColor)
            }

            item.note?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_note, subTextColor, iconColor)
            }
        }
    }
}

@Composable
fun SkyFitClassCalendarCardItemRowComponent(
    value: String,
    iconRes: DrawableResource,
    textColor: Color = SkyFitColor.text.default,
    iconColor: Color = SkyFitColor.icon.default
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = iconColor
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = value,
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier.weight(1f),
            color = textColor
        )
    }
}