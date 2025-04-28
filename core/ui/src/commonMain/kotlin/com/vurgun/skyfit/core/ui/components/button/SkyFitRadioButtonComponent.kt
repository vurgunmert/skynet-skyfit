package com.vurgun.skyfit.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun SkyFitRadioButtonComponent(
    text: String,
    selected: Boolean,
    onOptionSelected: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.Red)
    ) {
        RadioButton(
            selected = selected,
            onClick = { onOptionSelected(!selected) },
            colors = RadioButtonDefaults.colors(
                selectedColor = SkyFitColor.icon.default,
                unselectedColor = SkyFitColor.icon.default
            )
        )
        Text(text, style = SkyFitTypography.bodyMediumRegular)
    }
}
