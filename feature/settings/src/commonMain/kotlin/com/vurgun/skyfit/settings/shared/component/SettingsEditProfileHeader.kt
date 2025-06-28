package com.vurgun.skyfit.feature.persona.settings.shared.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryMediumButton
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.save_action
import fiwe.core.ui.generated.resources.settings_account_label

@Composable
fun SettingsEditProfileHeader(
    showSave: Boolean = false,
    onClickSave: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        BackIcon(onClickBack)

        Text(
            text = stringResource(Res.string.settings_account_label),
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )

        if (showSave) {
            PrimaryMediumButton(
                text = stringResource(Res.string.save_action),
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onClickSave
            )
        }
    }
}
