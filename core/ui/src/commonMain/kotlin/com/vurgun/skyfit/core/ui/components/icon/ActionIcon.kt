package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActionIcon(
    res: DrawableResource,
    modifier: Modifier = Modifier.size(16.dp),
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Icon(
        painter = painterResource(res),
        contentDescription = contentDescription,
        modifier = modifier.clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
        tint = SkyFitColor.icon.default
    )
}