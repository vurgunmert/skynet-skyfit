package com.vurgun.skyfit.health.posture.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureTypeDTO
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureFinding
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.icon.CloseIconRow
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.health.posture.PostureAnalysisAction
import com.vurgun.skyfit.health.posture.PostureAnalysisContentState
import com.vurgun.skyfit.health.posture.PostureOptionState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_arrow_replay
import fiwe.core.ui.generated.resources.ic_chart_pie
import fiwe.core.ui.generated.resources.ic_check
import fiwe.core.ui.generated.resources.posture_view_back
import fiwe.core.ui.generated.resources.posture_view_front
import fiwe.core.ui.generated.resources.posture_view_left
import fiwe.core.ui.generated.resources.posture_view_right

@Composable
fun PostureReportContent(
    content: PostureAnalysisContentState.Report,
    onAction: (PostureAnalysisAction) -> Unit
) {
    var isReportVisible: Boolean by remember { mutableStateOf(true) }
    var selectedType: PostureTypeDTO by remember { mutableStateOf(PostureTypeDTO.Front) }
    val selectedImageBitmap by remember(content.optionStates, selectedType) {
        derivedStateOf {
            content.optionStates.firstOrNull { it.type == selectedType }?.bitmap
        }
    }

    Box(Modifier.fillMaxSize()) {

        content.optionStates.last().bitmap?.let {
            Image(
                bitmap = it,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }

        PostureResultContent_BottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.systemBars),
            onClickReset = { onAction(PostureAnalysisAction.Reset) },
            onClickComplete = { onAction(PostureAnalysisAction.OnConfirmComplete) },
            onClickReport = { isReportVisible = !isReportVisible }
        )

        if (isReportVisible) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 96.dp)
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                CloseIconRow(
                    onClick = { isReportVisible = !isReportVisible },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(4.dp))

                selectedImageBitmap?.let {
                    Image(
                        bitmap = it,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .aspectRatio(it.width.toFloat() / it.height.toFloat()),
                        contentScale = ContentScale.Fit,
                        contentDescription = null
                    )
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SecondaryPillChip(
                        text = stringResource(Res.string.posture_view_front),
                        selected = selectedType == PostureTypeDTO.Front,
                        onClick = { selectedType = PostureTypeDTO.Front }
                    )
                    SecondaryPillChip(
                        text = stringResource(Res.string.posture_view_back),
                        selected = selectedType == PostureTypeDTO.Back,
                        onClick = { selectedType = PostureTypeDTO.Back })
                    SecondaryPillChip(
                        text = stringResource(Res.string.posture_view_left),
                        selected = selectedType == PostureTypeDTO.Left,
                        onClick = { selectedType = PostureTypeDTO.Left })
                    SecondaryPillChip(
                        text = stringResource(Res.string.posture_view_right),
                        selected = selectedType == PostureTypeDTO.Right,
                        onClick = { selectedType = PostureTypeDTO.Right })
                }

                PostureOptionFindings(selectedType, content.optionStates)

                Spacer(Modifier.height(124.dp))
            }
        }
    }
}


@Composable
private fun PostureOptionFindings(
    selectedType: PostureTypeDTO,
    results: List<PostureOptionState>
) {
    val selectedPosture = results.firstOrNull { it.type == selectedType }

    selectedPosture?.findings?.let { findings ->
        val titleRes = when (selectedType) {
            PostureTypeDTO.Front -> Res.string.posture_view_front
            PostureTypeDTO.Back -> Res.string.posture_view_back
            PostureTypeDTO.Left -> Res.string.posture_view_left
            PostureTypeDTO.Right -> Res.string.posture_view_right
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            PostureResultSection(
                title = stringResource(titleRes),
                findings = findings
            )
        }
    }
}


@Composable
private fun PostureResultSection(
    title: String,
    findings: List<PostureFinding>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        findings.forEach { finding ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = finding.key.capitalize(Locale.current),
                style = SkyFitTypography.bodyMediumMediumBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = finding.explanation,
                style = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun PostureResultContent_BottomBar(
    modifier: Modifier,
    onClickReset: () -> Unit,
    onClickComplete: () -> Unit,
    onClickReport: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(SkyFitColor.background.surfaceSecondary)
            .wrapContentWidth()
            .padding(vertical = 24.dp, horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SecondaryIconButton(
            painter = painterResource(Res.drawable.ic_arrow_replay),
            onClick = onClickReset,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.width(48.dp))

        PrimaryIconButton(
            painter = painterResource(Res.drawable.ic_check),
            onClick = onClickComplete,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.width(48.dp))

        PrimaryIconButton(
            painter = painterResource(Res.drawable.ic_chart_pie),
            onClick = onClickReport,
            modifier = Modifier.size(48.dp)
        )
    }
}