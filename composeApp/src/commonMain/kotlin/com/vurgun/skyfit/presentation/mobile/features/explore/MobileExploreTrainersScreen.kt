package com.vurgun.skyfit.presentation.mobile.features.explore

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
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.components.TrainerProfileCardItemBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileExploreTrainersScreen(rootNavigator: Navigator) {

    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SkyFitScreenHeader("Pro Antrenorler", onBackClick = {})
                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        val trainers = listOf(
            Trainer("url1", "Lucas Bennett", 1800, 13, 32, 4.8),
            Trainer("url2", "Olivia Hayes", 1500, 10, 20, 4.5),
            Trainer("url3", "Mason Reed", 2000, 15, 40, 4.9),
            Trainer("url4", "Sophia Hill", 1700, 12, 28, 4.7),
            Trainer("url5", "Emma Johnson", 1600, 11, 25, 4.6),
            Trainer("url6", "James Smith", 1900, 14, 35, 4.8),
            Trainer("url7", "Ava Brown", 1750, 13, 30, 4.7)
        )

        MobileExploreTrainersGridComponent(trainers = trainers)
    }
}

@Composable
private fun MobileExploreTrainersGridComponent(trainers: List<Trainer>) {
    LazyVerticalGrid(        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
    ) {
        items(trainers) { trainer ->
            TrainerProfileCardItemBox(
                imageUrl = trainer.imageUrl,
                name = trainer.name,
                followerCount = trainer.followerCount,
                classCount = trainer.classCount,
                videoCount = trainer.videoCount,
                rating = trainer.rating,
                onClick = { /* Handle click */ }
            )
        }
    }
}

private data class Trainer(
    val imageUrl: String,
    val name: String,
    val followerCount: Int,
    val classCount: Int,
    val videoCount: Int,
    val rating: Double?
)