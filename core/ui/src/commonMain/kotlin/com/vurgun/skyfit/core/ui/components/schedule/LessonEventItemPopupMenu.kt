package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.popup.TextPopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.activate_action
import skyfit.core.ui.generated.resources.delete_action
import skyfit.core.ui.generated.resources.disable_action
import skyfit.core.ui.generated.resources.edit_action
import skyfit.core.ui.generated.resources.ic_check_circle
import skyfit.core.ui.generated.resources.ic_close_circle
import skyfit.core.ui.generated.resources.ic_delete
import skyfit.core.ui.generated.resources.ic_pencil

@Composable
fun LessonEventItemPopupMenu(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onActivate: (() -> Unit)? = null,
    onDeactivate: (() -> Unit)? = null,
) {
    BasicPopupMenu(
        modifier = Modifier.width(160.dp),
        isOpen = isOpen,
        onDismiss = onDismiss
    ) {
        onDeactivate?.let {
            TextPopupMenuItem(
                text = stringResource(Res.string.disable_action),
                iconRes = Res.drawable.ic_close_circle,
                onClick = {
                    it()
                    onDismiss()
                }
            )
            Divider(color = SkyFitColor.border.default, thickness = 1.dp)
        }

        onActivate?.let { action ->
            TextPopupMenuItem(
                text = stringResource(Res.string.activate_action),
                iconRes = Res.drawable.ic_check_circle,
                onClick = {
                    action()
                    onDismiss()
                }
            )
            Divider(color = SkyFitColor.border.default, thickness = 1.dp)
        }

        TextPopupMenuItem(
            text = stringResource(Res.string.edit_action),
            iconRes = Res.drawable.ic_pencil,
            onClick = {
                onEdit()
                onDismiss()
            }
        )

        Divider(color = SkyFitColor.border.default, thickness = 1.dp)

        TextPopupMenuItem(
            text = stringResource(Res.string.delete_action),
            iconRes = Res.drawable.ic_delete,
            onClick = {
                onDelete()
                onDismiss()
            },
            textColor = SkyFitColor.text.criticalOnBgFill,
            iconTint = SkyFitColor.icon.critical
        )
    }
}