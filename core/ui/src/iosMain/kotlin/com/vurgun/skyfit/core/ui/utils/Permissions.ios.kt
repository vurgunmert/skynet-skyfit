package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import com.mohamedrejeb.calf.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
actual object PlatformPermissionManager : ComposePermissionManager {

    @Composable
    override fun isGranted(permission: AppPermission): Boolean {
        val galleryPermissionState = rememberPermissionState(permission.key)
        return galleryPermissionState.status.isGranted
    }

    @Composable
    override fun requestPermission(permission: AppPermission, callback: (Boolean) -> Unit) {
        val galleryPermissionState = rememberPermissionState(Permission.Gallery)
        galleryPermissionState.launchPermissionRequest()

        LaunchedEffect(galleryPermissionState.status) {
            if (galleryPermissionState.status.isGranted) {
                callback(true)
            } else {
                callback(false)
            }
        }
        galleryPermissionState.launchPermissionRequest()
    }

    @Composable
    override fun shouldShowRationale(permission: AppPermission): Boolean {
        val galleryPermissionState = rememberPermissionState(Permission.Gallery)
        return galleryPermissionState.status.shouldShowRationale
    }
}
