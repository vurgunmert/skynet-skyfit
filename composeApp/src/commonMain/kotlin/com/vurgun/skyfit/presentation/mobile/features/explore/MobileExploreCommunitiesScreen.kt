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
fun MobileExploreCommunitiesScreen(rootNavigator: Navigator) {

    val showSearch: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                ExploreCommunitiesScreenToolbarComponent()
                if (showSearch) {
                    ExploreCommunitiesScreenSearchComponent()
                }
                ExploreCommunitiesScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreFeaturedCommunitiesComponent()
            MobileExploreNewCommunitiesComponent()
        }
    }
}

@Composable
private fun ExploreCommunitiesScreenToolbarComponent() {
    TodoBox("ExploreCommunitiesScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun ExploreCommunitiesScreenSearchComponent() {
    TodoBox("ExploreCommunitiesScreenSearchComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun ExploreCommunitiesScreenTabsComponent() {
    TodoBox("ExploreCommunitiesScreenTabsComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileExploreFeaturedCommunitiesComponent() {
    TodoBox("MobileExploreFeaturedCommunitiesComponent", Modifier.size(398.dp, 152.dp))
}

@Composable
private fun MobileExploreNewCommunitiesComponent() {
    TodoBox("MobileExploreNewCommunitiesComponent", Modifier.size(430.dp, 400.dp))
}
