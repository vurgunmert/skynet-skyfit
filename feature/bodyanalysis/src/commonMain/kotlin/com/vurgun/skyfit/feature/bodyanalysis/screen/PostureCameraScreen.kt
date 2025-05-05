package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.enums.CameraLens
import com.kashif.cameraK.enums.Directory
import com.kashif.cameraK.enums.FlashMode
import com.kashif.cameraK.enums.ImageFormat
import com.kashif.cameraK.enums.TorchMode
import com.kashif.cameraK.permissions.Permissions
import com.kashif.cameraK.permissions.providePermissions
import com.kashif.cameraK.ui.CameraPreview
import com.kashif.imagesaverplugin.ImageSaverConfig
import com.kashif.imagesaverplugin.ImageSaverPlugin
import com.kashif.imagesaverplugin.rememberImageSaverPlugin

@Composable
fun PostureCameraScreen(
    viewModel: PostureAnalysisViewModel,
    isLoading: Boolean
) {
    val permissions: Permissions = providePermissions()

    Scaffold {
        val cameraPermissionState = remember { mutableStateOf(permissions.hasCameraPermission()) }
        val storagePermissionState = remember { mutableStateOf(permissions.hasStoragePermission()) }

        val cameraController = remember { mutableStateOf<CameraController?>(null) }
        val imageSaverPlugin = rememberImageSaverPlugin(
            config = ImageSaverConfig(
                isAutoSave = false,
                prefix = "SkyFit",
                directory = Directory.PICTURES,
                customFolderName = "Posture"
            )
        )

        PermissionsHandler(
            permissions = permissions,
            cameraPermissionState = cameraPermissionState,
            storagePermissionState = storagePermissionState
        )

        if (cameraPermissionState.value && storagePermissionState.value) {
            CameraContent(
                viewModel = viewModel,
                isLoading = isLoading,
                cameraController = cameraController,
                imageSaverPlugin = imageSaverPlugin,
            )
        }
    }
}

@Composable
private fun PermissionsHandler(
    permissions: Permissions,
    cameraPermissionState: MutableState<Boolean>,
    storagePermissionState: MutableState<Boolean>
) {
    if (!cameraPermissionState.value) {
        permissions.RequestCameraPermission(
            onGranted = { cameraPermissionState.value = true },
            onDenied = { println("Camera Permission Denied") }
        )
    }

    if (!storagePermissionState.value) {
        permissions.RequestStoragePermission(
            onGranted = { storagePermissionState.value = true },
            onDenied = { println("Storage Permission Denied") }
        )
    }
}

@Composable
private fun CameraContent(
    viewModel: PostureAnalysisViewModel,
    isLoading: Boolean,
    cameraController: MutableState<CameraController?>,
    imageSaverPlugin: ImageSaverPlugin,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            cameraConfiguration = {
                setCameraLens(CameraLens.BACK)
                setFlashMode(FlashMode.OFF)
                setImageFormat(ImageFormat.JPEG)
                setDirectory(Directory.PICTURES)
                setTorchMode(TorchMode.OFF)
                addPlugin(imageSaverPlugin)
            },
            onCameraControllerReady = {
                print("==> Camera Controller Ready")
                cameraController.value = it
            }
        )

        if (uiState.showGrid) PostureAnalysisGrid()
        if (uiState.showGuideOverlay) {
            uiState.currentPosture?.let {
                HumanGuideOverlay(it)
            }
        }

        cameraController.value?.let { controller ->
            CameraScreenControlOverlay(
                viewModel = viewModel,
                isLoading = isLoading,
                cameraController = controller,
                imageSaverPlugin = imageSaverPlugin,
            )
        }
    }
}

@Composable
private fun CameraScreenControlOverlay(
    viewModel: PostureAnalysisViewModel,
    isLoading: Boolean,
    cameraController: CameraController,
    imageSaverPlugin: ImageSaverPlugin,
) {

    Box(modifier = Modifier.fillMaxSize()) {

        if (!isLoading) {
            PostureAnalysisBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.systemBars),
                onClickRetake = { },
                onClickCapture = {
                    viewModel.handleImageCapture(
                        cameraController = cameraController,
                        imageSaverPlugin = imageSaverPlugin,
                    )
                },
                onImagesSelected = viewModel::handleImagePicked
            )
        }
    }
}