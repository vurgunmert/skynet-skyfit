package com.vurgun.skyfit.core.ui.components.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun FullScreenLoaderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default),
        contentAlignment = Alignment.Center
    ) {
        CircularLoader()
    }
}