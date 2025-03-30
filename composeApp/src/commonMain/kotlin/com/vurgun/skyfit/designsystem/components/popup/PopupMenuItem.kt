package com.vurgun.skyfit.designsystem.components.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PopupMenuItem(
    text: String,
    iconRes: DrawableResource,
    onClick: () -> Unit,
    textColor: Color = SkyFitColor.text.default,
    iconTint: Color = SkyFitColor.icon.default
) {
    DropdownMenuItem(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, color = textColor, style = SkyFitTypography.bodyMediumRegular)
            Icon(
                painter = painterResource(iconRes),
                contentDescription = text,
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}