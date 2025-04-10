package com.vurgun.skyfit.ui.core.components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import com.vurgun.skyfit.ui.core.utils.AppPermission
import com.vurgun.skyfit.ui.core.utils.PlatformPermissionManager
import kotlinx.coroutines.launch

sealed class ImagePickerType(val mode: FilePickerSelectionMode) {
    data object Single : ImagePickerType(FilePickerSelectionMode.Single)
    data object Multi : ImagePickerType(FilePickerSelectionMode.Multiple)
}

@Composable
fun SkyFitPickImageWrapper(
    selectionType: ImagePickerType = ImagePickerType.Single,
    onImagesSelected: (ByteArray, ImageBitmap) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()

    val initialIsGranted = PlatformPermissionManager.isGranted(AppPermission.Gallery)
    val initialShouldShowRationale = PlatformPermissionManager.shouldShowRationale(AppPermission.Gallery)

    var isPermissionGranted by remember { mutableStateOf(initialIsGranted) }
    var showRationaleDialog by remember { mutableStateOf(false) }
    var triggerPermissionRequest by remember { mutableStateOf(false) }

    if (triggerPermissionRequest) {
        PlatformPermissionManager.requestPermission(AppPermission.Gallery) { granted ->
            isPermissionGranted = granted
            if (!granted) showRationaleDialog = true
        }
        triggerPermissionRequest = false
    }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = selectionType.mode,
        onResult = { files ->
            scope.launch {
                val bitmapArray: ByteArray = files
                    .flatMap { it.readByteArray(context).toList() }
                    .toByteArray()
                try {
                    onImagesSelected(bitmapArray, bitmapArray.toImageBitmap())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    )

    val onClick = {
        when {
            isPermissionGranted -> pickerLauncher.launch()
            initialShouldShowRationale -> showRationaleDialog = true
            else -> triggerPermissionRequest = true
        }
    }

    Box(modifier = Modifier.clickable { onClick() }) {
        content()
    }

    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { Text("Permission Required") },
            text = { Text("This app needs access to your gallery to select images.") },
            confirmButton = {
                Button(onClick = {
                    showRationaleDialog = false
                    triggerPermissionRequest = true
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = { showRationaleDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}