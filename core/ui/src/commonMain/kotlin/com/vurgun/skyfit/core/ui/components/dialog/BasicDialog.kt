package com.vurgun.skyfit.core.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.core.ui.components.button.PrimaryDialogButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryDialogButton
import com.vurgun.skyfit.core.ui.components.icon.CloseIconRow
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ok_action

class BasicDialogState {
    var title by mutableStateOf<String?>(null)
    var message by mutableStateOf<String?>(null)
    var confirmText by mutableStateOf<String?>(null)
    var dismissText by mutableStateOf<String?>(null)
    var onConfirm: (() -> Unit)? = null

    fun show(
        title: String? = null,
        message: String,
        confirmText: String? = null,
        dismissText: String? = null,
        onConfirm: (() -> Unit)? = null
    ) {
        this.title = title
        this.message = message
        this.confirmText = confirmText
        this.dismissText = dismissText
        this.onConfirm = onConfirm
    }

    fun dismiss() {
        title = null
        message = null
        confirmText = null
        dismissText = null
        onConfirm = null
    }

    val isVisible: Boolean
        get() = !message.isNullOrEmpty()
}

@Composable
fun rememberBasicDialogState(): BasicDialogState = remember { BasicDialogState() }

@Composable
fun BasicDialog(
    state: BasicDialogState,
    onDismiss: () -> Unit = { state.dismiss() }
) {
    if (!state.isVisible) return

    val confirmText = state.confirmText ?: stringResource(Res.string.ok_action)
    val dismissText = state.dismissText ?: stringResource(Res.string.ok_action)

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CloseIconRow(onClick = onDismiss, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(4.dp))

            state.title?.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = it,
                    style = SkyFitTypography.bodyLargeMedium,
                    color = SkyFitColor.text.default,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = state.message.orEmpty(),
                style = SkyFitTypography.bodyLargeMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                SecondaryDialogButton(
                    text = dismissText,
                    onClick = onDismiss
                )
                PrimaryDialogButton(
                    text = confirmText,
                    onClick = {
                        state.onConfirm?.invoke()
                        onDismiss()
                    }
                )
            }
        }
    }
}
