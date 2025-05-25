package com.vurgun.skyfit.core.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.close_action
import skyfit.core.ui.generated.resources.ic_close_circle
import skyfit.core.ui.generated.resources.no_not_yet_action
import skyfit.core.ui.generated.resources.ok_action

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
                    .widthIn(max = 430.dp)
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

@Composable
fun SkyFitDestructiveDialogComponent(
    showDialog: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Evet, İptal Et",
    dismissText: String = "Hayır, Geri Dön"
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close_circle),
                            contentDescription = "Close",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onDismiss() }
                        )
                    }

                    // Title
                    Text(
                        text = title,
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Message
                    Text(
                        text = message,
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons Row (Single Line)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Confirm Button (Destructive)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(50))
                                .border(1.dp, SkyFitColor.border.critical, RoundedCornerShape(50))
                                .clickable { onConfirm() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = confirmText,
                                color = SkyFitColor.text.criticalOnBgFill,
                                style = SkyFitTypography.bodyMediumSemibold
                            )
                        }

                        // Dismiss Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(50))
                                .background(SkyFitColor.specialty.buttonBgRest)
                                .clickable { onDismiss() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dismissText,
                                color = SkyFitColor.text.inverse,
                                style = SkyFitTypography.bodyMediumSemibold
                            )
                        }
                    }
                }
            }
        }
    }
}