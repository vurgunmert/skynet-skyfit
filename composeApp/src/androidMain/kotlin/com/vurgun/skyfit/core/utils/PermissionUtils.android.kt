package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import com.mohamedrejeb.calf.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
actual object ImagePickerPermissions {

    @Composable
    actual fun isPermissionGranted(): Boolean {
        val galleryPermissionState = rememberPermissionState(Permission.Gallery)
        return galleryPermissionState.status.isGranted
    }

    @Composable
    actual fun shouldShowRationale(): Boolean {
        val galleryPermissionState = rememberPermissionState(Permission.Gallery)
        return galleryPermissionState.status.shouldShowRationale
    }

    @Composable
    actual fun requestPermission(callback: (Boolean) -> Unit) {
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
}