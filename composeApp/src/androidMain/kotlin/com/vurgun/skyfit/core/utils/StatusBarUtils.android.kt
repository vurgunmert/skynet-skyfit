package com.vurgun.skyfit.core.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getStatusBarHeight(): Dp {
    val insets = WindowInsets.systemBars
    val density = LocalDensity.current
    return insets.getTop(density).dp
}
