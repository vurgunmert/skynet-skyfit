package com.vurgun.skyfit.feature.courses.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.ui.core.components.image.CircleNetworkImage
import com.vurgun.skyfit.ui.core.components.popup.BasicPopupMenu
import com.vurgun.skyfit.ui.core.components.text.BodyMediumRegularText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor

data class SelectableTrainerMenuItemModel(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val selected: Boolean = false
)

@Composable
fun LessonSelectTrainerPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    trainers: List<SelectableTrainerMenuItemModel>,
    onSelectionChanged: (SelectableTrainerMenuItemModel) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            trainers.forEachIndexed { index, item ->
                SelectablePopupMenuItem(
                    selected = item.selected,
                    onSelect = {
                        onSelectionChanged(item)
                        onDismiss()
                    },
                    content = {
                        CircleNetworkImage(item.imageUrl, size = 24.dp)
                        BodyMediumRegularText(item.name, modifier = Modifier.weight(1f))
                    }
                )
                if (index != trainers.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}