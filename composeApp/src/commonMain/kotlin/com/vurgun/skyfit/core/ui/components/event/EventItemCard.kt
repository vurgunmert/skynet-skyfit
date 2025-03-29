package com.vurgun.skyfit.core.ui.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_dots_vertical
import skyfit.composeapp.generated.resources.ic_location_pin
import skyfit.composeapp.generated.resources.ic_note


@Composable
fun EventItem(
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
                    color = textColor,
                )
            }
        }

        item.hours?.let {
            Spacer(Modifier.height(8.dp))
            EventFieldTextCard(it, iconRes = Res.drawable.ic_clock)
        }

        item.duration?.let {
            Spacer(Modifier.height(8.dp))
            EventFieldTextCard(it, iconRes = Res.drawable.ic_clock)
        }

        item.location?.let {
            Spacer(Modifier.height(8.dp))
            EventFieldTextCard(it, iconRes = Res.drawable.ic_location_pin)
        }

        item.note?.let {
            Spacer(Modifier.height(8.dp))
            EventFieldTextCard(it, iconRes = Res.drawable.ic_note)
        }
    }
}


@Composable
private fun EventItemColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}


@Composable
fun NewLessonEventItem(
    title: String,
    iconId: String,
    date: String,
    timePeriod: String,
    category: String,
    location: String,
    trainer: String,
    capacity: String,
    cost: String
) {
    EventItemColumn {
        BasicAppointmentEventTitleRow(title, iconId, date)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventTimeText(timePeriod)
            EventCapacityText(category)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventLocationText(location)
            EventTrainerText(trainer)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventCapacityText(capacity)
            EventCostText(cost)
        }
    }
}

@Composable
fun EditableLessonEventItem(
    title: String,
    iconId: String,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String?,
    isMenuOpen: Boolean = false,
    onMenuToggle: ((Boolean) -> Unit),
    menuContent: (@Composable (() -> Unit))
) {
    EventItemColumn {

        EventTitleRow(title = title, iconId = iconId) {

            BodyMediumSemiboldText(date, color = SkyFitColor.text.secondary)

            Box {
                Icon(
                    painter = painterResource(Res.drawable.ic_dots_vertical),
                    contentDescription = "Menu",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp).clickable { onMenuToggle(!isMenuOpen) }
                )
                if (isMenuOpen) {
                    menuContent()
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventTimeText(timePeriod, modifier = Modifier.weight(1f))
            EventTrainerText(trainer, modifier = Modifier.weight(1f))
        }
        EventLocationText(location)

        note.takeUnless { it.isNullOrEmpty() }?.let {
            EventNoteText(it)
        }
    }
}