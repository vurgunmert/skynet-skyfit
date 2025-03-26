package com.vurgun.skyfit.permission

import androidx.compose.runtime.Composable
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun requestCameraPermission(): Boolean {
    val cameraPermissionState = rememberPermissionState(Permission.Camera)
    return cameraPermissionState.status.isGranted
}