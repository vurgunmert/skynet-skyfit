package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

private enum class MobileUserBodyAnalysisScreenStep {
    INFO,
    POSTURE_OPTIONS,
    POSTURE_FRONT,
    POSTURE_BACK,
    POSTURE_RIGHT,
    ANALYSING,
    POSTURE_RESULTS,
    ANALYSE_RESULTS,
    EXIT
}

@Composable
fun MobileUserBodyAnalysisScreen(navigator: Navigator) {

    var showGrid: Boolean = true
    var showCameraPreview: Boolean = false
    var step = MobileUserBodyAnalysisScreenStep.EXIT

    SkyFitScaffold {

        Box {

            when (step) {
                MobileUserBodyAnalysisScreenStep.INFO -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenInfoComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_OPTIONS -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenPostureOptionsComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_FRONT,
                MobileUserBodyAnalysisScreenStep.POSTURE_BACK,
                MobileUserBodyAnalysisScreenStep.POSTURE_RIGHT -> {
                    if (showGrid) {
                        MobileUserBodyAnalysisScreenPostureGridComponent()
                    }

                    if (showCameraPreview) {
                        MobileUserBodyAnalysisScreenCameraPreviewComponent()
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.ANALYSING -> {
                    MobileUserBodyAnalysisScreenPosturePhotoComponent()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenAnalysingToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenAnalysingProgressComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.POSTURE_RESULTS -> {
                    MobileUserBodyAnalysisScreenPosturePhotoComponent()
                    MobileUserBodyAnalysisScreenPosturePhotoAnalysisComponent()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenAnalysingToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.ANALYSE_RESULTS -> {
                    MobileUserBodyAnalysisScreenPosturePhotoComponent()
                    MobileUserBodyAnalysisScreenPosturePhotoAnalysisComponent()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserBodyAnalysisScreenAnalysingToolbarComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenResultsComponent()
                        Spacer(Modifier.weight(1f))
                        MobileUserBodyAnalysisScreenMediaActionsComponent()
                    }
                }

                MobileUserBodyAnalysisScreenStep.EXIT -> {
                    MobileUserBodyAnalysisScreenExitActionComponent()
                }
            }
        }
    }
}


@Composable
private fun MobileUserBodyAnalysisScreenToolbarComponent() {
    TodoBox("MobileUserBodyAnalysisScreenToolbarComponent", Modifier.size(430.dp, 64.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenInfoComponent() {
    TodoBox("MobileUserBodyAnalysisScreenInfoComponent", Modifier.size(406.dp, 528.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureOptionsComponent() {
    TodoBox("MobileUserBodyAnalysisScreenPostureOptionsComponent", Modifier.size(406.dp, 528.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenMediaActionsComponent() {
    TodoBox("MobileUserBodyAnalysisScreenMediaActionsComponent", Modifier.size(352.dp, 112.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenResultsComponent() {
    TodoBox("MobileUserBodyAnalysisScreenResultsComponent", Modifier.size(406.dp, 725.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenExitActionComponent() {
    TodoBox("MobileUserBodyAnalysisScreenExitActionComponent", Modifier.size(382.dp, 196.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenPostureGridComponent() {
    TodoBox("MobileUserBodyAnalysisScreenPostureGridComponent", Modifier.fillMaxSize().background(Color.Blue))
}

@Composable
private fun MobileUserBodyAnalysisScreenCameraPreviewComponent() {
    TodoBox("MobileUserBodyAnalysisScreenCameraPreviewComponent", Modifier.fillMaxSize().background(Color.Red))
}

@Composable
private fun MobileUserBodyAnalysisScreenPosturePhotoComponent() {
    TodoBox("MobileUserBodyAnalysisScreenPosturePhotoComponent", Modifier.size(420.dp, 723.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenAnalysingToolbarComponent() {
    TodoBox("MobileUserBodyAnalysisScreenAnalysingToolbarComponent", Modifier.size(430.dp, 48.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenAnalysingProgressComponent() {
    TodoBox("MobileUserBodyAnalysisScreenAnalysingProgressComponent", Modifier.size(324.dp, 100.dp))
}

@Composable
private fun MobileUserBodyAnalysisScreenPosturePhotoAnalysisComponent() {
    TodoBox("MobileUserBodyAnalysisScreenPosturePhotoAnalysisComponent", Modifier.size(47.dp, 689.dp))
}
