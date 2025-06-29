package com.vurgun.skyfit.health.posture.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
import com.vurgun.skyfit.core.data.utility.PlatformType
import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureTypeDTO
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryFlatIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.health.posture.PostureAnalysisAction
import com.vurgun.skyfit.health.posture.PostureAnalysisContentState
import com.vurgun.skyfit.health.posture.PostureAnalysisViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.body_analysis_grid
import fiwe.core.ui.generated.resources.ic_camera
import fiwe.core.ui.generated.resources.ic_flip_camera
import fiwe.core.ui.generated.resources.ic_image
import fiwe.core.ui.generated.resources.posture_guide_view_back
import fiwe.core.ui.generated.resources.posture_guide_view_front
import fiwe.core.ui.generated.resources.posture_guide_view_left
import fiwe.core.ui.generated.resources.posture_guide_view_right
import fiwe.core.ui.generated.resources.posture_view_back
import fiwe.core.ui.generated.resources.posture_view_front
import fiwe.core.ui.generated.resources.posture_view_left
import fiwe.core.ui.generated.resources.posture_view_right

@Composable
internal fun DesktopCameraPreviewContent(
    content: PostureAnalysisContentState.CameraPreview,
    viewModel: PostureAnalysisViewModel,
) {
    val permissions: Permissions = providePermissions()
    val headerState by viewModel.headerState.collectAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (headerState.gridGuideEnabled) {
                    PostureAnalysisGrid()
                }

                if (headerState.bodyGuideEnabled) {
                    HumanGuideOverlay(content.postureType)
                }
            }

            PostureAnalysisBottomBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars),
                onClickRetake = {
                    viewModel.onAction(PostureAnalysisAction.RetakeOption)
                },
                onClickCapture = { },
                onImagesSelected = { bytes, bitmap ->
                    viewModel.onAction(
                        PostureAnalysisAction.CaptureFromGallery(
                            byteArray = bytes,
                            bitmap = bitmap
                        )
                    )
                }
            )

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
internal fun CameraPreviewContent(
    content: PostureAnalysisContentState.CameraPreview,
    viewModel: PostureAnalysisViewModel,
) {
    val permissions: Permissions = providePermissions()
    val headerState by viewModel.headerState.collectAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default
    ) {
        val cameraPermissionState = remember { mutableStateOf(permissions.hasCameraPermission()) }
        val storagePermissionState = remember { mutableStateOf(permissions.hasStoragePermission()) }

        val cameraController = remember { mutableStateOf<CameraController?>(null) }
        val imageSaverPlugin = rememberImageSaver()

        PermissionsHandler(
            permissions = permissions,
            cameraPermissionState = cameraPermissionState,
            storagePermissionState = storagePermissionState
        )

        if (cameraPermissionState.value && storagePermissionState.value) {

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

                if (headerState.gridGuideEnabled) {
                    PostureAnalysisGrid()
                }

                if (headerState.bodyGuideEnabled) {
                    HumanGuideOverlay(content.postureType)
                }

                cameraController.value?.let { controller ->
                    CameraScreenControlOverlay(
                        postureType = content.postureType,
                        isAlreadyCaptured = content.isExist,
                        onAction = viewModel::onAction,
                        cameraController = controller,
                        imageSaverPlugin = imageSaverPlugin,
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraScreenControlOverlay(
    postureType: PostureTypeDTO,
    isAlreadyCaptured: Boolean,
    onAction: (PostureAnalysisAction) -> Unit,
    cameraController: CameraController,
    imageSaverPlugin: ImageSaverPlugin,
) {
    val postureText = when (postureType) {
        PostureTypeDTO.Front -> stringResource(Res.string.posture_view_front)
        PostureTypeDTO.Back -> stringResource(Res.string.posture_view_back)
        PostureTypeDTO.Left -> stringResource(Res.string.posture_view_left)
        PostureTypeDTO.Right -> stringResource(Res.string.posture_view_right)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        RectangleChip(text = postureText)

        PostureAnalysisBottomBar(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars),
            onClickRetake = {
                onAction(PostureAnalysisAction.RetakeOption)
            },
            onClickCapture = {
                onAction(
                    PostureAnalysisAction.CaptureFromCamera(
                        cameraController = cameraController,
                        imageSaverPlugin = imageSaverPlugin,
                    )
                )
            },
            onImagesSelected = { bytes, bitmap ->
                onAction(
                    PostureAnalysisAction.CaptureFromGallery(
                        byteArray = bytes,
                        bitmap = bitmap
                    )
                )
            }
        )
    }
}


@Composable
private fun rememberImageSaver(): ImageSaverPlugin {
    return rememberImageSaverPlugin(
        config = ImageSaverConfig(
            isAutoSave = false,
            prefix = "FIWE",
            directory = Directory.PICTURES,
            customFolderName = "Posture"
        )
    )
}


@Composable
private fun PostureAnalysisGrid() {
    Image(
        painter = painterResource(Res.drawable.body_analysis_grid),
        contentDescription = "Grid",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun HumanGuideOverlay(postureType: PostureTypeDTO) {
    val res = when (postureType) {
        PostureTypeDTO.Front -> Res.drawable.posture_guide_view_front
        PostureTypeDTO.Back -> Res.drawable.posture_guide_view_back
        PostureTypeDTO.Left -> Res.drawable.posture_guide_view_right
        PostureTypeDTO.Right -> Res.drawable.posture_guide_view_left
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Boundaries
        val maxImageHeight = 649.dp
        val maxImageWidth = 222.dp
        val aspectRatio = maxImageWidth / maxImageHeight

        val availableHeight = maxHeight * 0.6f
        val scaledHeight = minOf(availableHeight, maxImageHeight)
        val scaledWidth = scaledHeight * aspectRatio

        Image(
            painter = painterResource(res),
            contentDescription = null,
            modifier = Modifier
                .height(scaledHeight)
                .width(scaledWidth)
        )
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
private fun PostureAnalysisBottomBar(
    modifier: Modifier,
    onClickRetake: () -> Unit,
    onClickCapture: () -> Unit,
    onImagesSelected: (ByteArray, ImageBitmap) -> Unit
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(SkyFitColor.background.surfaceSecondary)
            .wrapContentWidth()
            .padding(vertical = 24.dp, horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SecondaryIconButton(painter = painterResource(Res.drawable.ic_flip_camera), onClick = onClickRetake)

        Spacer(Modifier.width(48.dp))

        PrimaryIconButton(
            painter = painterResource(Res.drawable.ic_camera),
            onClick = onClickCapture,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.width(48.dp))

        SkyFitPickImageWrapper(onImagesSelected = onImagesSelected, content = {
            SecondaryFlatIconButton(painter = painterResource(Res.drawable.ic_image))
        })
    }
}