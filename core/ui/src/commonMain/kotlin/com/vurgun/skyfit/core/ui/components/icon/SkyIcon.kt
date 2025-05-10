package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

enum class SkyIconSize(val dp: Dp) {
    Tiny(12.dp),
    Small(16.dp),
    Normal(20.dp),
    Medium(24.dp),
    Large(32.dp),
    ExtraLarge(40.dp)
}

enum class SkyIconTint(val color: Color) {
    Default(SkyFitColor.icon.default),
    Inverse(SkyFitColor.icon.inverse),
    Disabled(SkyFitColor.icon.disabled),
    Critical(SkyFitColor.icon.critical)
}


@Composable
fun SkyIcon(
    res: DrawableResource,
    modifier: Modifier = Modifier,
    size: SkyIconSize = SkyIconSize.Normal,
    tint: SkyIconTint = SkyIconTint.Default,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {
    val iconModifier = modifier
        .size(size.dp)
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)

    Icon(
        painter = painterResource(res),
        contentDescription = contentDescription,
        tint = tint.color,
        modifier = iconModifier
    )
}
