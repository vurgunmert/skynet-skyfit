package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vurgun.skyfit.data.bodyanalysis.model.BackPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.FrontPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.LeftPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.PostureFinding
import com.vurgun.skyfit.data.bodyanalysis.model.PostureType
import com.vurgun.skyfit.data.bodyanalysis.model.RightPostureResponse
import com.vurgun.skyfit.ui.core.components.button.SecondaryMicroButton
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_close_circle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PostureAnalysisResultDialog(
    viewModel: PostureAnalysisViewModel,
    onDismiss: () -> Unit
) {

    val results = viewModel.uiState.value.postureStates
    var selectedType: PostureType by remember { mutableStateOf(PostureType.Front) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(16.dp))
                .background(SkyFitColor.background.surfaceSecondary)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Close Button
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close_circle),
                        contentDescription = "Close",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SecondaryMicroButton("Ön Görünüm", onClick = { selectedType = PostureType.Front })
                SecondaryMicroButton("Arka Görünüm", onClick = { selectedType = PostureType.Back })
                SecondaryMicroButton("Sol Görünüm", onClick = { selectedType = PostureType.Left })
                SecondaryMicroButton("Sag Görünüm", onClick = { selectedType = PostureType.Right })
            }
            Spacer(Modifier.height(24.dp))
            results.firstOrNull { it.type == selectedType }?.let { posture ->
                posture.bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "PosturePhoto",
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .heightIn(min = 450.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                posture.result?.let { result ->
                    when (result) {
                        is PostureAnalysisResult.Front -> {
                            PostureResultSection(
                                title = "Front Analysis",
                                findings = result.data.toFindingsMap()
                            )
                        }

                        is PostureAnalysisResult.Back -> {
                            PostureResultSection(
                                title = "Back Analysis",
                                findings = result.data.toFindingsMap()
                            )
                        }

                        is PostureAnalysisResult.Left -> {
                            PostureResultSection(
                                title = "Left Analysis",
                                findings = result.data.toFindingsMap()
                            )
                        }

                        is PostureAnalysisResult.Right -> {
                            PostureResultSection(
                                title = "Right Analysis",
                                findings = result.data.toFindingsMap()
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(124.dp))
        }
    }
}


@Composable
fun PostureAnalysisResultScreen(
    viewModel: PostureAnalysisViewModel
) {
    val results = viewModel.uiState.value.postureStates.filter { it.completed }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(results) { posture ->
            posture.result?.let { result ->
                when (result) {
                    is PostureAnalysisResult.Front -> {
                        PostureResultSection(
                            title = "Front Analysis",
                            findings = result.data.toFindingsMap()
                        )
                    }

                    is PostureAnalysisResult.Back -> {
                        PostureResultSection(
                            title = "Back Analysis",
                            findings = result.data.toFindingsMap()
                        )
                    }

                    is PostureAnalysisResult.Left -> {
                        PostureResultSection(
                            title = "Left Analysis",
                            findings = result.data.toFindingsMap()
                        )
                    }

                    is PostureAnalysisResult.Right -> {
                        PostureResultSection(
                            title = "Right Analysis",
                            findings = result.data.toFindingsMap()
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PostureResultSection(
    title: String,
    findings: Map<String, PostureFinding>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        findings.forEach { (label, finding) ->
            if (finding.detected == true) {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = label,
                    style = SkyFitTypography.bodyMediumMediumBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$label: ${finding.turkishExplanation}",
                    style = SkyFitTypography.bodyMediumRegular,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

fun FrontPostureResponse.toFindingsMap(): Map<String, PostureFinding> = mapOf(
    "Genu Varum" to genuVarum,
    "Patellar Lateral Tilt (Right)" to patellarLateralTiltRight,
    "Patellar Lateral Tilt (Left)" to patellarLateralTiltLeft,
    "Shoulder Elevation (Right)" to shoulderElevationRight,
    "Cervical Lateral Tilt (Right)" to cervicalLateralTiltRight,
    "Lateral SIAS Pelvic Drop (Left)" to lateralSIASPelvicDropLeft,
    "Shoulder Protraction" to shoulderProtraction
)

fun BackPostureResponse.toFindingsMap(): Map<String, PostureFinding> = mapOf(
    "Shoulder Elevation (Right)" to shoulderElevationRight,
    "Pelvic Rotation (Left)" to pelvicRotationLeft
)

fun LeftPostureResponse.toFindingsMap(): Map<String, PostureFinding> = mapOf(
    "Cervical Rotation (Left)" to cervicalRotationLeft,
    "Hip Extension Restriction" to hipExtensionRestriction
)

fun RightPostureResponse.toFindingsMap(): Map<String, PostureFinding> = mapOf(
    "Cervical Rotation (Right)" to cervicalRotationRight,
    "Hip Extension Restriction" to hipExtensionRestriction,
    "Decreased Lordosis" to decreasedLordosis,
    "Decreased Kyphosis" to decreasedKyphosis,
    "Anterior Cervical Tilt" to anteriorCervicalTilt
)
