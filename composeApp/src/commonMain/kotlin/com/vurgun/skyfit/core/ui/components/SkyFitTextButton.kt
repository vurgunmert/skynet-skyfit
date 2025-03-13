package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun SkyFitTextButton(text: String, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(SkyFitColor.background.surfaceSecondary)
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumRegular
        )
    }
}

@Composable
fun SkyFitSelectableTextButton(text: String, selected: Boolean, onSelect: (Boolean) -> Unit) {
    Box(
        Modifier.fillMaxWidth()
            .clip(CircleShape)
            .background(SkyFitColor.background.surfaceSecondary)
            .clickable(onClick = { onSelect(!selected) })
            .border(
                if (selected) 2.dp else 0.dp,
                color = SkyFitColor.border.secondaryButton,
                shape = CircleShape
            )
            .padding(16.dp)
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumRegular
        )
    }
}