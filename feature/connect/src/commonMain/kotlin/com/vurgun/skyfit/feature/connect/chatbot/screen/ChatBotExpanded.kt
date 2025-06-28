package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotMessage
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalExpandedOverlayController
import com.vurgun.skyfit.feature.connect.chatbot.model.*
import com.vurgun.skyfit.feature.connect.component.SkyChatMessageItem
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageBubbleShimmer
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageInputComponent
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

@Composable
internal fun ChatBotExpanded(viewModel: ChatbotViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val navigator = LocalNavigator.currentOrThrow
    val overlayController = LocalExpandedOverlayController.currentOrThrow

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            ChatbotUiEffect.Dismiss ->
                navigator.pop()

            ChatbotUiEffect.NavigateToPostureAnalysis -> {
                overlayController.invoke(SharedScreen.PostureAnalysis)
            }

            is ChatbotUiEffect.NavigateToChat ->
                navigator.push(ChatWithBotScreen(effect.query, effect.sessionId))

            ChatbotUiEffect.NavigateToMealReport -> Unit
        }
    }

    when(val state = uiState) {
        is ChatbotUiState.Content -> {
            ChatBotExpandedComponent.ScreenContent(state, viewModel::onAction)
        }
        is ChatbotUiState.Error -> {
            ErrorScreen(message = state.message) { navigator.pop() }
        }
        ChatbotUiState.Loading -> {
            FullScreenLoaderContent()
        }
    }
}

internal object ChatBotExpandedComponent {

    @Composable
    fun ScreenContent(
        content: ChatbotUiState.Content,
        onAction: (ChatbotUiAction) -> Unit
    ){
        val hazeState = rememberHazeState()
        val hazeStyle = HazeStyle(
            backgroundColor = SkyFitColor.background.surfaceSecondary,
            tints = listOf(
                HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
            ),
            blurRadius = 20.dp,
            noiseFactor = 0f
        )

        Row(modifier = Modifier.fillMaxSize().padding(end = 16.dp, bottom = 16.dp)) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .background(SkyFitColor.background.surfaceTertiary)
            ) {
                if (content.onboardingCompleted) {
                    ActionGroup(
                        modifier = Modifier.fillMaxSize(),
                        onClickPostureAnalysis = { onAction(ChatbotUiAction.OnClickPostureAnalysis) },
                        onClickMealReport = { onAction(ChatbotUiAction.OnClickMealReport) },
                        onSubmitNewQuery = { onAction(ChatbotUiAction.OnClickNewSession(it)) }
                    )
                } else {
                    Onboarding(
                        hazeState, hazeStyle,
                        modifier = Modifier.fillMaxSize(),
                        onComplete = { onAction(ChatbotUiAction.OnClickOnboardingStart) })
                }
            }

            Spacer(Modifier.width(20.dp))

            SessionHistoryGroup(
                messages = content.historyMessages,
                onAction = onAction
            )
        }
    }

    @Composable
    private fun SessionHistoryGroup(
        messages: List<ChatbotMessage>,
        onAction: (ChatbotUiAction) -> Unit
    ) {
        Column(
            modifier = Modifier
                .width(315.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
                .background(SkyFitColor.background.surfaceTertiary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            messages.forEach { message ->
                ChatBotComponent.ChatHistoryItem(message, { onAction(ChatbotUiAction.OnClickSession(message.sessionId)) })
            }

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = stringResource(Res.string.new_chat_label),
                leftIcon = painterResource(Res.drawable.ic_chat),
                onClick = { onAction(ChatbotUiAction.OnClickNewSession(null)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun Onboarding(
        hazeState: HazeState,
        hazeStyle: HazeStyle,
        modifier: Modifier = Modifier,
        onComplete: () -> Unit,
    ) {
        val viewModel = remember { ChatBotOnboardingViewModel(onboardCompleted = onComplete) }
        val currentPage by viewModel.currentPage
        val pageData = viewModel.currentPageData

        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(SkyFitColor.background.surfaceTertiary)
        ) {
            Image(
                painter = painterResource(Res.drawable.background_chatbot),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize()
                    .hazeSource(state = hazeState),
                contentScale = ContentScale.FillWidth,
            )

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .width(583.dp)
                    .wrapContentHeight()
                    .align(Alignment.Center)
                    .hazeEffect(hazeState, hazeStyle)
                    .padding(vertical = 128.dp, horizontal = 40.dp),
            ) {
                SkyText(
                    text = pageData.title,
                    styleType = TextStyleType.Heading4
                )
                Spacer(Modifier.height(16.dp))
                SkyText(
                    text = pageData.message,
                    styleType = TextStyleType.BodyLargeMedium
                )
                Spacer(Modifier.height(65.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (0 until viewModel.pages.size).forEach { index ->
                            Box(
                                modifier = Modifier
                                    .width(if (currentPage == index) 40.dp else 8.dp)
                                    .height(8.dp)
                                    .background(
                                        color = if (index == currentPage) Color.White else Color.Gray,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }

                    SkyButton(
                        label = pageData.buttonLabel,
                        size = SkyButtonSize.Medium,
                        onClick = viewModel::nextPage
                    )
                }
            }
        }
    }

    @Composable
    fun ActionGroup(
        modifier: Modifier = Modifier,
        onClickPostureAnalysis: () -> Unit,
        onClickMealReport: () -> Unit,
        onSubmitNewQuery: (query: String) -> Unit
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .background(
                    SkyFitColor.background.surfaceTertiary,
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(96.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SkyText(
                text = "Nasıl yardımcı olabilirim?",
                styleType = TextStyleType.Heading4
            )
            Spacer(Modifier.height(32.dp))
            SkyFitChatMessageInputComponent(
                modifier = Modifier
                    .padding(bottom = 24.dp),
                onSend = { userInput -> onSubmitNewQuery(userInput) }
            )
            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                ShortcutCardBox(
                    modifier = Modifier,
                    leftRes = Res.drawable.chatbot_shortcut_body_analysis_left,
                    rightRes = Res.drawable.body_scan_fill_semi_left,
                    title = stringResource(Res.string.body_analysis_label),
                    onClick = onClickPostureAnalysis
                )

                FeatureVisible(false) {
                    ShortcutCardBox(
                        Modifier.weight(1f),
                        leftRes = Res.drawable.chatbot_shortcut_meal_report_left,
                        rightRes = Res.drawable.carbon_report_semi_left,
                        title = "Beslenme Raporu",
                        onClick = onClickMealReport
                    )
                }
            }
        }
    }


    @Composable
    private fun ChatBotMessageScreenContent(
        viewModel: ChatWithBotViewModel
    ) {
        val messages by viewModel.messages.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val listState = rememberLazyListState()

        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        Scaffold(
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
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {
                LazyColumn(
                    modifier = Modifier.widthIn(max = 617.dp).fillMaxHeight().padding(16.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(36.dp)
                ) {
                    items(messages) { message ->

                        SkyChatMessageItem(message)
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
                        onSend = { viewModel.onAction(ChatWithBotAction.OnSendQuery(it)) }
                    )
                }
            }
        }
    }
}