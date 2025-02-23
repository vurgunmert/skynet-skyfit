package com.vurgun.skyfit.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun requestCameraPermission(): Boolean {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    SideEffect {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    return cameraPermissionState.status.isGranted
}