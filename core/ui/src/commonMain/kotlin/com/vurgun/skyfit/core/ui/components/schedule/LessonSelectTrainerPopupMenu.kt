package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.image.CircleNetworkImage
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_high_intensity_training

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
                        SkyImage(
                            url = item.imageUrl,
                            size = SkyImageSize.Size32,
                            shape = SkyImageShape.Circle,
                            error = Res.drawable.ic_high_intensity_training,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SkyFitColor.background.fillTransparentSecondary)
                        )
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