package com.vurgun.skyfit.core.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyLargeMediumText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.error_generic_message
import fiwe.core.ui.generated.resources.error_generic_title
import fiwe.core.ui.generated.resources.ok_action

@Composable
fun ErrorScreen(
    title: String? = null,
    message: String? = null,
    confirmText: String = stringResource(Res.string.ok_action),
    onConfirm: () -> Unit
) {
    SkyFitScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                BodyLargeMediumText(
                    text = title ?: stringResource(Res.string.error_generic_title)
                )

                BodyMediumRegularText(
                    text = message ?: stringResource(Res.string.error_generic_message)
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryLargeButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = confirmText,
                    onClick = onConfirm
                )
            }
        }
    }
}
