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
fun MobileExploreCommunityDetailScreen(rootNavigator: Navigator) {

    var joined: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileExploreCommunityDetailScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreCommunityDetailScreenInfoComponent()
            Spacer(Modifier.height(16.dp))
            MobileExploreCommunityDetailScreenJoinedGroupsComponent()
            Spacer(Modifier.height(16.dp))
            MobileExploreCommunityDetailScreenAvailableGroupsComponent()

            if (joined) {
                Spacer(Modifier.weight(1f))
                MobileExploreCommunityDetailScreenAddGroupActionComponent()
            }
        }
    }
}

@Composable
private fun MobileExploreCommunityDetailScreenToolbarComponent() {
    TodoBox("MobileExploreCommunityDetailScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreCommunityDetailScreenInfoComponent() {
    TodoBox("MobileExploreCommunityDetailScreenInfoComponent", Modifier.size(382.dp, 82.dp))
}


@Composable
private fun MobileExploreCommunityDetailScreenJoinedGroupsComponent() {
    TodoBox("MobileExploreCommunityDetailScreenJoinedGroupsComponent", Modifier.size(382.dp, 180.dp))
}

@Composable
private fun MobileExploreCommunityDetailScreenAvailableGroupsComponent() {
    TodoBox("MobileExploreCommunityDetailScreenAvailableGroupsComponent", Modifier.size(382.dp, 192.dp))
}

@Composable
private fun MobileExploreCommunityDetailScreenAddGroupActionComponent() {
    TodoBox("MobileExploreCommunityDetailScreenAddGroupActionComponent", Modifier.size(382.dp, 48.dp))
}
