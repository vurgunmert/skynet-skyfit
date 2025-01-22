package com.vurgun.skyfit.presentation.shared.resources

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SkyFitTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        typography = SkyFitTypography.typography,
        content = content
    )
}