package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserExerciseInActionScreenStep {
    SESSION,
    BREAK,
    PROGRAM,
    TROPHY,
    COMPLETE
}

@Composable
fun MobileUserExerciseInActionScreen(navigator: Navigator) {

    val showToolbar: Boolean = true
    var activePage by remember { mutableStateOf(MobileUserExerciseInActionScreenStep.SESSION) }

    Box(Modifier.fillMaxSize()) {
        MobileUserExerciseInActionGraphicComponent()

        if (showToolbar) {
            MobileUserExerciseInActionScreenToolbarComponent(
                onClickBack = {  },
                exerciseName = "Ip atlama",
                exerciseRepeat = "15x4",
                remainingTime = "00:12",
                isBreak = false
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {

            when (activePage) {
                MobileUserExerciseInActionScreenStep.SESSION -> {
                    Spacer(Modifier.weight(1f))
                    MobileUserExerciseInActionScreenActionsComponent()
                }

                MobileUserExerciseInActionScreenStep.BREAK -> {
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenBreakComponent()
                }

                MobileUserExerciseInActionScreenStep.PROGRAM -> {
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenProgramComponent()
                }

                MobileUserExerciseInActionScreenStep.TROPHY -> {
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenTrophyComponent(
                        onClickTrophy = {
                            navigator.jumpAndTakeover(SkyFitNavigationRoute.Dashboard, SkyFitNavigationRoute.UserTrophies)
                        },
                        onClickNext = {
                            activePage = MobileUserExerciseInActionScreenStep.COMPLETE
                        }
                    )
                }

                MobileUserExerciseInActionScreenStep.COMPLETE -> {
                    navigator.jumpAndTakeover(
                        SkyFitNavigationRoute.UserExerciseInAction,
                        SkyFitNavigationRoute.UserExerciseInActionComplete
                    )
                }
            }

        }
    }
}

@Composable
private fun MobileUserExerciseInActionGraphicComponent() {
    TodoBox("MobileUserExerciseInActionGraphicComponent", Modifier.fillMaxSize().background(Color.LightGray))
}


@Composable
private fun MobileUserExerciseInActionScreenToolbarBackComponent(onClick: () -> Unit) {
    // Back Button
    Icon(
        painter = painterResource(Res.drawable.logo_skyfit),
        contentDescription = "Back",
        tint = SkyFitColor.text.default,
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick)
    )
}


@Composable
private fun MobileUserExerciseInActionScreenToolbarExerciseComponent(name: String, repeat: String) {
    // Exercise Info
    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(SkyFitColor.background.surfaceOpalTransparent, shape = CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Ip Atlama", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.width(4.dp))
        Text("15×4", style = SkyFitTypography.heading5)
    }
}


@Composable
private fun MobileUserExerciseInActionScreenToolbarTimerComponent(time: String, percent: Int) {
    // Music Icon & Timer Row
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Timer Display
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(SkyFitColor.background.surfaceOpalTransparent, shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(time, style = SkyFitTypography.heading5)
        }

        // Progress Indicators
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (index < 2) SkyFitColor.specialty.buttonBgRest else SkyFitColor.text.default,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
private fun MobileUserExerciseInActionScreenToolbarComponent(
    onClickBack: () -> Unit,
    exerciseName: String,
    exerciseRepeat: String,
    remainingTime: String,
    isBreak: Boolean,
) {
    var isMusicPlaying by remember { mutableStateOf(true) }
    var showMusicControls by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MobileUserExerciseInActionScreenToolbarBackComponent(onClick = onClickBack)
                MobileUserExerciseInActionScreenToolbarExerciseComponent(name = exerciseName, repeat = exerciseRepeat)

                // Music icon button (get position)
                MusicProgressIconButton(
                    modifier = Modifier,
                    isPlaying = isMusicPlaying,
                    onClick = { showMusicControls = !showMusicControls }
                )

                MobileUserExerciseInActionScreenToolbarTimerComponent(remainingTime, 30)
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Popup appears below the button
                AnimatedVisibility(
                    visible = showMusicControls,
                    enter = fadeIn(animationSpec = tween(300, easing = LinearOutSlowInEasing)) +
                            slideInVertically(initialOffsetY = { -it }), // Start above and move down
                    exit = fadeOut(animationSpec = tween(100, easing = FastOutLinearInEasing)) +
                            slideOutVertically(targetOffsetY = { it }) // Move down when closing
                ) {
                    MusicControlsPopup(
                        isPlaying = isMusicPlaying,
                        onPause = { isMusicPlaying = false; showMusicControls = false },
                        onNext = { /* Handle next track */ showMusicControls = false },
                        onPrevious = { /* Handle previous track */ showMusicControls = false }
                    )
                }
            }
        }
    }
}


@Composable
private fun MobileUserExerciseInActionScreenBreakComponent() {
    TodoBox("MobileUserExerciseInActionScreenBreakComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenProgramComponent() {
    TodoBox("MobileUserExerciseInActionScreenProgramComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenTrophyComponent(
    onClickTrophy: () -> Unit,
    onClickNext: () -> Unit
) {
    Box(
        Modifier.padding(horizontal = 24.dp, vertical = 18.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceHover, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier.fillMaxSize().padding(horizontal = 76.dp, vertical = 112.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(148.dp, 203.dp)
                    .background(SkyFitColor.background.surfaceDisabled)
            )
            Spacer(Modifier.height(36.dp))
            Text(
                text = "3 Günlük Seri Tamamlandı!",
                textAlign = TextAlign.Center,
                style = SkyFitTypography.heading3,
                modifier = Modifier.width(230.dp)
            )
            Spacer(Modifier.height(36.dp))
            SkyFitButtonComponent(
                Modifier.fillMaxWidth(), text = "Ödüller",
                onClick = onClickTrophy,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Large,
                initialState = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.logo_skyfit)
            )
            Spacer(Modifier.height(16.dp))
            SkyFitButtonComponent(
                Modifier.fillMaxWidth(), text = "Ileri",
                onClick = onClickNext,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                initialState = ButtonState.Rest
            )
        }
    }
}

@Composable
private fun MobileUserExerciseInActionScreenActionsComponent() {
    TodoBox("MobileUserExerciseInActionScreenActionsComponent", Modifier.size(382.dp, 95.dp))
}

@Composable
private fun MusicProgressIconButton(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = true,
    onClick: () -> Unit
) {
    val progressDarkColor: Color = SkyFitColor.background.surfaceSecondary
    val progressLightColor: Color = SkyFitColor.border.secondaryButton

    // Infinite rotation animation that does not reset
    val rotationAngle by remember { mutableStateOf(Animatable(0f)) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            rotationAngle.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            rotationAngle.stop()
        }
    }

    Box(
        modifier = modifier
            .size(48.dp)
            .background(SkyFitColor.background.surfaceSemiTransparent, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationAngle.value) // Rotation applied here
        ) {
            val strokeWidth = 2.dp.toPx()
            val cornerRadius = 16.dp.toPx()
            val padding = strokeWidth / 2
            val rectSize = Size(size.width - strokeWidth, size.height - strokeWidth)

            // Background rounded rectangle (keeps everything inside)
            drawRoundRect(
                color = SkyFitColor.background.surfaceSemiTransparent,
                size = rectSize,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                topLeft = Offset(padding, padding)
            )

            // Rotating Progress Layer (Dark and Light)
            drawRoundRect(
                color = progressDarkColor,
                topLeft = Offset(padding, padding),
                size = rectSize,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(strokeWidth)
            )

            // Light Progress (Moves with Dark)
            drawRoundRect(
                color = progressLightColor,
                topLeft = Offset(padding, padding),
                size = rectSize,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(strokeWidth)
            )
        }

        // Center Icon
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Music Icon",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun MusicControlsPopup(
    isPlaying: Boolean,
    onPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(80.dp)
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Now Playing", style = SkyFitTypography.bodySmallSemibold)
            Text(text = "Ed Sheeran", style = SkyFitTypography.bodySmall)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onPrevious) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Previous", tint = Color.White)
                }
                IconButton(onClick = onPause) {
                    Icon(if (isPlaying) Icons.Filled.Lock else Icons.Filled.PlayArrow, contentDescription = "Play/Pause", tint = Color.White)
                }
                IconButton(onClick = onNext) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Next", tint = Color.White)
                }
            }
        }
    }
}