package com.vurgun.skyfit.feature.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.image.CircularImage
import com.vurgun.skyfit.ui.core.components.special.RatingStarComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun VerticalExerciseCardItemComponent(
    imageUrl: String,
    name: String,
    participants: List<String>,
    extraParticipantsCount: Int,
    rating: Double,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(186.dp, 278.dp)
            .clickable { onClick() }
    ) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Exercise Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        // Rating Star Box
        RatingStarComponent(rating, Modifier.align(Alignment.TopEnd).padding(8.dp))

        // Bottom Details
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(96.dp)
        ) {
            // Background Blur
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF012E36).copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                    .blur(16.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            )

            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        SkyFitColor.background.fillTransparentSecondaryActive,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Exercise Name
                Text(
                    text = name,
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(Modifier.height(8.dp))

                // Avatar List with Overlap
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.height(4.dp))

                    participants.take(4).forEachIndexed { index, avatarUrl ->
                        CircularImage(
                            url = avatarUrl,
                            modifier = Modifier.size(32.dp).offset(x = (-10 * index).dp)
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


                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = "Preview exercise",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}