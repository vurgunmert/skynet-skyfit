package com.vurgun.skyfit.ui.core.styling

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Padding(
    val xxxSmall: Dp = 2.dp,   // For fine adjustments and tight spaces
    val xxSmall: Dp = 4.dp,    // Small elements and tight groupings
    val xSmall: Dp = 8.dp,     // Compact spacing
    val small: Dp = 12.dp,     // Regular small spacing
    val medium: Dp = 16.dp,    // Default spacing between components
    val large: Dp = 24.dp,     // Section spacing and prominent elements
    val xLarge: Dp = 32.dp,    // Major section separation
    val xxLarge: Dp = 48.dp,   // Screen-level spacing
    val xxxLarge: Dp = 64.dp,  // Large margins and major spacing
    val huge: Dp = 128.dp      // Extra-large layouts and special cases
)

val LocalPadding = compositionLocalOf { Padding() }