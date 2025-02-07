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
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.PeekabooCameraState
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.body_analysis_back_figure
import skyfit.composeapp.generated.resources.body_analysis_front_figure
import skyfit.composeapp.generated.resources.body_analysis_grid
import skyfit.composeapp.generated.resources.body_analysis_right_figure
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserBodyAnalysisScreenStep {
    INFO,
    POSTURE_OPTIONS,
    POSTURE_FRONT,
    POSTURE_BACK,
    POSTURE_RIGHT,
    ANALYSING,
    POSTURE_RESULTS,
    ANALYSE_RESULTS
}

@Composable
fun MobileUserBodyAnalysisScreen(navigator: Navigator) {

    var showToolbar by remember { mutableStateOf(true) }
    var showBottomBar by remember { mutableStateOf(true) }
    var showGrid by remember { mutableStateOf(false) }
    var showFrontCamera by remember { mutableStateOf(false) }
    var showCameraPreview by remember { mutableStateOf(false) }
    var step by remember { mutableStateOf(MobileUserBodyAnalysisScreenStep.POSTURE_FRONT) }
    var showCancelDialog by remember { mutableStateOf(false) }

    val cameraState = rememberPeekabooCameraState(
        initialCameraMode = CameraMode.Back,
        onCapture = { /* Handle captured images */ }
    )


    SkyFitScaffold {
        Box(Modifier.fillMaxSize()) {

            when (step) {
                MobileUserBodyAnalysisScreenStep.INFO -> {
                    MobileUserBodyAnalysisScreenInfoComponent(Modifier.align(Alignment.Center),
                        onClickDismiss = {})
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_OPTIONS -> {
                    MobileUserBodyAnalysisScreenPostureOptionsComponent(
                        modifier = Modifier.align(Alignment.Center),
                        onClickFront = { step = MobileUserBodyAnalysisScreenStep.POSTURE_FRONT },
                        onClickBack = { step = MobileUserBodyAnalysisScreenStep.POSTURE_BACK },
                        onClickRight = { step = MobileUserBodyAnalysisScreenStep.POSTURE_RIGHT }
                    )
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_FRONT -> {
                    MobileUserBodyAnalysisScreenPostureFrontGridComponent()
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_BACK -> {
                    MobileUserBodyAnalysisScreenPostureBackGridComponent()
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_RIGHT -> {
                    MobileUserBodyAnalysisScreenPostureRightGridComponent()
                }

                MobileUserBodyAnalysisScreenStep.ANALYSING -> {
                    MobileUserBodyAnalysisScreenPostureCapturedPhotoComponent()
                    MobileUserBodyAnalysisScreenAnalysingProgressComponent()
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_RESULTS -> {
                    MobileUserBodyAnalysisScreenPostureCapturedPhotoComponent()
                    MobileUserBodyAnalysisScreenPostureAnalysisResultsComponent()
                }

                MobileUserBodyAnalysisScreenStep.ANALYSE_RESULTS -> {
                    MobileUserBodyAnalysisScreenPostureCapturedPhotoComponent()
                    MobileUserBodyAnalysisScreenPostureAnalysisResultsComponent()
                }
            }

            if (showGrid) {
                MobileUserBodyAnalysisScreenPostureGridComponent()
            }

            if (showCameraPreview) {
                MobileUserBodyAnalysisScreenCameraPreviewComponent(cameraState)
            }

            if (showToolbar) {
                MobileUserBodyAnalysisScreenToolbarComponent(Modifier.align(Alignment.TopStart))
            }

            if (showBottomBar) {
                MobileUserBodyAnalysisScreenMediaActionsComponent(Modifier.align(Alignment.BottomCenter),
                    onClickFlipCamera = {
                        if (!showCameraPreview) {
                            showCameraPreview = true
                        } else {
                            cameraState.toggleCamera()
                        }
                    },
                    onClickCapture = {
                        if (!showCameraPreview && cameraState.isCameraReady) {
                            showCameraPreview = true
                        } else {
                            cameraState.capture()
                        }
                    },
                    onClickPickImage = {
                        showCameraPreview = false
                    })
            }
        }


        MobileUserBodyAnalysisScreenExitActionComponent(
            showDialog = showCancelDialog,
            onClickDismiss = { showCancelDialog = false },
            onClickExit = { navigator.popBackStack() }
        )
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenToolbarComponent(modifier: Modifier) {
    Row(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = {})
        )
        Spacer(Modifier.weight(1f))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = {})
        )
        Spacer(Modifier.width(16.dp))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = {})
        )
        Spacer(Modifier.width(16.dp))
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = {})
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenInfoComponent(modifier: Modifier, onClickDismiss: () -> Unit) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp).fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Close Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vücut Analizi Bilgilendirme",
                style = SkyFitTypography.heading3
            )
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
private fun MobileUserBodyAnalysisScreenPostureOptionsComponent(
    modifier: Modifier,
    onClickFront: () -> Unit,
    onClickBack: () -> Unit,
    onClickRight: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 36.dp)
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PostureOptionItem(
            text = "Ön Görünüm", onClick = onClickFront
        )
        PostureOptionItem(
            text = "Arka Görünüm", onClick = onClickBack
        )
        PostureOptionItem(
            text = "Sağ Görünüm", onClick = onClickRight
        )
    }
}

@Composable
private fun PostureOptionItem(text: String, onClick: () -> Unit) {
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
                painter = painterResource(Res.drawable.logo_skyfit),
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
    onClickFlipCamera: () -> Unit,
    onClickCapture: () -> Unit,
    onClickPickImage: () -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(SkyFitColor.background.surfaceSecondary, CircleShape)
            .padding(vertical = 24.dp, horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit), onClick = {
            onClickFlipCamera()
        })
        Spacer(Modifier.width(48.dp))
        SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit), onClick = onClickCapture)
        Spacer(Modifier.width(48.dp))
        SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit), onClick = onClickPickImage)
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
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logo_skyfit),
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
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )

                        SkyFitButtonComponent(
                            text = "Hayır, şimdi değil",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickDismiss,
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureGridComponent() {
    Box(Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(Res.drawable.body_analysis_grid),
            contentDescription = "Front Figure",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureFrontGridComponent() {
    Box(Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(Res.drawable.body_analysis_front_figure),
            contentDescription = "Front Figure",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureBackGridComponent() {
    Box(Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(Res.drawable.body_analysis_back_figure),
            contentDescription = "Front Figure",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureRightGridComponent() {
    Box(Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(Res.drawable.body_analysis_right_figure),
            contentDescription = "Front Figure",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MobileUserBodyAnalysisScreenCameraPreviewComponent(cameraState: PeekabooCameraState) {
    PeekabooCamera(
        state = cameraState,
        modifier = Modifier.fillMaxSize(),
        permissionDeniedContent = {
            // TODO: Custom permission UI content for permission denied scenario
        },
    )
}

@Composable
private fun BoxScope.MobileUserBodyAnalysisScreenPostureCapturedPhotoComponent() {
    AsyncImage(
        model = "https://media.istockphoto.com/id/540226606/photo/beginning-of-yoga-workout.jpg?s=612x612&w=0&k=20&c=v1KvMMc1sThJ5zv5ETig2_jvG6ya1T1ijqsWg_lLz9w=",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
    )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MobileUserBodyAnalysisScreenPostureAnalysisResultsComponent() {
    FlowRow(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(SkyFitColor.background.surfaceSecondary)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PostureAnalysisEstimationCardComponent(title = "Tahmini Boy", value = "175 cm")
        PostureAnalysisEstimationCardComponent(title = "Tahmini Yaş", value = "27")
        PostureAnalysisEstimationCardComponent(title = "Tahmini Kilo", value = "60 kg")
        PostureAnalysisEstimationCardComponent(title = "Cinsiyet", value = "Kadın")
        PostureAnalysisEstimationCardComponent(title = "Vücut Tipi", value = "Mesomorph")
        PostureAnalysisEstimationCardComponent(title = "Vücut Şekli", value = "Pear")

        PostureAnalysisMuscleProgressListComponent()

        PostureAnalysisMuscleAnalysisListComponent()

        PostureAnalysisMuscleAnalysisSuggestedExercisesComponent()
    }
}

@Composable
fun PostureAnalysisEstimationCardComponent(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .wrapContentHeight()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
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
fun PostureAnalysisMuscleProgressComponent(
    muscleName: String,
    progress: Float,
    emoji: String,
    feedback: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
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
fun PostureAnalysisMuscleProgressListComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PostureAnalysisMuscleProgressComponent(
            muscleName = "Biceps",
            progress = 0.40f,
            emoji = "🙁",
            feedback = "Az gelişmiş"
        )

        PostureAnalysisMuscleProgressComponent(
            muscleName = "Karın & Core",
            progress = 0.80f,
            emoji = "😊",
            feedback = "Harika!"
        )

        PostureAnalysisMuscleProgressComponent(
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PostureAnalysisMuscleAnalysisTextComponent(
            title = "Shoulders and Arms",
            description = "The comparison shows significant improvement in muscle definition and reduction in body fat after the fitness program. The overall physique appears more toned and fit, with better muscle separation and definition in the chest, abdomen, shoulders, and arms. The posture has also improved, contributing to a more athletic and confident appearance.",
            bulletPoints = listOf(
                "The shoulders and arms have muscle mass but lack distinct definition.",
                "Deltoid muscles are not prominently visible."
            )
        )
    }
}

@Composable
fun PostureAnalysisMuscleAnalysisSuggestedExercisesComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Section Title
        Text(
            text = "Vücut Analizine Göre Önerilen Antrenmanlar",
            style = SkyFitTypography.bodyMediumSemibold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Suggested Exercise Cards
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
            .background(SkyFitColor.background.surfaceSecondaryHover, RoundedCornerShape(12.dp))
            .padding(vertical = 6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = SkyFitTypography.bodyMediumSemibold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = details,
                    style = SkyFitTypography.bodySmall,
                    color = Color.DarkGray
                )
            }

            // Save Button
            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(), text = "Kaydet",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                state = ButtonState.Disabled
            )
        }
    }
}

@Composable
fun MobileUserBodyAnalysisScreenAnalysingProgressComponent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Scanning Effect
        HoloScanningEffect()
    }
}

@Composable
fun HoloScanningEffect() {
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

