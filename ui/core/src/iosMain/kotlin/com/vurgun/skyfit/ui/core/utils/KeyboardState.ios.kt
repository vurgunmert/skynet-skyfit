package com.vurgun.skyfit.ui.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UIKeyboardFrameEndUserInfoKey
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun keyboardAsState(): State<KeyboardState> {
    val keyboardState = remember { mutableStateOf<KeyboardState>(KeyboardState.Closed()) }
    val density = LocalDensity.current

    DisposableEffect(Unit) {
        val willShow = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIKeyboardWillShowNotification,
            `object` = null,
            queue = null
        ) { notification ->
            val keyboardFrame = (notification?.userInfo?.getValue(UIKeyboardFrameEndUserInfoKey) as? CValue<CGRect>)?.useContents {
                this.size.height
            } ?: 0.0
            val keyboardHeightPx = keyboardFrame.toInt()
            val keyboardHeightDp = with(density) { keyboardHeightPx.toDp() }

            keyboardState.value = KeyboardState.Opened(px = keyboardHeightPx, dp = keyboardHeightDp)
        }

        val willHide = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIKeyboardWillHideNotification,
            `object` = null,
            queue = null
        ) {
            keyboardState.value = KeyboardState.Closed()
        }

        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(willShow)
            NSNotificationCenter.defaultCenter.removeObserver(willHide)
        }
    }

    return rememberUpdatedState(keyboardState.value)
}
