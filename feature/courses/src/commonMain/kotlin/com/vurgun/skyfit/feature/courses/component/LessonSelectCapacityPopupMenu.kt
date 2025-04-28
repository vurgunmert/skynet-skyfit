package com.vurgun.skyfit.feature.courses.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun LessonSelectCapacityPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedCapacity: Int = 1,
    maxCapacity: Int = 50,
    onSelectionChanged: (Int) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            (1..maxCapacity).forEach { value ->
                SelectablePopupMenuItem(
                    selected = value == selectedCapacity,
                    onSelect = {
                        onSelectionChanged(value)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(value.toString(), modifier = Modifier.weight(1f))
                    }
                )
                if (value != maxCapacity) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}