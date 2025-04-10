package com.vurgun.skyfit.ui.core.components.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun ErrorDialog(message: String?, onDismiss: () -> Unit) {
    if (!message.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Error",
                    style = SkyFitTypography.bodyMediumRegular
                )
            },
            text = {
                Text(
                    text = message,
                    color = SkyFitColor.text.criticalOnBgFill,
                    style = SkyFitTypography.bodyMediumSemibold
                )
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        )
    }
}
