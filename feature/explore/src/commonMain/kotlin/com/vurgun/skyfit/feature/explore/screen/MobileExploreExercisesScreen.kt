package com.vurgun.skyfit.feature.explore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.exercise.VerticalExerciseCardItemComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent

@Composable
fun MobileExploreExercisesScreen(goToBack: () -> Unit) {
    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SkyFitScreenHeader("Popüler Antrenmanlar", onClickBack = {})
                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        val participants = listOf("123", "48", "129471", "31241", "83940")
        val exercises = listOf(
            ExerciseCardItemViewData("url1", "Dumbbell Exercise", participants, 4.8f),
            ExerciseCardItemViewData("url2", "Running", participants, 4.7f),
            ExerciseCardItemViewData("url3", "Push Ups", participants, 4.9f),
            ExerciseCardItemViewData("url4", "Core", participants, 4.6f),
            ExerciseCardItemViewData("url5", "Stretching", participants, 4.5f),
            ExerciseCardItemViewData("url6", "Yoga", participants, 4.8f),
            ExerciseCardItemViewData("url7", "HIIT", participants, 4.7f)
        )

        MobileExploreExercisesGridComponent(exercises = exercises)
    }
}

@Composable
private fun MobileExploreExercisesGridComponent(exercises: List<ExerciseCardItemViewData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
    ) {
        items(exercises) { exercise ->
            ExerciseCardItemBox(
                imageUrl = exercise.imageUrl,
                name = exercise.name,
                participants = exercise.participants,
                rating = exercise.rating,
                onClick = { /* Handle click */ }
            )
        }
    }
}

@Composable
private fun ExerciseCardItemBox(
    imageUrl: String,
    name: String,
    participants: List<String>,
    rating: Float,
    onClick: () -> Unit
) {
    VerticalExerciseCardItemComponent(
        imageUrl = imageUrl,
        name = name,
        participants = participants,
        rating = rating,
        onClick = { /* Handle click */ },
        extraParticipantsCount = 123
    )
}

private data class ExerciseCardItemViewData(
    val imageUrl: String,
    val name: String,
    val participants: List<String>,
    val rating: Float
)
