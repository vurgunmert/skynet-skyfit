package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState

@Composable
actual fun keyboardAsState(): State<KeyboardState> {
    return rememberUpdatedState(KeyboardState.Closed())
}