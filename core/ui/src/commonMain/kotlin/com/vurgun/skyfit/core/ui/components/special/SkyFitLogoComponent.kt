package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

@Composable
fun SkyFitLogoComponent() {
    Image(
        painterResource(Res.drawable.ic_app_logo),
        null,
        modifier = Modifier.size(108.dp, 118.dp)
    )
}

@Composable
fun SkyFitAutoSizeLogo(maxWidth: Dp) {
    Image(
        painterResource(Res.drawable.ic_app_logo),
        null,
        modifier = Modifier.size(maxWidth * 0.6f)
    )
}