package com.vurgun.skyfit.ui.core.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_clock
import skyfit.ui.core.generated.resources.ic_dashboard
import skyfit.ui.core.generated.resources.ic_dots_vertical
import skyfit.ui.core.generated.resources.ic_location_pin
import skyfit.ui.core.generated.resources.ic_note
import skyfit.ui.core.generated.resources.ic_profile_fill

@Composable
fun LessonSessionColumn(
    viewData: LessonSessionColumnViewData,
    modifier: Modifier = Modifier,
    onClickItem: ((LessonSessionItemViewData) -> Unit)? = null,
    onClickShowAll: (() -> Unit)? = null
) {
    Column(
        modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = SkyFitAsset.getPainter(viewData.iconId),
                modifier = Modifier.size(24.dp),
                contentDescription = viewData.title,
                tint = SkyFitColor.icon.default
            )

            Text(
                text = viewData.title,
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.weight(1f)
            )

            if (onClickShowAll != null) {
                Text(
                    text = "Hepsini Görüntüle",
                    style = SkyFitTypography.bodyXSmall,
                    color = SkyFitColor.border.secondaryButton,
                    modifier = Modifier.clickable(onClick = onClickShowAll)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            viewData.items.forEach { item ->
                BasicLessonEventItem(
                    title = item.title,
                    iconId = item.iconId,
                    date = item.date.toString(),
                    timePeriod = item.hours.toString(),
                    location = item.location.toString(),
                    trainer = item.trainer.toString(),
                    note = item.note
                )
            }
        }
    }
}


@Composable
fun LessonSessionColumnItem(
    item: LessonSessionItemViewData,
    onClickItem: ((LessonSessionItemViewData) -> Unit)? = null,
    isMenuOpen: Boolean = false,
    onMenuToggle: ((Boolean) -> Unit)? = null,
    menuContent: (@Composable (() -> Unit))? = null
) {
    val textColor = if (item.enabled) SkyFitColor.text.default else SkyFitColor.text.disabled
    val subTextColor = if (item.enabled) SkyFitColor.text.secondary else SkyFitColor.text.disabled
    val iconColor = if (item.enabled) SkyFitColor.icon.default else SkyFitColor.icon.disabled

    Column(
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
            .clickable(enabled = onClickItem != null, onClick = { onClickItem?.invoke(item) })
            .padding(12.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = SkyFitAsset.getPainter(item.iconId),
                contentDescription = item.title,
                modifier = Modifier.size(24.dp),
                tint = iconColor
            )
            Text(
                text = item.title,
                style = SkyFitTypography.bodyMediumSemibold,
                modifier = Modifier.padding(start = 8.dp),
                color = textColor
            )
            Spacer(Modifier.weight(1f))

            if (menuContent != null) {
                Box {
                    Icon(
                        painter = painterResource(Res.drawable.ic_dots_vertical),
                        contentDescription = "Menu",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp).clickable { onMenuToggle?.invoke(!isMenuOpen) }
                    )
                    if (isMenuOpen) {
                        menuContent()
                    }
                }
            }

            if (item.enrolledCount != null && item.maxCapacity != null) {
                Text(
                    text = "(${item.enrolledCount}/${item.maxCapacity})",
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor
                )
            }
        }
        item.date?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
        }

        item.hours?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
        }

        item.duration?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
        }

        item.location?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_location_pin, subTextColor, iconColor)
        }

        item.trainer?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_profile_fill, subTextColor, iconColor)
        }

        item.category?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_dashboard, subTextColor, iconColor)
        }

        item.note?.let {
            Spacer(Modifier.height(8.dp))
            LessonSessionItemDetailRow(it, iconRes = Res.drawable.ic_note, subTextColor, iconColor)
        }
    }
}

@Composable
private fun LessonSessionItemDetailRow(
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