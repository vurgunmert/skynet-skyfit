package com.vurgun.skyfit.designsystem.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vurgun.skyfit.core.ui.components.button.PrimaryDialogButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryDialogButton
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.close_action
import skyfit.composeapp.generated.resources.ic_close_circle
import skyfit.composeapp.generated.resources.no_not_yet_action
import skyfit.composeapp.generated.resources.ok_action

@Composable
fun DestructiveDialog(
    showDialog: Boolean,
    message: String,
    confirmText: String = stringResource(Res.string.ok_action),
    denyText: String = stringResource(Res.string.no_not_yet_action),
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onClickDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Icon
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close_circle),
                            contentDescription = stringResource(Res.string.close_action),
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp).clickable(onClick = onClickDismiss)
                        )
                    }

                    // Alert Message
                    Text(
                        text = message,
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SecondaryDialogButton(
                            text = confirmText,
                            modifier = Modifier,
                            onClick = onClickConfirm
                        )
                        Spacer(Modifier.width(24.dp))
                        PrimaryDialogButton(
                            text = denyText,
                            modifier = Modifier.weight(1f),
                            onClick = onClickDismiss
                        )
                    }
                }
            }
        }
    }
}