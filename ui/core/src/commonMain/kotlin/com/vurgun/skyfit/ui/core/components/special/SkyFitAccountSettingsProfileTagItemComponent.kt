package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_close

@Composable
fun SkyFitAccountSettingsProfileTagItemComponent(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    showClose: Boolean = true,
    onClick: () -> Unit
) {
    val textColor = if (enabled) SkyFitColor.text.default else SkyFitColor.text.disabled

    Row(
        modifier
            .background(SkyFitColor.specialty.secondaryButtonRest, CircleShape)
            .border(1.dp, SkyFitColor.border.secondaryButton, CircleShape)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 16.dp)
    ) {
        Text(text = value, modifier = Modifier, style = SkyFitTypography.bodyMediumMedium)

        if (showClose) {
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_close),
                contentDescription = "Close",
                modifier = Modifier.size(16.dp),
                tint = textColor
            )
        }
    }
}