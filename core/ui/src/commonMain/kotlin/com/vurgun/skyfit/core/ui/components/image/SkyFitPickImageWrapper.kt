package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.dialog_image_too_large_text
import fiwe.core.ui.generated.resources.dialog_image_too_large_title
import fiwe.core.ui.generated.resources.ok_action

sealed class ImagePickerType(val mode: FilePickerSelectionMode) {
    data object Single : ImagePickerType(FilePickerSelectionMode.Single)
    data object Multi : ImagePickerType(FilePickerSelectionMode.Multiple)
}

//@Composable
//fun SkyFitPickImageWrapper(
//    selectionType: ImagePickerType = ImagePickerType.Single,
//    onImagesSelected: (ByteArray, ImageBitmap) -> Unit,
//    content: @Composable () -> Unit
//) {
//    val context = LocalPlatformContext.current
//    val scope = rememberCoroutineScope()
//
//    val initialIsGranted = PlatformPermissionManager.isGranted(AppPermission.Gallery)
//    val initialShouldShowRationale = PlatformPermissionManager.shouldShowRationale(AppPermission.Gallery)
//
//    var isPermissionGranted by remember { mutableStateOf(initialIsGranted) }
//    var showRationaleDialog by remember { mutableStateOf(false) }
//    var triggerPermissionRequest by remember { mutableStateOf(false) }
//
//    if (triggerPermissionRequest) {
//        PlatformPermissionManager.requestPermission(AppPermission.Gallery) { granted ->
//            isPermissionGranted = granted
//            if (!granted) showRationaleDialog = true
//        }
//        triggerPermissionRequest = false
//    }
//
//    val pickerLauncher = rememberFilePickerLauncher(
//        type = FilePickerFileType.Image,
//        selectionMode = selectionType.mode,
//        onResult = { files ->
//            scope.launch {
//                val bitmapArray: ByteArray = files
//                    .flatMap { it.readByteArray(context).toList() }
//                    .toByteArray()
//                try {
//                    onImagesSelected(bitmapArray, bitmapArray.toImageBitmap())
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    )
//
//    val onClick = {
//        when {
//            isPermissionGranted -> pickerLauncher.launch()
//            initialShouldShowRationale -> showRationaleDialog = true
//            else -> triggerPermissionRequest = true
//        }
//    }
//
//    Box(modifier = Modifier.clickable { onClick() }) {
//        content()
//    }
//
//    if (showRationaleDialog) {
//        AlertDialog(
//            onDismissRequest = { showRationaleDialog = false },
//            title = { Text("Permission Required") },
//            text = { Text("This app needs access to your gallery to select images.") },
//            confirmButton = {
//                Button(onClick = {
//                    showRationaleDialog = false
//                    triggerPermissionRequest = true
//                }) {
//                    Text("Allow")
//                }
//            },
//            dismissButton = {
//                Button(onClick = { showRationaleDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}

@Composable
fun SkyFitPickImageWrapper(
    selectionType: ImagePickerType = ImagePickerType.Single,
    onImagesSelected: (ByteArray, ImageBitmap) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalPlatformContext.current
    var selectedFile by remember { mutableStateOf<KmpFile?>(null) }
    var showImageTooLargeDialog by remember { mutableStateOf(false) }

    // Maybe Android 26 permissions

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = selectionType.mode,
        onResult = { files ->
            selectedFile = files.firstOrNull()
        }
    )

    Box(modifier = Modifier.clickable(onClick = pickerLauncher::launch)) {
        content()
    }

    // Process selected image
    LaunchedEffect(selectedFile) {
        selectedFile?.let { file ->
            try {
                val bytes = file.readByteArray(context)

                if (bytes.size > 10 * 1024 * 1024) {
                    showImageTooLargeDialog = true
                    return@let
                }

                val bitmap = bytes.toImageBitmap()
                onImagesSelected(bytes, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                selectedFile = null
            }
        }
    }

    // Dialog: Image too large
    if (showImageTooLargeDialog) {
        AlertDialog(
            onDismissRequest = { showImageTooLargeDialog = false },
            title = {
                SkyText(
                    text = stringResource(Res.string.dialog_image_too_large_title),
                    styleType = TextStyleType.BodyLargeSemibold
                )
            },
            text = {
                SkyText(
                    text = stringResource(Res.string.dialog_image_too_large_text),
                    styleType = TextStyleType.BodyMediumRegular
                )
            },
            confirmButton = {
                SkyButton(
                    label = stringResource(Res.string.ok_action),
                    size = SkyButtonSize.Medium,
                    onClick = {
                        showImageTooLargeDialog = false
                    }
                )
            }
        )
    }
}