package com.vurgun.skyfit.core.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.core.ui.components.button.PrimaryDialogButton
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.error_generic_message
import skyfit.core.ui.generated.resources.error_generic_title
import skyfit.core.ui.generated.resources.ok_action

@Composable
fun ErrorDialog(
    title: String? = null,
    message: String?,
    onDismiss: () -> Unit
) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = title ?: stringResource(Res.string.error_generic_title),
                    style = SkyFitTypography.bodyLargeMedium,
                    color = SkyFitColor.text.criticalOnBgFill,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message ?: stringResource(Res.string.error_generic_message),
                    style = SkyFitTypography.bodyLargeMedium,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                ) {
                    PrimaryDialogButton(
                        text = stringResource(Res.string.ok_action),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}
