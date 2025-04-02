package com.vurgun.skyfit.designsystem.components.popup

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.designsystem.components.image.CircleNetworkImage

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
            trainers.forEach {
                SelectablePopupMenuItem(
                    selected = it.selected,
                    onSelect = {
                        onSelectionChanged(it)
                        onDismiss()
                    },
                    content = {
                        CircleNetworkImage(it.imageUrl, size = 24.dp)
                        BodyMediumRegularText(it.name, modifier = Modifier.weight(1f))
                    }
                )

                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
            }
        }
    }
}