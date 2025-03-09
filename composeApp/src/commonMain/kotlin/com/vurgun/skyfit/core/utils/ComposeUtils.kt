package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class KeyboardState(val heightPx: Int, val heightDp: Dp) {
    data class Opened(private val px: Int, private val dp: Dp) : KeyboardState(px, dp)
    data class Closed(private val px: Int = 0, private val dp: Dp = 0.dp) : KeyboardState(px, dp)
}

@Composable
expect fun keyboardAsState(): State<KeyboardState>

