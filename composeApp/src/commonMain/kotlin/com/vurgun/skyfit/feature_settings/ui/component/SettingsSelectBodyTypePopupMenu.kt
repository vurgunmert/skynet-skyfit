package com.vurgun.skyfit.feature_settings.ui.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.domain.models.BodyType
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.designsystem.components.popup.BasicPopupMenu
import com.vurgun.skyfit.designsystem.components.popup.SelectablePopupMenuItem

@Composable
fun SettingsSelectBodyTypePopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedBodyType: BodyType,
    onSelectionChanged: (BodyType) -> Unit
) {
    val options = listOf(
        BodyType.ECTOMORPH,
        BodyType.MESOMORPH,
        BodyType.ENDOMORPH
    )

    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            options.forEachIndexed { index, type ->
                SelectablePopupMenuItem(
                    selected = selectedBodyType == type,
                    onSelect = {
                        onSelectionChanged(type)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(type.turkishName, modifier = Modifier.weight(1f))
                    }
                )
                if (index != options.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}