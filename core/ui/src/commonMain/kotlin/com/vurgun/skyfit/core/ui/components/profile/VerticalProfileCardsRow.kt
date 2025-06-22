package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.profile.FacilityProfileCardItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile

@Composable
fun VerticalFacilityProfileCardsRow(
    facilities: List<FacilityProfileCardItemViewData>,
    onClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        items(facilities) { facility ->
            FacilityProfileCardItemBox(
                imageUrl = facility.imageUrl,
                name = facility.name,
                memberCount = facility.memberCount,
                trainerCount = facility.trainerCount,
                rating = facility.rating ?: 0f,
                onClick = onClick
            )
        }
    }
}

@Composable
fun VerticalTrainerProfileCardsRow(
    trainers: List<FacilityTrainerProfile>,
    onClick: (FacilityTrainerProfile) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPaddingStart: Dp
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = contentPaddingStart)
    ) {
        items(trainers) { trainer ->
            VerticalTrainerProfileCard(
                imageUrl = trainer.profileImageUrl,
                name = trainer.fullName,
                followerCount = trainer.followerCount,
                lessonCount = trainer.lessonTypeCount,
                videoCount = trainer.videoCount,
                rating = trainer.point,
                onClick = {onClick(trainer)}
            )
        }
    }
}
