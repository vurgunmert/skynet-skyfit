package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_chevron_left

enum class SkyIconSize(val dp: Dp) {
    Tiny(12.dp),
    Small(16.dp),
    Normal(20.dp),
    Medium(24.dp),
    Large(32.dp),
    Size36(36.dp),
    Size40(40.dp),
    Size56(56.dp)
}

enum class SkyIconTint(val color: Color) {
    Default(SkyFitColor.icon.default),
    Inverse(SkyFitColor.icon.inverse),
    Disabled(SkyFitColor.icon.disabled),
    Critical(SkyFitColor.icon.critical),
    Info(SkyFitColor.icon.info),
    Secondary(SkyFitColor.icon.secondary),
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
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
        .size(size.dp)

    Icon(
        painter = painterResource(res),
        contentDescription = contentDescription,
        tint = tint.color,
        modifier = iconModifier
    )
}
@Composable
fun SkyIconButton(
    res: DrawableResource,
    onClick: () -> Unit,
    size: SkyIconSize = SkyIconSize.Normal,
    tint: SkyIconTint = SkyIconTint.Default,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = contentDescription,
            tint = tint.color,
            modifier = Modifier.size(size.dp)
        )
    }
}

@Composable
fun SkyIcon(
    iconId: Int,
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
        painter = SkyFitAsset.getPainter(iconId),
        contentDescription = contentDescription,
        tint = tint.color,
        modifier = iconModifier
    )
}
