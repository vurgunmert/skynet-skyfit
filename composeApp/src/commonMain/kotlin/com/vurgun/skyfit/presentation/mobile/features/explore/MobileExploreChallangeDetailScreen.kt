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
fun MobileExploreChallengeDetailScreen(rootNavigator: Navigator) {

    var joined: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileExploreChallengeDetailScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (joined) {
                MobileExploreChallengeDetailScreenParticipantBarComponent()
            } else {
                MobileExploreChallengeDetailScreenJoinInfoComponent()
            }

            MobileExploreChallengeDetailScreenLeadershipBoardComponent()
            MobileExploreChallengeDetailScreenActivitySummaryComponent()

            if (joined) {
                MobileExploreChallengeDetailScreenExitActionComponent()
            } else {
                MobileExploreChallengeDetailScreenJoinActionComponent()
            }
        }
    }
}

@Composable
private fun MobileExploreChallengeDetailScreenToolbarComponent() {
    TodoBox("MobileExploreChallengeDetailScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreChallengeDetailScreenParticipantBarComponent() {
    TodoBox("MobileExploreChallengeDetailScreenParticipantBarComponent", Modifier.size(382.dp, 56.dp))
}


@Composable
private fun MobileExploreChallengeDetailScreenJoinInfoComponent() {
    TodoBox("MobileExploreChallengeDetailScreenJoinInfoComponent", Modifier.size(382.dp, 418.dp))
}

@Composable
private fun MobileExploreChallengeDetailScreenLeadershipBoardComponent() {
    TodoBox("MobileExploreChallengeDetailScreenLeadershipBoardComponent", Modifier.size(382.dp, 644.dp))
}

@Composable
private fun MobileExploreChallengeDetailScreenActivitySummaryComponent() {
    TodoBox("MobileExploreChallengeDetailScreenActivitySummaryComponent", Modifier.size(382.dp, 436.dp))
}

@Composable
private fun MobileExploreChallengeDetailScreenExitActionComponent() {
    TodoBox("MobileExploreChallengeDetailScreenExitActionComponent", Modifier.size(382.dp, 48.dp))
}

@Composable
private fun MobileExploreChallengeDetailScreenJoinActionComponent() {
    TodoBox("MobileExploreChallengeDetailScreenJoinActionComponent", Modifier.size(382.dp, 48.dp))
}