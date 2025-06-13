package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotAction
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotOnboardingViewModel
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import com.vurgun.skyfit.feature.connect.chatbot.screen.ChatBotExpandedComponent.Onboarding
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_chatbot
import skyfit.core.ui.generated.resources.ic_chat

@Composable
internal fun ChatBotExpanded(viewModel: ChatbotViewModel) {

    val hazeState = rememberHazeState()
    val hazeStyle = HazeStyle(
        backgroundColor = SkyFitColor.background.surfaceSecondary,
        tints = listOf(
            HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
        ),
        blurRadius = 20.dp,
        noiseFactor = 0f
    )

    val historyItems = viewModel.historyItems

    Row(modifier = Modifier.fillMaxSize().padding(end = 16.dp, bottom = 16.dp)) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
                .background(SkyFitColor.background.surfaceTertiary)
        ) {
            Onboarding(
                hazeState, hazeStyle,
                modifier = Modifier.fillMaxSize(),
                onComplete = { viewModel.onAction(ChatBotAction.OnClickStart) })
        }

        Spacer(Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .width(315.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
                .background(SkyFitColor.background.surfaceTertiary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            historyItems.forEach {
                ChatBotComponent.ChatHistoryItem(it, { viewModel.onAction(ChatBotAction.OnClickHistoryItem(it)) })
            }

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = "Yeni Sohbet",
                leftIcon = painterResource(Res.drawable.ic_chat),
                onClick = { viewModel.onAction(ChatBotAction.OnClickNew) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

internal object ChatBotExpandedComponent {

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
}