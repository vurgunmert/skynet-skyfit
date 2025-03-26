package com.vurgun.skyfit.core.ui.components.error

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

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
