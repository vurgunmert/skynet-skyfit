package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

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
            when (val result = posture.result) {
                is PostureAnalysisResult.Front -> {
                    PostureResultSection(
                        title = "Front Analysis",
                        findings = mapOf(
                            "Genu Varum" to result.data.genu_varum.explanation,
                            "Left Patellar Tilt" to result.data.left_patellar_tilt.explanation,
                            "Right Patellar Tilt" to result.data.right_patellar_tilt.explanation,
                            "Right Shoulder Elevated" to result.data.right_shoulder_elevated.explanation,
                            "Left Shoulder Elevated" to result.data.left_shoulder_elevated.explanation,
                            "Right Cervical Tilt" to result.data.right_cervical_tilt.explanation,
                            "Left Cervical Tilt" to result.data.left_cervical_tilt.explanation,
                            "Left Pelvic Drop" to result.data.left_pelvic_drop.explanation,
                            "Right Pelvic Drop" to result.data.right_pelvic_drop.explanation,
                            "Shoulder Protraction" to result.data.shoulder_protraction.explanation,
                            "Shoulder Tightness" to result.data.shoulder_tightness.explanation,
                            "Pelvic Rotation" to result.data.pelvic_rotation.explanation
                        )
                    )
                }

                is PostureAnalysisResult.Back -> {
                    PostureResultSection(
                        title = "Back Analysis",
                        findings = mapOf(
                            "Decreased Lordosis" to result.data.decreased_lordosis.explanation,
                            "Decreased Kyphosis" to result.data.decreased_kyphosis.explanation
                        )
                    )
                }

                is PostureAnalysisResult.Left -> {
                    PostureResultSection(
                        title = "Left Analysis",
                        findings = mapOf(
                            "Cervical Rotation (Left)" to result.data.cervical_rotation_left.explanation
                        )
                    )
                }

                is PostureAnalysisResult.Right -> {
                    PostureResultSection(
                        title = "Right Analysis",
                        findings = mapOf(
                            "Cervical Rotation (Right)" to result.data.cervical_rotation_right.explanation
                        )
                    )
                }

                null -> {}
            }
        }
    }
}


@Composable
fun PostureResultSection(
    title: String,
    findings: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = title,
            style = SkyFitTypography.bodyMediumMediumBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        findings.forEach { (label, explanation) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = label,
                    style = SkyFitTypography.bodyLargeMedium,
                )
                Text(
                    text = explanation,
                    style = SkyFitTypography.bodyMediumRegular,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
