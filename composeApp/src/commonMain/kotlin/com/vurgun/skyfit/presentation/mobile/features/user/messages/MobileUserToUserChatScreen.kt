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
fun MobileUserToUserChatScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserToUserChatScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserToUserChatScreenMessagesComponent()

            Spacer(Modifier.weight(1f))

            MobileUserToUserChatScreenInputComponent()

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileUserToUserChatScreenToolbarComponent() {
    TodoBox("MobileUserToUserChatScreenToolbarComponent", Modifier.size(430.dp, 80.dp))
}

@Composable
private fun MobileUserToUserChatScreenMessagesComponent() {
    TodoBox("MobileUserToUserChatScreenMessagesComponent", Modifier.size(430.dp, 208.dp))
}

@Composable
private fun MobileUserToUserChatScreenInputComponent() {
    TodoBox("MobileUserToUserChatScreenInputComponent", Modifier.size(430.dp, 24.dp))
}