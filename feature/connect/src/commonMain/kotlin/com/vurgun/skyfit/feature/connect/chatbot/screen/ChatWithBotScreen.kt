package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyPageScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatWithBotAction
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatWithBotEffect
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatWithBotViewModel
import com.vurgun.skyfit.feature.connect.component.SkyChatMessageItem
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageBubbleShimmer
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageInputComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.chatbot_label

class ChatWithBotScreen(
    val query: String? = null,
    val sessionId: String? = null,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ChatWithBotViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                ChatWithBotEffect.NavigateBack -> {
                    navigator.pop()
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData(query, sessionId)
        }

        ChatBotMessageScreenContent(viewModel)
    }
}

@Composable
private fun ChatBotMessageScreenContent(
    viewModel: ChatWithBotViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    SkyPageScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CompactTopBar(
                title = stringResource(Res.string.chatbot_label),
                onClickBack = { viewModel.onAction(ChatWithBotAction.OnClickBack) }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(SkyFitColor.background.default)
                .clickable(
                    // Clear focus and hide keyboard when clicking anywhere
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                items(messages) { message ->
                    SkyChatMessageItem(message, showDate = false)
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
                    onSend = {
                        viewModel.onAction(ChatWithBotAction.OnSendQuery(it))
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}