package com.vurgun.skyfit.feature_profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
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
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_star_filled
import skyfit.composeapp.generated.resources.logo_skyfit


@Composable
fun TrainerProfileCardItemBox(
    imageUrl: String,
    name: String,
    followerCount: Int,
    classCount: Int,
    videoCount: Int,
    rating: Double?,
    onClick: () -> Unit
) {
    ProfileCardItemBox(
        imageUrl = imageUrl,
        name = name,
        details = listOf(
            Pair("$followerCount", "Takipçi"),
            Pair("$classCount", "Dersler"),
            Pair("$videoCount", "Videolar")
        ),
        rating = rating ?: 0.0,
        onClick = onClick
    )
}

@Composable
fun FacilityProfileCardItemBox(
    imageUrl: String,
    name: String,
    memberCount: Int,
    trainerCount: Int,
    rating: Double,
    onClick: () -> Unit
) {
    ProfileCardItemBox(
        imageUrl = imageUrl,
        name = name,
        details = listOf(
            Pair("$memberCount", "Üye"),
            Pair("$trainerCount", "Eğitmen")
        ),
        rating = rating,
        showRatingInDetail = true,
        onClick = onClick
    )
}

@Composable
private fun ProfileCardItemBox(
    imageUrl: String,
    name: String,
    details: List<Pair<String, String>>,
    rating: Double,
    showRatingInDetail: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .size(186.dp, 278.dp)
            .clickable { onClick() }
    ) {
        // Profile Image
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        // Rating Star
        RatingStarComponent(rating, Modifier.align(Alignment.TopEnd).padding(8.dp))

        // Details Box
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

            // Content
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        SkyFitColor.background.fillTransparentSecondaryActive,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = name,
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    details.forEachIndexed { index, detail ->
                        ProfileCardVerticalDetailItemComponent(title = detail.first, subtitle = detail.second)
                        if (index < details.lastIndex) VerticalDetailDivider()
                    }
                    if (showRatingInDetail) {
                        VerticalDetailDivider()
                        RatingStarComponent(rating, Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCardVerticalDetailItemComponent(title: String, subtitle: String) {
    Column(
        modifier = Modifier.size(56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = SkyFitTypography.bodyLargeSemibold, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, style = SkyFitTypography.bodySmallMedium, color = Color.Gray)
    }
}

@Composable
fun RatingStarComponent(rating: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(SkyFitColor.background.fillSemiTransparent, CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rating",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            painter = painterResource(Res.drawable.ic_star_filled),
            contentDescription = "Star",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun VerticalDetailDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(48.dp)
            .background(SkyFitColor.border.default)
    )
}

@Composable
fun ExerciseProfileCardItemComponent(
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
                        AvatarImage(
                            avatarUrl = avatarUrl,
                            modifier = Modifier.offset(x = (-10 * index).dp)
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

@Composable
fun AvatarImage(avatarUrl: String, modifier: Modifier = Modifier.size(32.dp)) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = "Avatar",
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Gray),
        contentScale = ContentScale.Crop
    )
}
