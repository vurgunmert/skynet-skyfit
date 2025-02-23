package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.preat.peekaboo.image.picker.toImageBitmap
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.PeekabooCameraState
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import com.vurgun.skyfit.permission.requestCameraPermission
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.button.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import io.ktor.util.encodeBase64
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.body_analysis_back_figure
import skyfit.composeapp.generated.resources.body_analysis_figure_back
import skyfit.composeapp.generated.resources.body_analysis_figure_front
import skyfit.composeapp.generated.resources.body_analysis_figure_right
import skyfit.composeapp.generated.resources.body_analysis_front_figure
import skyfit.composeapp.generated.resources.body_analysis_grid
import skyfit.composeapp.generated.resources.body_analysis_right_figure
import skyfit.composeapp.generated.resources.body_analysis_scan_result_fake
import skyfit.composeapp.generated.resources.ic_camera
import skyfit.composeapp.generated.resources.ic_chevron_left
import skyfit.composeapp.generated.resources.ic_close_circle
import skyfit.composeapp.generated.resources.ic_flip_camera
import skyfit.composeapp.generated.resources.ic_image
import skyfit.composeapp.generated.resources.ic_info_circle
import skyfit.composeapp.generated.resources.ic_posture_fill
import skyfit.composeapp.generated.resources.ic_visibility_hide
import skyfit.composeapp.generated.resources.logo_skyfit


@Composable
fun MobileUserBodyAnalysisScreen(navigator: Navigator) {

    val viewModel = remember { MobileUserBodyAnalysisViewModel() }
    val uiState by viewModel.uiState.collectAsState()
    var capturedImage by remember { mutableStateOf<ByteArray?>(null) }

    val cameraState = rememberPeekabooCameraState(
        initialCameraMode = CameraMode.Back,
        onCapture = { capturedByteArray ->
            capturedImage = capturedByteArray
            viewModel.startScanning(capturedImage?.encodeBase64())
        }
    )

    var permissionGranted by remember { mutableStateOf(false) }
    if (uiState is MobileUserBodyAnalysisState.CameraPreview) {
        permissionGranted = requestCameraPermission()
    }

    SkyFitScaffold {
        Box(Modifier.fillMaxSize()) {

            /** 🔹 INFO SCREEN */
            if (uiState is MobileUserBodyAnalysisState.Info) {
                MobileUserBodyAnalysisScreenInfoComponent(
                    Modifier.align(Alignment.Center),
                    onClickDismiss = { viewModel.dismissInfoScreen() },
                    onClickExit = { viewModel.showCaptureExitScreen() },
                    onToggleGuide = {},
                    onToggleInfo = { viewModel.showInfoScreen() },
                )
            }

            /** 🔹 POSTURE OPTIONS */
            if (uiState is MobileUserBodyAnalysisState.PostureOptions) {
                MobileUserBodyAnalysisScreenPostureOptionsComponent(
                    onClickFront = { viewModel.selectPosture(PostureType.FRONT) },
                    onClickBack = { viewModel.selectPosture(PostureType.BACK) },
                    onClickRight = { viewModel.selectPosture(PostureType.RIGHT) },
                    onClickExit = { viewModel.showCaptureExitScreen() },
                    onToggleGuide = {},
                    onToggleInfo = { viewModel.showInfoScreen() },
                )
            }

            /** 🔹 CAMERA PREVIEW */
            if (uiState is MobileUserBodyAnalysisState.CameraPreview) {
                val state = uiState as MobileUserBodyAnalysisState.CameraPreview

                if (permissionGranted) {
                    MobileUserBodyAnalysisScreenCameraPreviewComponent(
                        cameraState = cameraState,
                        posture = state.postureType,
                        showGuide = state.showGuide,
                        onToggleGuide = { viewModel.toggleGuideVisibility(state.postureType, !state.showGuide) },
                        onCapture = { viewModel.startScanning(capturedImage?.encodeBase64()) },
                        onClickExit = { viewModel.showCaptureExitScreen() },
                        onToggleInfo = { viewModel.showInfoScreen() }
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Kamera erişimi reddedildi. Lütfen izin verin.",
                            style = SkyFitTypography.bodyLargeMedium,
                            color = Color.White
                        )
                    }
                }
            }

            /** 🔹 SCANNING PROGRESS */
            if (uiState is MobileUserBodyAnalysisState.Scanning) {
                MobileUserBodyAnalysisScreenScanningComponent(
                    capturedImage = capturedImage,
                    onComplete = { viewModel.showCaptureResults() }
                )
            }

            /** 🔹 POSTURE CAPTURE RESULTS */
            if (uiState is MobileUserBodyAnalysisState.CaptureResult) {
                MobileUserBodyAnalysisScreenCaptureResultComponent(
                    capturedImage = capturedImage,
                    onClickResult = { viewModel.showCaptureInsights() },
                    onClickExit = { viewModel.showCaptureExitScreen() },
                    onToggleGuide = {},
                    onToggleInfo = { viewModel.showInfoScreen() },
                )
            }

            /** 🔹 CAPTURE RESULT INSIGHT */
            if (uiState is MobileUserBodyAnalysisState.CaptureResultInsight) {
                MobileUserBodyAnalysisScreenCaptureResultInsightComponent(
                    capturedImage = capturedImage,
                    onClickDismiss = { viewModel.showCaptureResults() }
                )
            }

            /** 🔹 CAPTURE RESULT EXIT */
            if (uiState is MobileUserBodyAnalysisState.CaptureResultExit) {
                MobileUserBodyAnalysisScreenExitActionComponent(
                    showDialog = true,
                    onClickExit = { navigator.popBackStack() },
                    onClickDismiss = {
                        if (capturedImage != null) {
                            viewModel.showCaptureResults()
                        } else {
                            viewModel.showInfoScreen()
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenToolbarComponent(
    modifier: Modifier,
    onClickBack: () -> Unit,
    onToggleGuide: () -> Unit,
    onClickInfo: () -> Unit
) {
    Row(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.ic_chevron_left),
            modifier = Modifier.size(48.dp),
            onClick = onClickBack
        )
        Spacer(Modifier.weight(1f))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.ic_posture_fill),
            modifier = Modifier.size(48.dp),
            onClick = onToggleGuide
        )
        Spacer(Modifier.width(16.dp))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.ic_visibility_hide),
            modifier = Modifier.size(48.dp),
            onClick = onToggleGuide
        )
        Spacer(Modifier.width(16.dp))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.ic_info_circle),
            modifier = Modifier.size(48.dp),
            onClick = onClickInfo
        )
    }
}

@Composable
private fun PostureCaptureInfoCard(title: String, content: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF12323A),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "● $title",
                style = SkyFitTypography.bodySmallSemibold
            )
            Spacer(modifier = Modifier.height(8.dp))
            content.forEach {
                Text(text = "• $it", style = SkyFitTypography.bodySmall)
            }
        }
    }
}


@Composable
private fun PostureOptionItem(
    res: DrawableResource,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF12323A),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(res),
                contentDescription = text,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = SkyFitTypography.bodyMediumMedium
            )
        }
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenMediaActionsComponent(
    modifier: Modifier,
    cameraState: PeekabooCameraState,
    onClickFlipCamera: () -> Unit,
    onClickCapture: () -> Unit,
    onClickPickImage: () -> Unit
) {
    Row(
        modifier = modifier
            .background(SkyFitColor.background.surfaceSecondary, CircleShape)
            .padding(vertical = 24.dp, horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkyFitIconButton(painter = painterResource(Res.drawable.ic_flip_camera), onClick = {
            onClickFlipCamera()
        })
        Spacer(Modifier.width(48.dp))

        if (!cameraState.isCapturing) {
            SkyFitIconButton(painter = painterResource(Res.drawable.ic_camera), onClick = onClickCapture)
        } else {
            Box(
                Modifier.size(64.dp)
                    .background(SkyFitColor.specialty.buttonBgLoading, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = SkyFitColor.icon.disabled,
                    strokeWidth = 1.dp
                )
            }
        }

        Spacer(Modifier.width(48.dp))
        SkyFitIconButton(painter = painterResource(Res.drawable.ic_image), onClick = onClickPickImage)
    }
}

@Composable
fun MobileUserBodyAnalysisScreenCameraPreviewComponent(
    cameraState: PeekabooCameraState,
    posture: PostureType,
    showGuide: Boolean,
    onClickExit: () -> Unit,
    onToggleGuide: () -> Unit,
    onToggleInfo: () -> Unit,
    onCapture: () -> Unit
) {
    var showPreview by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera View
        if (showPreview) {
            PeekabooCamera(
                state = cameraState,
                modifier = Modifier.fillMaxSize(),
                permissionDeniedContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Kamera erişimi reddedildi",
                            style = SkyFitTypography.bodyLargeMedium,
                            color = Color.White
                        )
                    }
                }
            )
        }

        if (showGuide) {
            MobileUserBodyAnalysisScreenPostureGuideComponent(postureType = posture)
        }

        MobileUserBodyAnalysisScreenToolbarComponent(
            Modifier.align(Alignment.TopStart),
            onClickBack = onClickExit,
            onToggleGuide = onToggleGuide,
            onClickInfo = onToggleInfo
        )

        MobileUserBodyAnalysisScreenMediaActionsComponent(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter),
            cameraState = cameraState,
            onClickFlipCamera = { cameraState.toggleCamera() },
            onClickCapture = { cameraState.capture() },
            onClickPickImage = { showPreview = !showPreview }
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenAnalysingToolbarComponent(modifier: Modifier, onClickBack: () -> Unit) {
    Row(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(32.dp).clickable(onClick = onClickBack)
        )
    }
}

@Composable
fun MobileUserBodyAnalysisScreenCaptureResultComponent(
    capturedImage: ByteArray?,
    onClickResult: () -> Unit,
    onClickExit: () -> Unit,
    onToggleGuide: () -> Unit,
    onToggleInfo: () -> Unit,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (capturedImage != null) {
            val bitmap = capturedImage.toImageBitmap()
            Image(
                painter = BitmapPainter(bitmap),
                contentDescription = "Captured Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(Res.drawable.body_analysis_scan_result_fake),
                contentDescription = "Captured Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(onClick = onClickResult)
            )
        }

        MobileUserBodyAnalysisScreenToolbarComponent(
            Modifier.align(Alignment.TopStart),
            onClickBack = onClickExit,
            onToggleGuide = onToggleGuide,
            onClickInfo = onToggleInfo
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MobileUserBodyAnalysisScreenCaptureResultInsightComponent(
    capturedImage: ByteArray?,
    onClickDismiss: () -> Unit
) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (capturedImage != null) {
            val bitmap = capturedImage.toImageBitmap()
            Image(
                painter = BitmapPainter(bitmap),
                contentDescription = "Captured Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        FlowRow(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentBlur, RoundedCornerShape(20.dp))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 3
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Icon(
                    painterResource(Res.drawable.ic_close_circle),
                    contentDescription = "Close",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.clickable(onClick = onClickDismiss)
                )
            }

            PostureAnalysisEstimationCardComponent(title = "Tahmini Boy", value = "175 cm")
            PostureAnalysisEstimationCardComponent(title = "Tahmini Yaş", value = "27")
            PostureAnalysisEstimationCardComponent(title = "Tahmini Kilo", value = "60 kg")
            PostureAnalysisEstimationCardComponent(title = "Cinsiyet", value = "Kadın")
            PostureAnalysisEstimationCardComponent(title = "Vücut Tipi", value = "Mesomorph")
            PostureAnalysisEstimationCardComponent(title = "Vücut Şekli", value = "Pear")

            PostureAnalysisMuscleProgressResultComponent()

            PostureAnalysisMuscleAnalysisListComponent()

            PostureAnalysisMuscleAnalysisSuggestedExercisesComponent()
        }
    }

}

@Composable
private fun PostureAnalysisEstimationCardComponent(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = title,
                style = SkyFitTypography.bodyMediumSemibold,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = SkyFitTypography.bodySmall,
            color = SkyFitColor.text.secondary
        )
    }
}

@Composable
private fun PostureAnalysisMuscleProgressItemComponent(
    muscleName: String,
    progress: Float,
    emoji: String,
    feedback: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Muscle Name and Percentage
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = muscleName,
                style = SkyFitTypography.bodyMediumSemibold,
                color = Color.White
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = SkyFitTypography.bodyMediumSemibold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SkyFitColor.specialty.buttonBgRest
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Emoji Feedback
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = emoji,
                fontSize = 18.sp // Bigger emoji for visual emphasis
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = feedback,
                style = SkyFitTypography.bodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }
}

@Composable
fun PostureAnalysisMuscleProgressResultComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PostureAnalysisMuscleProgressItemComponent(
            muscleName = "Biceps",
            progress = 0.40f,
            emoji = "🙁",
            feedback = "Az gelişmiş"
        )

        PostureAnalysisMuscleProgressItemComponent(
            muscleName = "Karın & Core",
            progress = 0.80f,
            emoji = "😊",
            feedback = "Harika!"
        )

        PostureAnalysisMuscleProgressItemComponent(
            muscleName = "Quadriceps",
            progress = 0.50f,
            emoji = "🤔",
            feedback = "Daha iyi olabilir"
        )
    }
}

@Composable
fun PostureAnalysisMuscleAnalysisTextComponent(
    title: String,
    description: String,
    bulletPoints: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = title,
            style = SkyFitTypography.bodyMediumSemibold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = description,
            style = SkyFitTypography.bodySmall,
            color = SkyFitColor.text.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bullet Points
        bulletPoints.forEach { point ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "•", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = point,
                    style = SkyFitTypography.bodySmall,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun PostureAnalysisMuscleAnalysisListComponent() {
    PostureAnalysisMuscleAnalysisTextComponent(
        title = "Shoulders and Arms",
        description = "The comparison shows significant improvement in muscle definition and reduction in body fat after the fitness program. The overall physique appears more toned and fit, with better muscle separation and definition in the chest, abdomen, shoulders, and arms. The posture has also improved, contributing to a more athletic and confident appearance.",
        bulletPoints = listOf(
            "The shoulders and arms have muscle mass but lack distinct definition.",
            "Deltoid muscles are not prominently visible."
        )
    )
}

@Composable
fun PostureAnalysisMuscleAnalysisSuggestedExercisesComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Vücut Analizine Göre Önerilen Antrenmanlar",
            style = SkyFitTypography.heading5,
            color = SkyFitColor.text.default
        )

        ExerciseSuggestionCard(
            title = "Üst vücut antrenmanı",
            details = "3 set x 15 tekrar"
        )

        ExerciseSuggestionCard(
            title = "Üst vücut antrenmanı",
            details = "3 set x 15 tekrar"
        )
    }
}

@Composable
fun ExerciseSuggestionCard(title: String, details: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(20.dp))
            .padding(16.dp),
    ) {
        Column {
            Text(
                text = title,
                style = SkyFitTypography.bodyLarge,
                color = SkyFitColor.text.inverse
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = details,
                style = SkyFitTypography.bodySmallSemibold,
                color = SkyFitColor.text.inverse
            )
        }

        SkyFitButtonComponent(
            modifier = Modifier.align(Alignment.CenterEnd).wrapContentWidth(), text = "Kaydet",
            onClick = { },
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Disabled
        )
    }
}

@Composable
fun MobileUserBodyAnalysisScreenScanningComponent(
    capturedImage: ByteArray?,
    onComplete: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (capturedImage != null) {
            val bitmap = capturedImage.toImageBitmap()
            Image(
                painter = BitmapPainter(bitmap),
                contentDescription = "Captured Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        HoloScanningEffect()
    }
}

@Composable
private fun HoloScanningEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    val scanOffset by infiniteTransition.animateFloat(
        initialValue = -150f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .offset(y = scanOffset.dp) // Moves up and down
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Cyan.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    )
}


@Composable
private fun MobileUserBodyAnalysisScreenInfoComponent(
    modifier: Modifier,
    onClickDismiss: () -> Unit,
    onClickExit: () -> Unit,
    onToggleGuide: () -> Unit,
    onToggleInfo: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(horizontal = 12.dp).fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header with Close Button
            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), contentAlignment = Alignment.CenterEnd) {
                IconButton(onClick = onClickDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Info Sections
            PostureCaptureInfoCard(
                title = "Pozisyon ve Duruş",
                content = listOf(
                    "Ekran, tüm vücudu kapsayacak şekilde ayarlanmalıdır.",
                    "Bacaklar omuz genişliğinde açık, eller yanda doğal pozisyonda olmalı.",
                    "Kafa, düz bir şekilde ekrana bakmalı ve nefes tutulmamalıdır."
                )
            )

            PostureCaptureInfoCard(
                title = "Giyim ve Arka Plan",
                content = listOf(
                    "Vücudu saran giysiler tercih edilmeli; bol ve geniş giysilerden kaçınılmalıdır.",
                    "Omuz başları, dizler ve ayak bileği eklemleri görünür olmalıdır.",
                    "Tek renk bir zemin önünde durulmalıdır."
                )
            )

            PostureCaptureInfoCard(
                title = "Kamera ve Postür Tipi",
                content = listOf(
                    "Kamera bel hizasında ve tam karşıdan tutulmalıdır.",
                    "Lateral Postür: Vücut yan konumda, baş yukarıda ve karşıya bakmalıdır.",
                    "Posterior Postür: Sırt tamamen dönük, skapula kemikleri görünecek şekilde durulmalıdır."
                )
            )
        }

        MobileUserBodyAnalysisScreenToolbarComponent(
            Modifier.align(Alignment.TopStart),
            onClickBack = onClickExit,
            onToggleGuide = onToggleGuide,
            onClickInfo = onToggleInfo
        )
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenPostureOptionsComponent(
    onClickFront: () -> Unit,
    onClickBack: () -> Unit,
    onClickRight: () -> Unit,
    onClickExit: () -> Unit,
    onToggleGuide: () -> Unit,
    onToggleInfo: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.align(Alignment.Center)
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PostureOptionItem(
                res = Res.drawable.body_analysis_figure_front,
                text = "Ön Görünüm", onClick = onClickFront
            )
            PostureOptionItem(
                res = Res.drawable.body_analysis_figure_back,
                text = "Arka Görünüm", onClick = onClickBack
            )
            PostureOptionItem(
                res = Res.drawable.body_analysis_figure_right,
                text = "Sağ Görünüm", onClick = onClickRight
            )
        }

        MobileUserBodyAnalysisScreenToolbarComponent(
            Modifier.align(Alignment.TopStart),
            onClickBack = onClickExit,
            onToggleGuide = onToggleGuide,
            onClickInfo = onToggleInfo
        )
    }
}


@Composable
fun MobileUserBodyAnalysisScreenPostureGuideComponent(
    postureType: PostureType,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        // Grid Overlay
        Icon(
            painter = painterResource(Res.drawable.body_analysis_grid),
            contentDescription = "Grid",
            modifier = Modifier.align(Alignment.Center)
        )

        // Posture Figure based on type
        val posturePainter = when (postureType) {
            PostureType.FRONT -> painterResource(Res.drawable.body_analysis_front_figure)
            PostureType.BACK -> painterResource(Res.drawable.body_analysis_back_figure)
            PostureType.RIGHT -> painterResource(Res.drawable.body_analysis_right_figure)
        }

        Icon(
            painter = posturePainter,
            contentDescription = "${postureType.name} Figure",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenExitActionComponent(
    showDialog: Boolean,
    onClickExit: () -> Unit,
    onClickDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClickDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Icon
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Icon(
                            painterResource(Res.drawable.ic_close_circle),
                            contentDescription = "Close",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.clickable(onClick = onClickDismiss)
                        )
                    }

                    // Alert Message
                    Text(
                        text = "Vücut analizi sayfasından ayrılmak istediğine emin misin?",
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SkyFitButtonComponent(
                            text = "Tamam",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickExit,
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Medium,
                        )

                        SkyFitButtonComponent(
                            text = "Hayır, şimdi değil",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickDismiss,
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.MediumDialog
                        )
                    }
                }
            }
        }
    }
}