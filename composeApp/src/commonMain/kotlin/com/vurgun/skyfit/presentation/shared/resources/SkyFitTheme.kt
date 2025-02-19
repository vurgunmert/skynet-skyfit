package com.vurgun.skyfit.presentation.shared.resources

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SkyFitTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        typography = SkyFitTypography.typography,
        colors = MaterialTheme.colors.copy(
            primary = SkyFitColor.specialty.buttonBgRest,
            primaryVariant = SkyFitColor.specialty.buttonBgHover,
            secondary = SkyFitColor.background.surfaceSecondary,
            secondaryVariant = SkyFitColor.background.surfaceTertiary,
            background = SkyFitColor.background.default,
            surface = SkyFitColor.background.default,
            error = SkyFitColor.text.critical,
            onPrimary = SkyFitColor.text.default,
            onSecondary = SkyFitColor.text.secondary,
            onBackground = SkyFitColor.text.inverseSecondary,
            onSurface = SkyFitColor.text.default,
            onError = SkyFitColor.text.criticalOnBgFill
        ),
        content = content
    )
}