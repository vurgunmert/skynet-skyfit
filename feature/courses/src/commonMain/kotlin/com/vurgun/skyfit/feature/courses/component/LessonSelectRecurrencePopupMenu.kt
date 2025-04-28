package com.vurgun.skyfit.feature.courses.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.data.domain.model.CalendarRecurrenceType
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.recurrence_daily_label
import skyfit.core.ui.generated.resources.recurrence_none_label
import skyfit.core.ui.generated.resources.recurrence_weekly_label

@Composable
fun LessonSelectRecurrenceTypePopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedRecurrenceType: CalendarRecurrenceType,
    onSelectionChanged: (CalendarRecurrenceType) -> Unit
) {

    val recurrenceOptions = listOf(
        CalendarRecurrenceType.NEVER to stringResource(Res.string.recurrence_none_label),
        CalendarRecurrenceType.DAILY to stringResource(Res.string.recurrence_daily_label),
        CalendarRecurrenceType.SOMEDAYS to stringResource(Res.string.recurrence_weekly_label)
    )

    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            recurrenceOptions.forEachIndexed { index, (type, label) ->
                SelectablePopupMenuItem(
                    selected = selectedRecurrenceType == type,
                    onSelect = {
                        onSelectionChanged(type)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(label, modifier = Modifier.weight(1f))
                    }
                )
                if (index != recurrenceOptions.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}