package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.bodyanalysis.model.PostureType
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.posture_option_view_back
import skyfit.core.ui.generated.resources.posture_option_view_front
import skyfit.core.ui.generated.resources.posture_option_view_left
import skyfit.core.ui.generated.resources.posture_option_view_right
import skyfit.core.ui.generated.resources.ic_check_circle

@Composable
fun BoxScope.PostureAnalysisOptionsScreen(
    viewModel: PostureAnalysisViewModel
) {

    val postureStates = viewModel.uiState.collectAsState().value.postureStates

    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .widthIn(max = 358.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        postureStates.forEach { posture ->

            when (posture.type) {
                PostureType.Front -> {
                    PostureOptionCard(
                        res = Res.drawable.posture_option_view_front,
                        text = "Ön Görünüm",
                        completed = posture.completed,
                        onClick = { viewModel.selectPosture(posture.type) }
                    )
                }

                PostureType.Back -> {
                    PostureOptionCard(
                        res = Res.drawable.posture_option_view_back,
                        text = "Arka Görünüm",
                        completed = posture.completed,
                        onClick = { viewModel.selectPosture(posture.type) }
                    )
                }

                PostureType.Left -> {
                    PostureOptionCard(
                        res = Res.drawable.posture_option_view_left,
                        text = "Sol Görünüm",
                        completed = posture.completed,
                        onClick = { viewModel.selectPosture(posture.type) }
                    )
                }

                PostureType.Right -> {
                    PostureOptionCard(
                        res = Res.drawable.posture_option_view_right,
                        text = "Sağ Görünüm",
                        completed = posture.completed,
                        onClick = { viewModel.selectPosture(posture.type) }
                    )
                }
            }

        }
    }
}

@Composable
private fun PostureOptionCard(
    res: DrawableResource,
    text: String,
    completed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        completed -> SkyFitColor.background.surfaceSuccessActive
        else -> SkyFitColor.background.fillTransparentSecondary
    }

    val borderColor = when {
        completed -> SkyFitColor.border.success
        else -> SkyFitColor.transparent
    }

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(LocalPadding.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = text,
            modifier = Modifier.size(32.dp, 48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        BodyMediumSemiboldText(text = text, modifier = Modifier.weight(1f))
        if (completed) {
            Icon(
                painter = painterResource(Res.drawable.ic_check_circle),
                contentDescription = null,
                tint = SkyFitColor.icon.success,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
