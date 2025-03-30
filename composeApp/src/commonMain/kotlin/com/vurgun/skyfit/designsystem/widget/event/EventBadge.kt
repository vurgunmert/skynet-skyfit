package com.vurgun.skyfit.designsystem.widget.event

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.designsystem.utils.PreviewMobileScaffoldColumn


sealed interface BadgeState {
    data object Default : BadgeState
    data object Success : BadgeState
    data object Error : BadgeState
    data object Warning : BadgeState
}

@Composable
fun EventBadge(
    value: String,
    modifier: Modifier = Modifier,
    state: BadgeState = BadgeState.Default
) {
    val backgroundColor = when (state) {
        BadgeState.Default -> SkyFitColor.background.surfaceInfo
        BadgeState.Error -> SkyFitColor.background.surfaceCriticalActive
        BadgeState.Warning -> SkyFitColor.background.surfaceCriticalActive
        BadgeState.Success -> SkyFitColor.background.surfaceSuccessActive
    }
    val textColor = when (state) {
        BadgeState.Default -> SkyFitColor.text.default
        BadgeState.Error -> SkyFitColor.text.criticalOnBgFill
        BadgeState.Warning -> SkyFitColor.text.default
        BadgeState.Success -> SkyFitColor.text.successOnBgFill
    }
    Text(
        text = value,
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = SkyFitTypography.bodyMediumMedium.copy(color = textColor)
    )
}

@Preview
@Composable
private fun EventBadge_Previews() {
    PreviewMobileScaffoldColumn {
        EventBadge("Default")
        EventBadge("Error", state = BadgeState.Error)
        EventBadge("Warning", state = BadgeState.Warning)
        EventBadge("Success", state = BadgeState.Success)
    }
}