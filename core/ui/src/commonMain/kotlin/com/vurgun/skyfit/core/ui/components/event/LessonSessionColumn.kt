package com.vurgun.skyfit.core.ui.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

@Composable
fun LessonSessionColumn(
    lessons: List<LessonSessionItemViewData>,
    onClickItem: ((LessonSessionItemViewData) -> Unit)? = null,
    onClickShowAll: (() -> Unit)? = null,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(SkyFitColor.background.fillTransparent),
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = SkyFitAsset.getPainter(SkyFitAsset.SkyFitIcon.EXERCISES.id),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                tint = SkyFitColor.icon.default
            )

            Text(
                text = stringResource(Res.string.lessons_label),
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.weight(1f)
            )

            if (onClickShowAll != null) {
                Text(
                    text = stringResource(Res.string.show_all_action),
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
            lessons.forEach { item ->
                AvailableActivityCalendarEventItem(
                    title = item.title,
                    iconId = item.iconId,
                    date = item.date.toString(),
                    timePeriod = item.hours.toString(),
                    location = item.location.toString(),
                    trainer = item.trainer.toString(),
                    capacity = item.capacityRatio.toString(),
                    note = item.note,
                    modifier = Modifier.clickable { onClickItem?.invoke(item) }
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
    val textColor = if (item.isActive) SkyFitColor.text.default else SkyFitColor.text.disabled
    val subTextColor = if (item.isActive) SkyFitColor.text.secondary else SkyFitColor.text.disabled
    val iconColor = if (item.isActive) SkyFitColor.icon.default else SkyFitColor.icon.disabled

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

            item.capacityRatio?.let {
                Text(
                    text = it,
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