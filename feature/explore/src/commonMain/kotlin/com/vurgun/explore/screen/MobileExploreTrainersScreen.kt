package com.vurgun.explore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.profile.VerticalTrainerProfileCard
import com.vurgun.skyfit.core.data.v1.domain.profile.TrainerProfileCardItemViewData

@Composable
fun MobileExploreTrainersScreen(goToBack: () -> Unit) {

//    val viewModel = ExploreViewModel()
//    val trainers = viewModel.trainers
//    var isSearchVisible by remember { mutableStateOf(false) }
//
//    SkyFitScaffold(
//        topBar = {
//            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
//                SkyFitScreenHeader("Pro Antrenörler", onClickBack = {})
//                Spacer(Modifier.height(16.dp))
//                if (isSearchVisible) {
//                    SkyFitSearchTextInputComponent()
//                    Spacer(Modifier.height(16.dp))
//                }
//                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
//            }
//        }
//    ) {
//        val trainers = listOf(
//            TrainerProfileCardItemViewData("url1", "Lucas Bennett", 1800, 13, 32, 4.8f),
//            TrainerProfileCardItemViewData("url2", "Olivia Hayes", 1500, 10, 20, 4.5f),
//            TrainerProfileCardItemViewData("url3", "Mason Reed", 2000, 15, 40, 4.9f),
//            TrainerProfileCardItemViewData("url4", "Sophia Hill", 1700, 12, 28, 4.7f),
//            TrainerProfileCardItemViewData("url5", "Emma Johnson", 1600, 11, 25, 4.6f),
//            TrainerProfileCardItemViewData("url6", "James Smith", 1900, 14, 35, 4.8f),
//            TrainerProfileCardItemViewData("url7", "Ava Brown", 1750, 13, 30, 4.7f)
//        )
//
//        MobileExploreTrainersGridComponent(trainers = trainers)
//    }
}

@Composable
private fun MobileExploreTrainersGridComponent(trainers: List<TrainerProfileCardItemViewData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
    ) {
        items(trainers) { trainer ->
            VerticalTrainerProfileCard(
                imageUrl = trainer.imageUrl,
                name = trainer.name,
                followerCount = trainer.followerCount,
                lessonCount = trainer.classCount,
                videoCount = trainer.videoCount,
                rating = trainer.rating,
                onClick = { /* Handle click */ }
            )
        }
    }
}