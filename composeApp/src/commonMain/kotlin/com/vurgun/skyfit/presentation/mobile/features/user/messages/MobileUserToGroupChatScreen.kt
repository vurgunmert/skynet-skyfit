package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserToGroupChatScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserToGroupChatScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserToGroupChatScreenMessagesComponent()

            Spacer(Modifier.weight(1f))

            MobileUserToGroupChatScreenInputComponent()

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MobileUserToGroupChatScreenToolbarComponent() {
    TodoBox("MobileUserToGroupChatScreenToolbarComponent", Modifier.size(430.dp, 80.dp))
}

@Composable
private fun MobileUserToGroupChatScreenMessagesComponent() {
    TodoBox("MobileUserToGroupChatScreenMessagesComponent", Modifier.size(430.dp, 208.dp))
}

@Composable
private fun MobileUserToGroupChatScreenInputComponent() {
    TodoBox("MobileUserToGroupChatScreenInputComponent", Modifier.size(430.dp, 24.dp))
}