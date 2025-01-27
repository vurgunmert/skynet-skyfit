package com.vurgun.skyfit.utils

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
actual fun keyboardAsState(): State<KeyboardState> {
    val view = LocalView.current
    val density = LocalDensity.current
    var keyboardState by remember { mutableStateOf<KeyboardState>(KeyboardState.Closed()) }

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnPreDrawListener {
            val insets = ViewCompat.getRootWindowInsets(view)
            val isKeyboardVisible = insets?.isVisible(WindowInsetsCompat.Type.ime()) == true
            val keyboardHeightPx = insets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0

            val keyboardHeightDp = with(density) { keyboardHeightPx.toDp() }
            keyboardState = if (isKeyboardVisible) {
                KeyboardState.Opened(px = keyboardHeightPx, dp = keyboardHeightDp)
            } else {
                KeyboardState.Closed()
            }
            true
        }
        view.viewTreeObserver.addOnPreDrawListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }

    return rememberUpdatedState(keyboardState)
}
