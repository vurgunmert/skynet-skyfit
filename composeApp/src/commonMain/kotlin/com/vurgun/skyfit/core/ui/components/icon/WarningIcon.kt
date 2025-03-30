package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun WarningIcon() {
    Icon(
        painter = painterResource(Res.drawable.logo_skyfit),
        contentDescription = "Warning",
        tint = SkyFitColor.icon.caution,
        modifier = Modifier.size(16.dp)
    )
}