package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize { COMPACT, MEDIUM, EXPANDED }

object WindowSizeHelper {
    fun fromWidth(width: Dp): WindowSize = when {
        width < 600.dp -> WindowSize.COMPACT
        width < 840.dp -> WindowSize.MEDIUM
        else           -> WindowSize.EXPANDED
    }
}


val LocalWindowSize = staticCompositionLocalOf { WindowSize.COMPACT }