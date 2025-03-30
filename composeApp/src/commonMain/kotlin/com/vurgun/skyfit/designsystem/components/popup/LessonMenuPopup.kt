package com.vurgun.skyfit.designsystem.components.popup

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.activate_action
import skyfit.composeapp.generated.resources.delete_action
import skyfit.composeapp.generated.resources.disable_action
import skyfit.composeapp.generated.resources.edit_action
import skyfit.composeapp.generated.resources.ic_check_circle
import skyfit.composeapp.generated.resources.ic_close_circle
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_pencil

@Composable
fun LessonEventItemPopupMenu(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onActivate: (() -> Unit)? = null,
    onDeactivate: (() -> Unit)? = null,
) {
    BasicPopupMenu(isOpen, onDismiss) {
        onDeactivate?.let {
            PopupMenuItem(
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
            PopupMenuItem(
                text = stringResource(Res.string.activate_action),
                iconRes = Res.drawable.ic_check_circle,
                onClick = {
                    action()
                    onDismiss()
                }
            )
            Divider(color = SkyFitColor.border.default, thickness = 1.dp)
        }

        PopupMenuItem(
            text = stringResource(Res.string.edit_action),
            iconRes = Res.drawable.ic_pencil,
            onClick = {
                onEdit()
                onDismiss()
            }
        )

        Divider(color = SkyFitColor.border.default, thickness = 1.dp)

        PopupMenuItem(
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