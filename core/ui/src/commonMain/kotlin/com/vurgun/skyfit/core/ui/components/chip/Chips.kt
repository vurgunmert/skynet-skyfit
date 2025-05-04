package com.vurgun.skyfit.core.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun SecondaryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) {
        SkyFitColor.specialty.buttonBgActive
    } else {
        SkyFitColor.specialty.secondaryButtonRest
    }

    val borderColor = if (selected) {
        SkyFitColor.border.secondary
    } else {
        SkyFitColor.border.secondaryButton
    }

    val textColor = if (selected) {
        SkyFitColor.text.inverse
    } else {
        SkyFitColor.text.default
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(backgroundColor)
            .border(1.dp, borderColor, CircleShape)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumMedium.copy(color = textColor)
        )
    }
}
