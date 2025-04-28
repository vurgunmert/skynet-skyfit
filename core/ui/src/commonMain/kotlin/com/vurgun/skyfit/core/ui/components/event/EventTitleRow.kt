package com.vurgun.skyfit.core.ui.components.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.CircularDeleteIcon
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.components.text.BodySmallRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.lesson_status_completed
import skyfit.core.ui.generated.resources.lesson_status_missing

@Composable
fun EventTitleRow(
    title: String,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    endContent: (@Composable RowScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        iconId?.let { iconId ->
            Icon(
                painter = SkyFitAsset.getPainter(iconId),
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
        }

        BodyMediumSemiboldText(
            text = title,
            modifier = Modifier.weight(1f)
        )

        endContent?.invoke(this)
    }
}

@Composable
fun BasicActivityEventTitleRow(title: String, iconId: Int? = null, timePeriod: String) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        EventBadge(value = timePeriod)
    })
}

@Composable
fun BookedActivityEventTitleRow(title: String, iconId: Int? = null, timePeriod: String) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        EventBadge(value = timePeriod)
    })
}

@Composable
fun AvailableActivityEventTitleRow(title: String, iconId: Int? = null, date: String, capacity: String, isFull: Boolean) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        BodySmallRegularText(text = date)
        EventBadge(value = capacity, state = if (isFull) BadgeState.Warning else BadgeState.Default)
    })
}

@Composable
fun BasicAppointmentEventTitleRow(title: String, iconId: Int? = null, date: String) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        BodyMediumSemiboldText(text = date)
    })
}

@Composable
fun ActiveAppointmentEventTitleRow(title: String, iconId: Int? = null, date: String, onDelete: () -> Unit) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        BodyMediumSemiboldText(text = date, color = SkyFitColor.text.secondary)
        CircularDeleteIcon(onClick = onDelete)
    })
}

@Composable
fun AttendanceAppointmentEventTitleRow(title: String, iconId: Int? = null, date: String, isCompleted: Boolean = false) {
    EventTitleRow(title = title, iconId = iconId, endContent = {
        BodyMediumSemiboldText(text = date, color = SkyFitColor.text.secondary)
        if (isCompleted) {
            EventBadge(value = stringResource(Res.string.lesson_status_completed), state = BadgeState.Success)
        } else {
            EventBadge(value = stringResource(Res.string.lesson_status_missing), state = BadgeState.Error)
        }
    })
}
