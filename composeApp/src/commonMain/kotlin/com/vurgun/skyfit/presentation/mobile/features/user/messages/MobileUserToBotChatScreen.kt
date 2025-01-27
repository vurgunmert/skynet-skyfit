package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject


@Composable
fun MobileUserToBotChatScreen(navigator: Navigator) {

    val viewModel: ChatbotViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            SkyFitChatMessageInputComponent(
                onSend = { userInput -> viewModel.sendQuery(userInput) }
            )
        }
    }
}
