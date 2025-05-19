package com.vurgun.skyfit.feature.connect.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageBubble
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageBubbleShimmer
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

class ChatBotMessageScreen(
    private val viewModel: ChatbotViewModel
): Screen {

    @Composable
    override fun Content() {
       val navigator = LocalNavigator.currentOrThrow

        ChatBotMessageScreenContent(viewModel) {
            navigator.pop()
        }
    }

}

@Composable
private fun ChatBotMessageScreenContent(
   viewModel: ChatbotViewModel,
   onDismiss: () -> Unit
) {

    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    SkyFitScaffold(topBar = {
        SkyFitScreenHeader("Chatbot", onClickBack = onDismiss)
    }) {
        Column(
            Modifier.fillMaxSize()
                .background(SkyFitColor.background.default)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }

                if (isLoading) {
                    item {
                        SkyFitChatMessageBubbleShimmer()
                    }
                }
            }

            Box(Modifier.padding(24.dp).fillMaxWidth()) {
                SkyFitChatMessageInputComponent(
                    enabled = !isLoading,
                    onSend = { userInput -> viewModel.sendQuery(userInput) }
                )
            }
        }
    }
}
