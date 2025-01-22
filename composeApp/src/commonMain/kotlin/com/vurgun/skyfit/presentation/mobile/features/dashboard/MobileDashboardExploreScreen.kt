package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardExploreScreen(rootNavigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileDashboardExploreScreenSearchComponent()
                MobileDashboardExploreScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileDashboardExploreScreenFeaturedExercisesComponent()
            MobileDashboardExploreScreenFeaturedTrainersComponent()
            MobileDashboardExploreScreenFeaturedFacilitiesComponent()
            MobileDashboardExploreScreenFeaturedCommunitiesComponent()
            MobileDashboardExploreScreenFeaturedChallengesComponent()
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenSearchComponent() {
    TodoBox("MobileDashboardExploreScreenSearchComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileDashboardExploreScreenTabsComponent() {
    TodoBox("MobileDashboardExploreScreenTabsComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileDashboardExploreScreenFeaturedExercisesComponent() {
    TodoBox("MobileDashboardExploreScreenFeaturedExercisesComponent", Modifier.size(398.dp, 326.dp))
}

@Composable
private fun MobileDashboardExploreScreenFeaturedTrainersComponent() {
    TodoBox("MobileDashboardExploreScreenFeaturedTrainersComponent", Modifier.size(398.dp, 322.dp))
}

@Composable
private fun MobileDashboardExploreScreenFeaturedFacilitiesComponent() {
    TodoBox("MobileDashboardExploreScreenFeaturedFacilitiesComponent", Modifier.size(398.dp, 322.dp))
}

@Composable
private fun MobileDashboardExploreScreenFeaturedCommunitiesComponent() {
    TodoBox("MobileDashboardExploreScreenFeaturedCommunitiesComponent", Modifier.size(398.dp, 116.dp))
}

@Composable
private fun MobileDashboardExploreScreenFeaturedChallengesComponent() {
    TodoBox("MobileDashboardExploreScreenFeaturedChallengesComponent", Modifier.size(398.dp, 206.dp))
}