package com.vurgun.skyfit.ui.core.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

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

@Composable
fun BodySmallRegularText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodySmall,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}


@Composable
fun BodySmallSemiboldText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodySmallSemibold,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}

@Composable
fun BodyMediumSemiboldText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodyMediumSemibold,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}
@Composable
fun BodyMediumRegularText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodyMediumRegular,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}
@Composable
fun BodyLargeMediumText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodyLargeMedium,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}

@Composable
fun BodyLargeSemiboldText(text: String, modifier: Modifier = Modifier, color: Color? = null) {
    Text(
        text = text,
        style = SkyFitTypography.bodyLargeSemibold,
        color = color ?: SkyFitColor.text.default,
        modifier = modifier
    )
}