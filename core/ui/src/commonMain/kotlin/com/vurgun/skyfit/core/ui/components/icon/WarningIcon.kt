package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
fun WarningIcon() {
    Icon(
        painter = painterResource(Res.drawable.logo_skyfit),
        contentDescription = "Warning",
        tint = SkyFitColor.icon.caution,
        modifier = Modifier.size(16.dp)
    )
}