package com.vurgun.skyfit.core.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.resources.SkyFitTheme
import moe.tlaster.precompose.PreComposeApp

@Composable
fun SkyFitHostScreen(content: @Composable () -> Unit) {
    SkyFitTheme {
        PreComposeApp(content)
    }
}