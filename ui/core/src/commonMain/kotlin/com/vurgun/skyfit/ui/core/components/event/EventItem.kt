package com.vurgun.skyfit.ui.core.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.special.SkyFitCheckBoxComponent
import com.vurgun.skyfit.ui.core.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_dots_vertical
import skyfit.ui.core.generated.resources.lesson_notify_me_action

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

//region ActivityCalendarEvents
@Composable
fun BasicActivityCalendarEventItem(
    title: String,
    iconId: Int?,
    timePeriod: String,
    enabled: Boolean = true
) {
    EventItemColumn(
        Modifier.alpha(if (enabled) 1f else 0.4f)
    ) {
        BasicActivityEventTitleRow(title, iconId, timePeriod)
    }
}

@Composable
fun BookedActivityCalendarEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String? = null,
    enabled: Boolean = true
) {
    EventItemColumn(
        Modifier.alpha(if (enabled) 1f else 0.4f)
    ) {
        BookedActivityEventTitleRow(title, iconId, timePeriod)
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

@Composable
fun PaidActivityCalendarEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    capacity: String,
    cost: String,
    note: String? = null,
    enabled: Boolean = true
) {
    EventItemColumn(
        Modifier.alpha(if (enabled) 1f else 0.4f)
    ) {
        BasicAppointmentEventTitleRow(title, iconId, date)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventTimeText(timePeriod, modifier = Modifier.weight(1f))
            EventTrainerText(trainer, modifier = Modifier.weight(1f))
        }
        EventLocationText(location)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventCapacityText(capacity, modifier = Modifier.weight(1f))
            EventCostText(cost, modifier = Modifier.weight(1f))
        }
        note.takeUnless { it.isNullOrEmpty() }?.let {
            EventNoteText(it)
        }
    }
}

@Composable
fun AvailableActivityCalendarEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    capacity: String,
    note: String? = null,
    isFull: Boolean = false,
    isNotifyMeEnabled: Boolean = false,
    onNotifyMeChanged: ((enabled: Boolean) -> Unit)? = null
) {
    EventItemColumn {
        AvailableActivityEventTitleRow(title, iconId, date, capacity, isFull)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventTimeText(timePeriod, modifier = Modifier.weight(1f))
            EventTrainerText(trainer, modifier = Modifier.weight(1f))
        }
        EventLocationText(location)

        note.takeUnless { it.isNullOrEmpty() }?.let {
            EventNoteText(it)
        }

        if (isFull) {
            SkyFitCheckBoxComponent(
                checked = isNotifyMeEnabled,
                onCheckedChange = { onNotifyMeChanged?.invoke(it) },
                label = stringResource(Res.string.lesson_notify_me_action),
                modifier = Modifier.fillMaxWidth().background(SkyFitColor.background.surfaceCriticalActive, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
    }
}

//endregion ActivityCalendarEvents

//region Appointment Events
@Composable
fun BasicAppointmentEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String? = null,
    onClick: () -> Unit
) {
    BasicLessonEventItem(title, iconId, date, timePeriod, location, trainer, note)
}

@Composable
fun ActiveAppointmentEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String? = null,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    EventItemColumn(modifier = Modifier.clickable(onClick = onClick)) {
        ActiveAppointmentEventTitleRow(title, iconId, date, onDelete = onDelete)
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

@Composable
fun AttendanceAppointmentEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String? = null,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    EventItemColumn(modifier = Modifier.clickable(onClick = onClick)) {
        AttendanceAppointmentEventTitleRow(title, iconId, date, isCompleted)
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
//endregion AppointmentEvents

//region LessonEvents
@Composable
fun BasicLessonEventItem(
    title: String,
    iconId: Int,
    date: String,
    timePeriod: String,
    location: String,
    trainer: String,
    note: String? = null
) {
    EventItemColumn {
        BasicAppointmentEventTitleRow(title, iconId, date)
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

@Composable
fun DetailedLessonEventItem(
    title: String,
    iconId: Int,
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
            EventTimeText(timePeriod, modifier = Modifier.weight(1f))
            EventCategoryText(category, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventLocationText(location, modifier = Modifier.weight(1f))
            EventTrainerText(trainer, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EventCapacityText(capacity, modifier = Modifier.weight(1f))
            EventCostText(cost, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun EditableLessonEventItem(
    title: String,
    iconId: Int,
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
//endregion LessonEvents