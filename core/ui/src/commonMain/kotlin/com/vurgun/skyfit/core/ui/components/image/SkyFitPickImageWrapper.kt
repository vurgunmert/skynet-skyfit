package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.utils.AppPermission
import com.vurgun.skyfit.core.ui.utils.PlatformPermissionManager
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.allow_action
import skyfit.core.ui.generated.resources.cancel_action
import skyfit.core.ui.generated.resources.dialog_image_too_large_text
import skyfit.core.ui.generated.resources.dialog_image_too_large_title
import skyfit.core.ui.generated.resources.dialog_permission_text
import skyfit.core.ui.generated.resources.dialog_permission_title
import skyfit.core.ui.generated.resources.ok_action

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
    val scope = rememberCoroutineScope()

    var selectedFile by remember { mutableStateOf<KmpFile?>(null) }
    var showImageTooLargeDialog by remember { mutableStateOf(false) }

    val initialPermGrant = PlatformPermissionManager.isGranted(AppPermission.Gallery)
    val initialPermRationale = PlatformPermissionManager.shouldShowRationale(AppPermission.Gallery)

    var isPermissionGranted by remember { mutableStateOf(initialPermGrant) }
    var showRationaleDialog by remember { mutableStateOf(initialPermRationale) }
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
            selectedFile = files.firstOrNull()
        }
    )

    val onClick = {
        when {
            isPermissionGranted -> pickerLauncher.launch()
            showRationaleDialog -> showRationaleDialog = true
            else -> triggerPermissionRequest = true
        }
    }

    Box(modifier = Modifier.clickable(onClick = onClick)) {
        content()
    }

    // Process selected file
    LaunchedEffect(selectedFile) {
        selectedFile?.let { file ->
            try {
                val bytes = file.readByteArray(context)

                // Check file size: 10MB limit (10 * 1024 * 1024)
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

    // Dialog: Permission
    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = {
                SkyText(
                    text = stringResource(Res.string.dialog_permission_title),
                    styleType = TextStyleType.BodyLargeSemibold
                )
            },
            text = {
                SkyText(
                    text = stringResource(Res.string.dialog_permission_text),
                    styleType = TextStyleType.BodyMediumRegular
                )
            },
            confirmButton = {
                SkyButton(
                    label = stringResource(Res.string.allow_action),
                    size = SkyButtonSize.Medium,
                    onClick = {
                        showRationaleDialog = false
                        triggerPermissionRequest = true
                    }
                )
            },
            dismissButton = {
                SkyButton(
                    label = stringResource(Res.string.cancel_action),
                    variant = SkyButtonVariant.Secondary,
                    size = SkyButtonSize.Medium,
                    onClick = {
                        showRationaleDialog = false
                    }
                )
            }
        )
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
