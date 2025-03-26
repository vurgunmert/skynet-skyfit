package com.vurgun.skyfit.core.ui.components.image

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
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.vurgun.skyfit.core.utils.ImagePickerPermissions
import kotlinx.coroutines.launch

@Composable
fun SkyFitPickImageWrapper(
    selectionMode: FilePickerSelectionMode = FilePickerSelectionMode.Single,
    onImagesSelected: (List<ByteArray>) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()

    val initialIsGranted = ImagePickerPermissions.isPermissionGranted()
    val initialShouldShowRationale = ImagePickerPermissions.shouldShowRationale()

    var isPermissionGranted by remember { mutableStateOf(initialIsGranted) }
    var showRationaleDialog by remember { mutableStateOf(false) }
    var triggerPermissionRequest by remember { mutableStateOf(false) }

    if (triggerPermissionRequest) {
        ImagePickerPermissions.requestPermission { granted ->
            isPermissionGranted = granted
            if (!granted) showRationaleDialog = true
        }
        triggerPermissionRequest = false
    }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = selectionMode,
        onResult = { files ->
            scope.launch {
                onImagesSelected(files.map { it.readByteArray(context) })
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