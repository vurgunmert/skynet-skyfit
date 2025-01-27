package com.vurgun.skyfit.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

@Composable
actual fun keyboardAsState(): State<KeyboardState> {
    return mutableStateOf(KeyboardState.Closed)
}