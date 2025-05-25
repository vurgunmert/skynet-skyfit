package com.vurgun.skyfit.core.ui.components.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader

@Composable
fun CompactTopbarWithAction(
    title: String,
    onClickBack: () -> Unit,
    actionLabel: String,
    onClickAction: () -> Unit
) {
    Box(Modifier.fillMaxWidth().padding(vertical = 12.dp), contentAlignment = Alignment.CenterEnd) {
        SkyFitScreenHeader(title, onClickBack = onClickBack)

        SkyButton(
            label = actionLabel,
            variant = SkyButtonVariant.Primary,
            size = SkyButtonSize.Micro,
            onClick = onClickAction,
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}