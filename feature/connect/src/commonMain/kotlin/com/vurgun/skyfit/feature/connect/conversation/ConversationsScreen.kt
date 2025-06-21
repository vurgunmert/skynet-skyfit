package com.vurgun.skyfit.feature.connect.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.connect.conversation.component.ConversationsComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.messages_label
import skyfit.core.ui.generated.resources.refresh_action

class ConversationsScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<ConversationsViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> ConversationsExpanded(viewModel)
            else -> ConversationsCompact(viewModel)
        }

        ConversationsCompact(viewModel)
    }
}

@Composable
fun ConversationsExpanded(viewModel: ConversationsViewModel) {

    ConversationsLayout(
        viewModel = viewModel,
        topBar = { //TODO: LARGE TOP BAR
            CompactTopBar(
                title = stringResource(Res.string.messages_label),
                onClickBack =  { viewModel.onAction(ConversationsAction.OnClickBack) }
            )
        }
    )
}

@Composable
fun ConversationsCompact(viewModel: ConversationsViewModel) {

    ConversationsLayout(
        viewModel = viewModel,
        topBar = {
            CompactTopBar(
                title = stringResource(Res.string.messages_label),
                onClickBack =  { viewModel.onAction(ConversationsAction.OnClickBack) }
            )
        }
    )
}

@Composable
private fun ConversationsLayout(
    viewModel: ConversationsViewModel,
    topBar: @Composable () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            ConversationsEffect.NavigateBack -> navigator.pop()
            ConversationsEffect.NavigateToChat -> navigator.push(ChatScreen())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(topBar = topBar) { paddingValues ->
        when (val state = uiState) {
            is ConversationsUiState.Loading -> FullScreenLoaderContent()
            is ConversationsUiState.Error -> ErrorScreen(
                message = state.message,
                confirmText = stringResource(Res.string.refresh_action),
                onConfirm = viewModel::loadData
            )

            is ConversationsUiState.Content -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ConversationsComponent.ActiveUsersRow()
                    ConversationsComponent.Content(state, viewModel::onAction)
                }
            }
        }
    }
}
