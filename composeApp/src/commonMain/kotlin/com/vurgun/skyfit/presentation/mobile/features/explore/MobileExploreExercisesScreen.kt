package com.vurgun.skyfit.presentation.mobile.features.explore

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
fun MobileExploreExercisesScreen(rootNavigator: Navigator) {

    val showSearch: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileExploreExercisesScreenToolbarComponent()
                if (showSearch) {
                    MobileExploreExercisesScreenSearchComponent()
                }
                MobileExploreExercisesScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreExercisesGridComponent()
        }
    }
}

@Composable
private fun MobileExploreExercisesScreenToolbarComponent() {
    TodoBox("MobileExploreExercisesScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreExercisesScreenSearchComponent() {
    TodoBox("MobileExploreExercisesScreenSearchComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreExercisesScreenTabsComponent() {
    TodoBox("MobileExploreExercisesScreenTabsComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreExercisesGridComponent() {
    TodoBox("MobileExploreTrainersComponent", Modifier.size(398.dp, 572.dp))
}