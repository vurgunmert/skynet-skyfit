package com.vurgun.skyfit.presentation.mobile.features.user.messages

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
fun MobileUserConversationsScreen(navigator: Navigator) {

    var conversations: List<Any> = listOf(1, 2)

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileUserConversationsScreenToolbarComponent()
                MobileUserConversationsScreenActiveUsersComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserConversationsSearchComponent()

            if (conversations.isEmpty()) {
                MobileUserConversationsEmptyComponent()
            } else {
                MobileUserConversationsComponent()
                Spacer(Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun MobileUserConversationsScreenToolbarComponent() {
    TodoBox("MobileUserConversationsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserConversationsScreenActiveUsersComponent() {
    TodoBox("MobileUserConversationsScreenActiveUsersComponent", Modifier.size(430.dp, 116.dp))
}

@Composable
private fun MobileUserConversationsSearchComponent() {
    TodoBox("MobileUserConversationsSearchComponent", Modifier.size(430.dp, 88.dp))
}

@Composable
private fun MobileUserConversationsEmptyComponent() {
    TodoBox("MobileUserConversationsEmptyComponent", Modifier.size(398.dp, 128.dp))
}

@Composable
private fun MobileUserConversationsComponent() {
    TodoBox("MobileUserConversationsComponent", Modifier.size(430.dp, 420.dp))
}