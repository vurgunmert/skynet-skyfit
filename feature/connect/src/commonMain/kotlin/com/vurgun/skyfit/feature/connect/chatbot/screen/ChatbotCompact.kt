package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotAction
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotEffect
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import com.vurgun.skyfit.feature.connect.chatbot.screen.ChatBotExpandedComponent.Onboarding
import com.vurgun.skyfit.feature.connect.chatbot.component.ChatbotAppLogo
import com.vurgun.skyfit.feature.connect.chatbot.component.ChatbotBackground
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.chatbot_label

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
                ActionGroup(
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
                Onboarding(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onComplete = {
                        viewModel.onAction(ChatBotAction.OnClickStart)
                    }
                )
            }
        }
    }
}