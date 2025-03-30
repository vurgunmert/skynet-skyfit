package com.vurgun.skyfit.designsystem.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActionIcon(
    res: DrawableResource,
    modifier: Modifier = Modifier.size(16.dp),
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(res),
        contentDescription = null,
        modifier = modifier.clickable(onClick = onClick),
        tint = SkyFitColor.icon.default
    )
}