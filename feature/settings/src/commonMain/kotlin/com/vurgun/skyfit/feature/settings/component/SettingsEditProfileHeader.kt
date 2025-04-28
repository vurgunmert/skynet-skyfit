package com.vurgun.skyfit.feature.settings.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryMediumButton
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.save_action
import skyfit.core.ui.generated.resources.settings_account_label

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
            .height(52.dp)
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = "Back",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(16.dp)
            )
        }

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
