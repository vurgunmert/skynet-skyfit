package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun LessonSelectCancelDurationPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedDuration: Int = 24,
    hours: List<Int> = listOf(8, 12, 16, 24, 36, 48),
    onSelectionChanged: (Int) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            hours.forEachIndexed { index, value ->
                SelectablePopupMenuItem(
                    selected = value == selectedDuration,
                    onSelect = {
                        onSelectionChanged(value)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText("$value saat kala", modifier = Modifier.weight(1f))
                    }
                )
                if (index != hours.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}

@Composable
fun MembershipPackagePopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selected: FacilityLessonPackageDTO,
    packages: List<FacilityLessonPackageDTO>,
    onSelectionChanged: (FacilityLessonPackageDTO) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            packages.forEachIndexed { index, value ->
                SelectablePopupMenuItem(
                    selected = value == selected,
                    onSelect = {
                        onSelectionChanged(value)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText("$value saat kala", modifier = Modifier.weight(1f))
                    }
                )
                if (index != packages.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}