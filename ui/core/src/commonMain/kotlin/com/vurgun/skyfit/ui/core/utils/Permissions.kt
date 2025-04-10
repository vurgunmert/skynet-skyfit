package com.vurgun.skyfit.ui.core.utils

import androidx.compose.runtime.Composable
import com.mohamedrejeb.calf.permissions.Permission

sealed class AppPermission(val key: Permission) {
    data object Camera : AppPermission(Permission.Camera)
    data object Gallery : AppPermission(Permission.Gallery)
    data object Files : AppPermission(Permission.ReadStorage)
}

interface ComposePermissionManager {
    @Composable
    fun isGranted(permission: AppPermission): Boolean
    @Composable
    fun requestPermission(permission: AppPermission, callback: (Boolean) -> Unit)
    @Composable
    fun shouldShowRationale(permission: AppPermission): Boolean
}

expect object PlatformPermissionManager : ComposePermissionManager
