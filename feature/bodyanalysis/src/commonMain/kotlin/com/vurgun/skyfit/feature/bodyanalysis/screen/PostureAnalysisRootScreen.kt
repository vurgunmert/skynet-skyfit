package com.vurgun.skyfit.feature.bodyanalysis.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryFlatIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileFillScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.body_analysis_grid
import skyfit.core.ui.generated.resources.ic_camera
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_flip_camera
import skyfit.core.ui.generated.resources.ic_image
import skyfit.core.ui.generated.resources.ic_info_circle
import skyfit.core.ui.generated.resources.ic_posture_fill
import skyfit.core.ui.generated.resources.ic_visibility_hide
import skyfit.core.ui.generated.resources.ic_visibility_show
import skyfit.core.ui.generated.resources.posture_guide_view_front

@Composable
fun PostureAnalysisRootScreen(
    onExit: () -> Unit
) {
    val viewModel: PostureAnalysisViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    SkyFitMobileFillScaffold {
        Box(Modifier.fillMaxSize()) {

            when (uiState.mode) {
                PostureAnalysisUIState.Mode.Info -> {
                    PostureAnalysisInfoScreen(
                        onClickDismiss = viewModel::toggleInfo
                    )
                }

                PostureAnalysisUIState.Mode.Options -> {
                    PostureAnalysisOptionsScreen(viewModel)
                }

                PostureAnalysisUIState.Mode.Camera -> {
                    PostureCameraScreen(
                        viewModel = viewModel,
                        isLoading = uiState.isCaptureLoading
                    )
                }

                PostureAnalysisUIState.Mode.Scanning -> {
                    PostureAnalysisScanningScreen(imageBitmap = uiState.lastCapturedImage)
                }

                PostureAnalysisUIState.Mode.Result -> {
                    showResultDialog = true
//                    PostureAnalysisResultScreen(viewModel)
                }
            }

            PostureAnalysisTopBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .fillMaxWidth(),
                uiState = uiState,
                onClickBack = onExit, //{ showExitDialog = true },
                onToggleGuide = viewModel::toggleGuideOverlay,
                onToggleGrid = viewModel::toggleGrid,
                onClickInfo = viewModel::toggleInfo
            )
        }

        if (showResultDialog) {
            PostureAnalysisResultDialog(
                viewModel = viewModel,
                onDismiss = {
                    showResultDialog = false
                    viewModel.toggleInfo()
                }
            )
        }
    }
}


@Composable
private fun PostureAnalysisTopBar(
    modifier: Modifier,
    uiState: PostureAnalysisUIState,
    onClickBack: () -> Unit,
    onToggleGuide: () -> Unit,
    onToggleGrid: () -> Unit,
    onClickInfo: () -> Unit
) {
    Row(
        modifier
            .wrapContentWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {

        PrimaryIconButton(
            painter = painterResource(Res.drawable.ic_chevron_left),
            modifier = Modifier.size(48.dp),
            onClick = onClickBack
        )

        Spacer(Modifier.weight(1f))

        if (uiState.showGuideOverlay) {
            PrimaryIconButton(
                painter = painterResource(Res.drawable.ic_posture_fill),
                modifier = Modifier.size(48.dp),
                onClick = onToggleGuide
            )
        } else {
            SecondaryIconButton(
                painter = painterResource(Res.drawable.ic_posture_fill),
                modifier = Modifier.size(48.dp),
                onClick = onToggleGuide
            )
        }

        Spacer(Modifier.width(16.dp))

        if (uiState.showGrid) {
            PrimaryIconButton(
                painter = painterResource(Res.drawable.ic_visibility_show),
                modifier = Modifier.size(48.dp),
                onClick = onToggleGrid
            )
        } else {
            SecondaryIconButton(
                painter = painterResource(Res.drawable.ic_visibility_hide),
                modifier = Modifier.size(48.dp),
                onClick = onToggleGrid
            )
        }

        Spacer(Modifier.width(16.dp))

        if (uiState.mode == PostureAnalysisUIState.Mode.Info) {
            PrimaryIconButton(
                painter = painterResource(Res.drawable.ic_info_circle),
                modifier = Modifier.size(48.dp),
                onClick = onClickInfo
            )
        } else {
            SecondaryIconButton(
                painter = painterResource(Res.drawable.ic_info_circle),
                modifier = Modifier.size(48.dp),
                onClick = onClickInfo
            )
        }

    }
}

@Composable
internal fun PostureAnalysisBottomBar(
    modifier: Modifier,
    onClickRetake: () -> Unit,
    onClickCapture: () -> Unit,
    onImagesSelected: (ByteArray, ImageBitmap) -> Unit
) {
    Row(
        modifier = modifier
            .background(SkyFitColor.background.surfaceSecondary, CircleShape)
            .padding(vertical = 24.dp, horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SecondaryIconButton(painter = painterResource(Res.drawable.ic_flip_camera), onClick = onClickRetake)

        Spacer(Modifier.width(48.dp))

        PrimaryIconButton(painter = painterResource(Res.drawable.ic_camera), onClick = onClickCapture)

        Spacer(Modifier.width(48.dp))

        SkyFitPickImageWrapper(onImagesSelected = onImagesSelected, content = {
            SecondaryFlatIconButton(painter = painterResource(Res.drawable.ic_image))
        })
    }
}

@Composable
fun PostureAnalysisGrid() {
    Icon(
        painter = painterResource(Res.drawable.body_analysis_grid),
        contentDescription = "Grid",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun HumanGuideOverlay() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp), // keep spacing from edges
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        // Assuming your human guide is about 2.9:1 (height to width)
        val guideAspectRatio = 2.9f

        // We'll take 80% of width and calculate height from aspect ratio
        val guideWidth = maxWidth * 0.8f
        val guideHeight = guideWidth * guideAspectRatio

        Image(
            painter = painterResource(Res.drawable.posture_guide_view_front),
            contentDescription = null,
            modifier = Modifier
                .width(guideWidth)
                .heightIn(min = guideHeight, max = maxHeight) // keep it inside the screen
        )
    }
}
