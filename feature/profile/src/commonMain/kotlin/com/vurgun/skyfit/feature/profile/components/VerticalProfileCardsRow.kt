package com.vurgun.skyfit.feature.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.profile.components.viewdata.FacilityProfileCardItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.TrainerProfileCardItemViewData

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
    trainers: List<TrainerProfileCardItemViewData>,
    onClick: () -> Unit = {},
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
                imageUrl = trainer.imageUrl,
                name = trainer.name,
                followerCount = trainer.followerCount,
                lessonCount = trainer.classCount,
                videoCount = trainer.videoCount,
                rating = trainer.rating,
                onClick = onClick
            )
        }
    }
}
