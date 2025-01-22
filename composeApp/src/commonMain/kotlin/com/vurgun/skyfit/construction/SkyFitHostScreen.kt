package com.vurgun.skyfit.construction

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTheme
import moe.tlaster.precompose.PreComposeApp

@Composable
fun SkyFitHostScreen(content: @Composable () -> Unit) {
    SkyFitTheme {
        PreComposeApp(content)
    }
}