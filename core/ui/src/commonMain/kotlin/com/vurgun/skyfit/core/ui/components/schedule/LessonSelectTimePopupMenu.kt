package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun LessonSelectTimePopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedTime: String = "09:00",
    timeOptions: List<String> = generateTimeOptions(),
    onSelectionChanged: (String) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            timeOptions.forEachIndexed { index, time ->
                SelectablePopupMenuItem(
                    selected = time == selectedTime,
                    onSelect = {
                        onSelectionChanged(time)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(
                            text = time,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .weight(1f)
                        )
                    }
                )
                if (index != timeOptions.lastIndex) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = SkyFitColor.border.default
                    )
                }
            }
        }
    }
}

fun generateTimeOptions(): List<String> {
    return buildList {
        for (hour in 0..23) {
            val hourStr = hour.toString().padStart(2, '0')
            add("$hourStr:00")
            add("$hourStr:30")
        }
    }
}

