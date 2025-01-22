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
fun MobileExploreTrainersScreen(rootNavigator: Navigator) {

    val showSearch: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileExploreTrainersScreenToolbarComponent()
                if (showSearch) {
                    MobileExploreTrainersScreenSearchComponent()
                }
                MobileExploreTrainersScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreTrainersGridComponent()
        }
    }
}

@Composable
private fun MobileExploreTrainersScreenToolbarComponent() {
    TodoBox("MobileExploreTrainersScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreTrainersScreenSearchComponent() {
    TodoBox("MobileExploreTrainersScreenSearchComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreTrainersScreenTabsComponent() {
    TodoBox("MobileExploreTrainersScreenTabsComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreTrainersGridComponent() {
    TodoBox("MobileExploreTrainersComponent", Modifier.size(398.dp, 866.dp))
}