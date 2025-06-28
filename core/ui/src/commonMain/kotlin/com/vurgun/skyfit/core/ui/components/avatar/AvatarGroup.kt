package com.vurgun.skyfit.core.ui.components.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.chatbot_shortcut_body_analysis_left
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AvatarGroup(
    avatarUrls: List<String>,
    maxVisible: Int = 4,
    avatarSize: Dp = 48.dp,
    overlapOffset: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    val visibleAvatars = avatarUrls.take(maxVisible)
    val extraCount = avatarUrls.size - maxVisible
    val avatarCount = visibleAvatars.size + if (extraCount > 0) 1 else 0
    val totalWidth = avatarSize + (avatarCount - 1) * (avatarSize - overlapOffset)

    Box(
        modifier = modifier
            .width(totalWidth)
            .height(avatarSize)
    ) {
        visibleAvatars.forEachIndexed { index, url ->
            SkyImage(
                url = url,
                error = Res.drawable.chatbot_shortcut_body_analysis_left,
                sizeOverride = avatarSize,
                shape = SkyImageShape.Circle,
                modifier = Modifier
                    .size(avatarSize)
                    .offset(x = (avatarSize - overlapOffset) * index)
                    .zIndex(index.toFloat())
            )
        }

        if (extraCount > 0) {
            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .offset(x = (avatarSize - overlapOffset) * visibleAvatars.size)
                    .zIndex(visibleAvatars.size.toFloat())
                    .background(SkyFitColor.specialty.buttonBgDisabled, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                SkyText(
                    text = "+$extraCount",
                    styleType = TextStyleType.BodyXSmallSemibold
                )
            }
        }
    }
}

@Preview
@Composable
private fun AvatarGroupPreview() {
    AvatarGroup(
        avatarUrls = listOf("1", "2", "3", "4", "5", "6"),
        maxVisible = 4,
        avatarSize = 32.dp,
        overlapOffset = 10.dp
    )
}