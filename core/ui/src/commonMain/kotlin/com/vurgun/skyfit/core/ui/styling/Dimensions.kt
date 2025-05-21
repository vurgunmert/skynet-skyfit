package com.vurgun.skyfit.core.ui.styling

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val viewTiny: Dp = 40.dp,
    val viewSmall: Dp = 48.dp,
    val viewNormal: Dp = 56.dp,
    val viewBig: Dp = 64.dp,
    val viewLarge: Dp = 72.dp,

    val iconTiny: Dp = 12.dp,
    val iconSmall: Dp = 16.dp,
    val iconNormal: Dp = 20.dp,
    val iconMedium: Dp = 24.dp,
    val iconBig: Dp = 28.dp,
    val iconLarge: Dp = 32.dp,
    val iconXLarge: Dp = 40.dp,
    val iconXXLarge: Dp = 64.dp,
    val iconXXXLarge: Dp = 100.dp,

    val mobileMaxWidth: Dp = 430.dp,
    val mobileMinWidth: Dp = 390.dp,

    val mobileMinWidthPx: Int = 320,
    val mobileMinHeightPx: Int = 480,

    val desktopMinWidthPx: Int = 1440,
    val desktopMinHeightPx: Int = 900
)

val LocalDimensions = compositionLocalOf { Dimensions() }
