package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography

@Composable
fun SkyFitRadioButtonComponent(
    text: String,
    selected: Boolean,
    onOptionSelected: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = { onOptionSelected(!selected) },
            colors = RadioButtonDefaults.colors(
                selectedColor = SkyFitColor.specialty.buttonBgRest,
                unselectedColor = SkyFitColor.icon.default
            )
        )
        Text(text, style = SkyFitTypography.bodyMediumRegular)
    }
}