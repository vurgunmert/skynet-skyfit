package com.vurgun.skyfit.core.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import dev.chrisbanes.haze.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.chatbot_shortcut_body_analysis_left
import skyfit.core.ui.generated.resources.ic_image

@Composable
fun ExerciseShowcaseCard(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier.size(186.dp, 278.dp),
    onClick: () -> Unit = {},
) {
    val hazeState = rememberHazeState()
    val hazeStyle = HazeStyle(
        backgroundColor = SkyFitColor.background.surfaceSecondary,
        tints = listOf(
            HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
        ),
        blurRadius = 20.dp,
        noiseFactor = 0f
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        SkyImage(
            url = imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState),
            placeholder = Res.drawable.ic_image,
            error = Res.drawable.chatbot_shortcut_body_analysis_left
        )

        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillSemiTransparent)
                .hazeEffect(hazeState, hazeStyle)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyText(
                text = title,
                styleType = TextStyleType.BodyLargeSemibold
            )
        }
    }
}

@Preview
@Composable
private fun ShowcaseCardPreview() {
    ExerciseShowcaseCard(
        imageUrl = "",
        title = "Dumbell Exercise",
        onClick = { }
    )
}