package com.vurgun.skyfit.health.posture.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureTypeDTO
import com.vurgun.skyfit.core.ui.components.box.rememberAnimatedBorderBrush
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.health.posture.PostureAnalysisAction
import com.vurgun.skyfit.health.posture.PostureAnalysisViewModel
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_check_circle
import fiwe.core.ui.generated.resources.ic_close
import fiwe.core.ui.generated.resources.posture_option_view_back
import fiwe.core.ui.generated.resources.posture_option_view_front
import fiwe.core.ui.generated.resources.posture_option_view_left
import fiwe.core.ui.generated.resources.posture_option_view_right
import fiwe.core.ui.generated.resources.posture_view_back
import fiwe.core.ui.generated.resources.posture_view_front
import fiwe.core.ui.generated.resources.posture_view_left
import fiwe.core.ui.generated.resources.posture_view_right

@Composable
fun PostureOptionsContent(
    viewModel: PostureAnalysisViewModel,
    onAction: (PostureAnalysisAction) -> Unit,
    modifier: Modifier
) {
    val postureStates by viewModel.postureStates.map { it.values.toList() }.collectAsState(initial = emptyList())

    val hasError = postureStates.any { it.isFailed }


    Column(
        modifier = modifier
            .widthIn(max = 358.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (hasError) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Postür analizi sırasında bir sorun oluştu. Lütfen kameranın düzgün konumlandığından ve internet bağlantınızın aktif olduğundan emin olun.",
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.criticalOnBgFill,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        postureStates.forEach { posture ->
            val (res, label) = when (posture.type) {
                PostureTypeDTO.Front -> Res.drawable.posture_option_view_front to Res.string.posture_view_front
                PostureTypeDTO.Back -> Res.drawable.posture_option_view_back to Res.string.posture_view_back
                PostureTypeDTO.Left -> Res.drawable.posture_option_view_left to Res.string.posture_view_left
                PostureTypeDTO.Right -> Res.drawable.posture_option_view_right to Res.string.posture_view_right
            }

            PostureOptionCard(
                res = res,
                text = stringResource(label),
                pending = posture.isPending,
                completed = posture.isCompleted,
                failed = posture.isFailed,
                onClick = { onAction(PostureAnalysisAction.SelectOption(posture.type)) }
            )
        }
    }
}

@Composable
fun PostureOptionCard(
    res: DrawableResource,
    text: String,
    pending: Boolean,
    completed: Boolean,
    failed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        completed -> SkyFitColor.background.surfaceSuccessActive
        failed -> SkyFitColor.background.surfaceCriticalActive
        else -> SkyFitColor.background.fillTransparentSecondary
    }

    val staticBorderColor = when {
        completed -> SkyFitColor.border.success
        failed -> SkyFitColor.border.critical
        else -> SkyFitColor.transparent
    }

    val animatedBrush = rememberAnimatedBorderBrush(
        enabled = pending,
        colors  = listOf(SkyFitColor.border.default,SkyFitColor.border.secondaryButton, SkyFitColor.border.secondaryButtonDisabled),
    )

    val borderModifier = if (pending) {
        Modifier.border(
            width = 1.dp,
            brush = animatedBrush,
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        Modifier.border(
            width = 1.dp,
            color = staticBorderColor,
            shape = RoundedCornerShape(16.dp)
        )
    }

    val (iconRes, tintColor) = when {
        completed -> Res.drawable.ic_check_circle to SkyFitColor.icon.success
        failed -> Res.drawable.ic_close to SkyFitColor.icon.critical
        else -> null to null
    }

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .then(borderModifier)
            .padding(LocalPadding.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = text,
            modifier = Modifier.size(width = 32.dp, height = 48.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        BodyMediumSemiboldText(
            text = text,
            modifier = Modifier.weight(1f)
        )

        if (iconRes != null && tintColor != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
