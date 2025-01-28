package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitConversationViewModel
import com.vurgun.skyfit.utils.keyboardAsState
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileUserToUserChatScreen(navigator: Navigator) {

    val viewModel: SkyFitConversationViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val keyboardState by keyboardAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserToUserChatScreenToolbarComponent()
        }
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }
            }

            SkyFitChatMessageInputComponent(
                modifier = Modifier.padding(bottom = keyboardState.heightDp).align(Alignment.BottomCenter),
                onSend = { userInput -> viewModel.sendMessage(userInput) }
            )
        }
    }
}

@Composable
private fun MobileUserToUserChatScreenToolbarComponent() {
    TodoBox("MobileUserToUserChatScreenToolbarComponent", Modifier.size(430.dp, 80.dp))
}