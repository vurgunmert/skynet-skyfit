package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable

actual object ImagePickerPermissions {
    @Composable
    actual fun isPermissionGranted(): Boolean = true

    @Composable
    actual fun shouldShowRationale(): Boolean = false

    @Composable
    actual fun requestPermission(callback: (Boolean) -> Unit) {
        callback(true)
    }

}