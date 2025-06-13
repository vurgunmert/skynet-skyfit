package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.connect.chatbot.component.ChatbotAppLogo
import com.vurgun.skyfit.feature.connect.chatbot.component.ChatbotBackground
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotAction
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotEffect
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotOnboardingViewModel
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun ChatbotCompact(viewModel: ChatbotViewModel) {
    val onboardingCompleted by viewModel.onboardingCompleted.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            ChatBotEffect.Dismiss ->
                navigator.pop()

            ChatBotEffect.NavigateToPostureAnalysis ->
                navigator.push(SharedScreen.PostureAnalysis)

            is ChatBotEffect.NavigateChat ->
                navigator.push(ChatWithBotScreen(presetQuery = effect.presetQuery))
        }
    }

    Scaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.chatbot_label),
                onClickBack = { viewModel.onAction(ChatBotAction.OnClickBack) })
        },
        bottomBar = {

        }
    ) {
        Box(Modifier.fillMaxSize()) {
            ChatbotBackground()

            ChatbotAppLogo(
                modifier = Modifier.align(Alignment.TopCenter)
                    .sizeIn(minWidth = 90.dp, minHeight = 90.dp, maxWidth = 240.dp, maxHeight = 240.dp)
                    .aspectRatio(1f)
                    .padding(top = 50.dp)
            )

            if (onboardingCompleted) {
                ChatbotCompactComponent.ActionGroup(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onClickShortcut = {
                        viewModel.onAction(ChatBotAction.OnClickPostureAnalysis)
                    },
                    onClickChatHistory = {
                        viewModel.onAction(ChatBotAction.OnClickNew)
                    },
                    onClickChat = {
                        viewModel.onAction(ChatBotAction.OnClickNew)
                    }
                )
            } else {
                ChatbotCompactComponent.Onboarding(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onComplete = {
                        viewModel.onAction(ChatBotAction.OnClickStart)
                    }
                )
            }
        }
    }
}

private object ChatbotCompactComponent {

    @Composable
    fun Onboarding(
        modifier: Modifier = Modifier,
        onComplete: () -> Unit
    ) {
        val viewModel = remember { ChatBotOnboardingViewModel(onboardCompleted = onComplete) }
        val currentPage by viewModel.currentPage
        val pageData = viewModel.currentPageData

        Box(
            modifier
                .fillMaxWidth()
                .height(345.dp)
                .background(
                    SkyFitColor.background.fillSemiTransparent,
                    RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(36.dp)
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
                Spacer(Modifier.weight(1f))
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
        onClickShortcut: () -> Unit,
        onClickChatHistory: () -> Unit,
        onClickChat: () -> Unit,
        historyItems: List<String> = emptyList(),
    ) {
        Column(
            modifier.fillMaxWidth()
                .heightIn(min = 345.dp)
                .background(
                    SkyFitColor.background.fillSemiTransparent.copy(0.9f),
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(24.dp)
        ) {
            SkyText(
                text = stringResource(Res.string.shortcuts_label),
                styleType = TextStyleType.Heading4
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
                    onClick = onClickShortcut
                )

//                ShortcutCardBox(
//                    Modifier.weight(1f),
//                    leftRes = Res.drawable.chatbot_shortcut_meal_report_left,
//                    rightRes = Res.drawable.carbon_report_semi_left,
//                    title = "Beslenme Raporu",
//                    onClick = onClickShortcut
//                )
            }


            if (historyItems.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Chat Geçmişi",
                    style = SkyFitTypography.heading4,
                )

                historyItems.forEach {
                    Spacer(Modifier.height(16.dp))
                    ChatBotComponent.ChatHistoryItem(it, onClick = onClickChatHistory)
                }
            }

            Spacer(Modifier.height(16.dp))

            NewChatAction(onClick = onClickChat)
        }
    }

    @Composable
    private fun NewChatAction(onClick: () -> Unit) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryIconButton(
                painter = painterResource(Res.drawable.ic_chat),
                modifier = Modifier.size(48.dp),
                onClick = onClick
            )
            Spacer(Modifier.height(4.dp))
            SkyText(
                text = stringResource(Res.string.new_chat_label),
                styleType = TextStyleType.BodyXSmall
            )
        }
    }


    @Composable
    private fun ShortcutCardBox(
        modifier: Modifier = Modifier,
        leftRes: DrawableResource,
        rightRes: DrawableResource,
        title: String,
        onClick: () -> Unit
    ) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .clip(shape = RoundedCornerShape(20))
                .background(SkyFitColor.specialty.buttonBgRest)
                .sizeIn(minWidth = 100.dp, minHeight = 100.dp, maxWidth = 180.dp, maxHeight = 180.dp)
                .aspectRatio(1f)
        ) {
            Row(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(leftRes),
                    contentDescription = "Left Image",
                    modifier = Modifier
                        .weight(0.5f, fill = true)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(rightRes),
                    contentDescription = "Right Image",
                    modifier = Modifier
                        .weight(0.5f, fill = true)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
                    .align(Alignment.BottomStart)
                    .height(36.dp)
                    .background(
                        color = SkyFitColor.background.default.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                    text = title,
                    color = SkyFitColor.text.default,
                    style = SkyFitTypography.bodyMediumMedium
                )
            }
        }
    }
}