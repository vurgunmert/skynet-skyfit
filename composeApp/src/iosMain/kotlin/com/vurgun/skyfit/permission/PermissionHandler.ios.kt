package com.vurgun.skyfit.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@Composable
actual fun requestCameraPermission(): Boolean {
    var permissionGranted by remember { mutableStateOf(false) }
    var permissionChecked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

        when (status) {
            AVAuthorizationStatusAuthorized -> {
                permissionGranted = true
            }

            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                permissionGranted = false
            }

            AVAuthorizationStatusNotDetermined -> {
                dispatch_async(dispatch_get_main_queue()) {
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        permissionGranted = granted
                        permissionChecked = true
                    }
                }
            }
        }
    }

    return permissionGranted
}
