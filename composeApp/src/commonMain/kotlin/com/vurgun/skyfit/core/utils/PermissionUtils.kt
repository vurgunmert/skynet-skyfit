package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable

expect object ImagePickerPermissions {
    @Composable
    fun isPermissionGranted(): Boolean

    @Composable
    fun shouldShowRationale(): Boolean

    @Composable
    fun requestPermission(callback: (Boolean) -> Unit)
}
