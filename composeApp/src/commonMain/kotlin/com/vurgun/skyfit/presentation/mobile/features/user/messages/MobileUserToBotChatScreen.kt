package com.vurgun.skyfit.presentation.mobile.features.user.messages

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
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitChatMessageBubbleShimmer
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileUserToBotChatScreen(navigator: Navigator) {

    val viewModel: ChatbotViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()

    // Auto-scroll to the last message when a new one arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    SkyFitScaffold(topBar = {
        SkyFitScreenHeader("Chatbot", onClickBack = { navigator.popBackStack() })
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
                    onSend = { userInput -> viewModel.sendQuery(userInput) }
                )
            }
        }
    }
}
