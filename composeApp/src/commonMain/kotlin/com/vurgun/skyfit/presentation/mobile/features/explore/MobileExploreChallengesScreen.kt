package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun MobileExploreChallengesScreen(rootNavigator: Navigator) {

    val showSearch: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileExploreChallengesScreenToolbarComponent()
                if (showSearch) {
                    MobileExploreChallengesScreenSearchComponent()
                }
                MobileExploreChallengesScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(30.dp))
            MobileExploreUserChallengesComponent()
            Spacer(Modifier.height(12.dp))
            MobileExploreActiveChallengesComponent()
            Spacer(Modifier.height(24.dp))
            MobileExploreActiveChallengesNavigatorComponent()
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MobileExploreChallengesScreenToolbarComponent() {
    TodoBox("MobileExploreChallengesScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreChallengesScreenSearchComponent() {
    TodoBox("MobileExploreChallengesScreenSearchComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileExploreChallengesScreenTabsComponent() {
    TodoBox("MobileExploreChallengesScreenTabsComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileExploreUserChallengesComponent() {
    TodoBox("ExploreUserChallengesComponent", Modifier.size(382.dp, 202.dp))
}

@Composable
private fun MobileExploreActiveChallengesComponent() {
    TodoBox("ExploreActiveChallengesComponent", Modifier.size(382.dp, 588.dp))
}

@Composable
private fun MobileExploreActiveChallengesNavigatorComponent() {
    TodoBox("ExploreActiveChallengesNavigatorComponent", Modifier.size(382.dp, 32.dp))
}