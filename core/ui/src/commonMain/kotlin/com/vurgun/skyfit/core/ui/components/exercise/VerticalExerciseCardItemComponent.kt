package com.vurgun.skyfit.core.ui.components.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.CircularImage
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.special.RatingButton
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_image
import fiwe.core.ui.generated.resources.ic_app_logo

@Composable
fun VerticalExerciseCardItemComponent(
    imageUrl: String,
    name: String,
    participants: List<String>,
    extraParticipantsCount: Int,
    rating: Float,
    onClick: () -> Unit
) {
    val hazeState = rememberHazeState()
    val hazeStyle = HazeStyle(
        backgroundColor = SkyFitColor.background.surfaceSecondary,
        tints = listOf(
            HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.3f))
        ),
        blurRadius = 10.dp,
        noiseFactor = 0.0f
    )

    Box(
        modifier = Modifier
            .size(186.dp, 278.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        // Background Image with hazeSource
        SkyImage(
            url = imageUrl,
            placeholder = Res.drawable.ic_image,
            error = Res.drawable.ic_image,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
        )

        // Rating Star Component
        RatingButton(rating, Modifier.align(Alignment.TopEnd).padding(8.dp))

        // Bottom Content with hazeEffect
        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(96.dp)
                .hazeEffect(hazeState, hazeStyle)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Exercise Name
                Text(
                    text = name,
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier.fillMaxWidth()
                )

                // Participants Row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    participants.take(4).forEachIndexed { index, avatarUrl ->
                        CircularImage(
                            url = avatarUrl,
                            modifier = Modifier
                                .size(32.dp)
                                .offset(x = (-10 * index).dp)
                        )
                    }

                    if (extraParticipantsCount > 0) {
                        Box(
                            modifier = Modifier
                                .offset(x = (-10 * 4).dp)
                                .size(32.dp)
                                .background(SkyFitColor.background.surfaceActive, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$extraParticipantsCount",
                                style = SkyFitTypography.bodySmallMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(Res.drawable.ic_app_logo),
                        contentDescription = "Preview exercise",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

}


@Preview
@Composable
private fun VerticalExerciseCardItemPreview() {
    VerticalExerciseCardItemComponent(
        imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/89/c5/07/89c5070d-7390-3692-886f-4bd4ed9ac908/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg",
        name = "Dumbell Exercise",
        participants = listOf(
            "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/89/c5/07/89c5070d-7390-3692-886f-4bd4ed9ac908/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg",
            "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/89/c5/07/89c5070d-7390-3692-886f-4bd4ed9ac908/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg",
            "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/89/c5/07/89c5070d-7390-3692-886f-4bd4ed9ac908/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg",
            "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/89/c5/07/89c5070d-7390-3692-886f-4bd4ed9ac908/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg",
        ),
        extraParticipantsCount = 123,
        rating = 4.8f,
        onClick = { }
    )
}