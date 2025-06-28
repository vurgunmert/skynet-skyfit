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
import com.vurgun.skyfit.core.ui.components.avatar.AvatarGroup
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.special.RatingButton
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import dev.chrisbanes.haze.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_arrow_right
import fiwe.core.ui.generated.resources.ic_image
import fiwe.core.ui.generated.resources.im_placeholder_dark

data class PopularExerciseShowcaseUiData(
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val participantAvatarUrls: List<String>
)

@Composable
fun PopularExerciseShowcaseCard(
    data: PopularExerciseShowcaseUiData,
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
            url = data.imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState),
            placeholder = Res.drawable.ic_image,
            error = Res.drawable.im_placeholder_dark
        )

        RatingButton(
            rating = data.rating,
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
        )

        Column(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillSemiTransparent)
                .hazeEffect(hazeState, hazeStyle)
                .padding(16.dp)
        ) {
            SkyText(
                text = data.title,
                styleType = TextStyleType.BodyLargeSemibold,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AvatarGroup(
                    avatarUrls = data.participantAvatarUrls,
                    maxVisible = 4,
                    avatarSize = 32.dp,
                    overlapOffset = 10.dp,
                    modifier = Modifier.weight(1f),
                )

                SkyIcon(
                    res = Res.drawable.ic_arrow_right,
                    size = SkyIconSize.Medium
                )
            }
        }
    }
}


@Preview
@Composable
private fun PopularExerciseShowcaseCardPreview() {

    val data = PopularExerciseShowcaseUiData(
        title = "Running",
        imageUrl = "Any",
        rating = 4.1f,
        participantAvatarUrls = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    )

    PopularExerciseShowcaseCard(
        data = data,
        modifier = Modifier.size(186.dp, 278.dp),
        onClick = { }
    )
}