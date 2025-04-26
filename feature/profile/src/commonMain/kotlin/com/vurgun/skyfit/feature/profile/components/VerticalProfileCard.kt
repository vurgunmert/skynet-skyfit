package com.vurgun.skyfit.feature.profile.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.divider.VerticalDivider
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.RatingStarComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.follower_label
import skyfit.ui.core.generated.resources.lessons_label
import skyfit.ui.core.generated.resources.videos_label

@Composable
fun VerticalTrainerProfileCard(
    imageUrl: String?,
    name: String,
    followerCount: Int,
    lessonCount: Int,
    videoCount: Int,
    rating: Float?,
    onClick: () -> Unit
) {
    ProfileCardItemBox(
        imageUrl = imageUrl,
        name = name,
        details = listOf(
            Pair("$followerCount", stringResource(Res.string.follower_label)),
            Pair("$lessonCount", stringResource(Res.string.lessons_label)),
            Pair("$videoCount", stringResource(Res.string.videos_label))
        ),
        rating = rating ?: 0f,
        onClick = onClick
    )
}

@Composable
fun FacilityProfileCardItemBox(
    imageUrl: String,
    name: String,
    memberCount: Int,
    trainerCount: Int,
    rating: Float,
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
    imageUrl: String?,
    name: String,
    details: List<Pair<String, String>>,
    rating: Float,
    showRatingInDetail: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .size(186.dp, 278.dp)
            .clickable { onClick() }
    ) {
        // Profile Image
        NetworkImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
        )

        RatingStarComponent(rating, Modifier.align(Alignment.TopEnd).padding(8.dp))

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(96.dp)
        ) {
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
                        VerticalProfileStatisticItem(title = detail.first, subtitle = detail.second)
                        if (index < details.lastIndex) VerticalDivider(Modifier.height(48.dp))
                    }
                    if (showRatingInDetail) {
                        VerticalDivider(Modifier.height(48.dp))
                        RatingStarComponent(rating, Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}