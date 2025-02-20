package com.vurgun.skyfit.presentation.shared.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography

@Composable
fun SecondaryMediumText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = SkyFitTypography.bodyMediumRegular,
        color = SkyFitColor.text.secondary,
        modifier = modifier
    )
}

@Composable
fun SecondaryMediumUnderlinedText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = SkyFitTypography.bodyMediumUnderlined,
        color = SkyFitColor.text.secondary,
        modifier = modifier
    )
}