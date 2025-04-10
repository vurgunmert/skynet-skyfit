package com.vurgun.skyfit.ui.core.utils

import androidx.compose.runtime.Composable

actual object PlatformPermissionManager : ComposePermissionManager {

    @Composable
    override fun isGranted(permission: AppPermission): Boolean = true

    @Composable
    override fun requestPermission(permission: AppPermission, callback: (Boolean) -> Unit) {
        callback(true)
    }

    @Composable
    override fun shouldShowRationale(permission: AppPermission): Boolean = false
}