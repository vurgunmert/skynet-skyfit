package com.vurgun.skyfit.feature.messaging.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.connect.chatbot.ChatbotViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_chatbot
import skyfit.core.ui.generated.resources.body_scan_fill_semi_left
import skyfit.core.ui.generated.resources.carbon_report_semi_left
import skyfit.core.ui.generated.resources.chatbot_shortcut_body_analysis_left
import skyfit.core.ui.generated.resources.chatbot_shortcut_meal_report_left
import skyfit.core.ui.generated.resources.ic_lightning
import skyfit.core.ui.generated.resources.logo_skyfit

class UserChatBotScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ChatbotViewModel>()

        MobileUserChatBotScreen(
            goToBack = { navigator.pop() },
            goToPostureAnalysis = { navigator.push(SharedScreen.PostureAnalysis) },
            goToChatBot = { },
            viewModel = viewModel
        )
    }
}

@Composable
private fun MobileUserChatBotScreen(
    goToBack: () -> Unit,
    goToPostureAnalysis: () -> Unit,
    goToChatBot: () -> Unit,
    viewModel: ChatbotViewModel
) {

    val showIntro = viewModel.isIntroEnabled.collectAsState().value

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Chatbot", onClickBack = goToBack)
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            MobileUserChatBotScreenBackgroundComponent()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                MobileUserChatBotScreenLogoComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                if (showIntro) {
                    MobileUserChatBotScreenIntroComponent(onComplete = viewModel::completeIntro)
                } else {
                    MobileUserChatBotScreenActionGroupComponent(
                        onClickShortcut = goToPostureAnalysis,
                        onClickChatHistory = goToChatBot,
                        onClickChat = goToChatBot
                    )
                }
            }
        }
    }
}

@Composable
private fun MobileUserChatBotScreenBackgroundComponent() {
    Image(
        painter = painterResource(Res.drawable.background_chatbot),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
private fun MobileUserChatBotScreenLogoComponent(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val size = min(maxWidth, maxHeight) * 0.7f
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Logo",
            modifier = Modifier.size(size)
        )
    }
}


@Composable
private fun MobileUserChatBotScreenIntroComponent(onComplete: () -> Unit) {
    val viewModel = remember { ChatBotOnboardingViewModel(onboardCompleted = onComplete) }
    val currentPage by viewModel.currentPage
    val pageData = viewModel.currentPageData

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillSemiTransparent, RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
            .padding(36.dp)
    ) {

        Text(
            text = pageData.title,
            color = Color.White,
            style = SkyFitTypography.heading4
        )
        Text(
            text = pageData.message,
            color = Color.White,
            style = SkyFitTypography.bodyLargeMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(Modifier.height(116.dp))
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

            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(),
                text = pageData.buttonLabel,
                onClick = viewModel::nextPage,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium
            )
        }
    }
}

@Composable
private fun MobileUserChatBotScreenActionGroupComponent(
    onClickShortcut: () -> Unit,
    onClickChatHistory: () -> Unit,
    onClickChat: () -> Unit
) {
    val historyItems = listOf(
        "Bana uygun kişisel bir antrenman planı oluştur. Hedefim \"kilo vermek.\"",
        "Günlük beslenme alışkanlıklarımı iyileştirmek için birkaç ipucu verir misin?",
        "Bana üst vücut kası yapmak için bir antrenman programı hazırla."
    )

    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.default.copy(0.72f), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(24.dp)
    ) {
        Text(
            text = "Kısayollar",
            style = SkyFitTypography.heading4
        )
        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
        ) {
            ShortcutCardBox(
                Modifier.weight(1f),
                leftRes = Res.drawable.chatbot_shortcut_body_analysis_left,
                rightRes = Res.drawable.body_scan_fill_semi_left,
                title = "Vücut Analizi",
                onClick = onClickShortcut
            )

            ShortcutCardBox(
                Modifier.weight(1f),
                leftRes = Res.drawable.chatbot_shortcut_meal_report_left,
                rightRes = Res.drawable.carbon_report_semi_left,
                title = "Beslenme Raporu",
                onClick = onClickShortcut
            )
        }


        if (historyItems.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Chat Geçmişi",
                style = SkyFitTypography.heading4,
            )

            historyItems.forEach {
                Spacer(Modifier.height(16.dp))
                MobileUserChatBotScreenChatHistoryItemComponent(it, onClick = onClickChatHistory)
            }
        }

        Spacer(Modifier.height(16.dp))
        MobileUserChatBotScreenNewChatAction(
            onClick = onClickChat
        )
    }
}

@Composable
private fun MobileUserChatBotScreenChatHistoryItemComponent(text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentActive, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_lightning),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(16.dp))

        Text(
            text,
            style = SkyFitTypography.bodySmall,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun MobileUserChatBotScreenNewChatAction(onClick: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(48.dp).background(SkyFitColor.specialty.buttonBgRest, CircleShape), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "New Chat",
                tint = SkyFitColor.icon.inverseSecondary,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "New Chat",
            style = SkyFitTypography.bodyXSmall
        )
    }
}


@Composable
fun ShortcutCardBox(
    modifier: Modifier = Modifier,
    leftRes: DrawableResource,
    rightRes: DrawableResource,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.aspectRatio(1f).clickable(onClick = onClick),
        shape = RoundedCornerShape(20),
        backgroundColor = SkyFitColor.specialty.buttonBgRest
    ) {

        Box {
            Row(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(leftRes),
                    contentDescription = "Left Image",
                    modifier = Modifier
                        .weight(0.5f, fill = true)
                        .fillMaxHeight(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(rightRes),
                    contentDescription = "Right Image",
                    modifier = Modifier
                        .weight(0.5f, fill = true)
                        .fillMaxHeight(),
                    contentScale = ContentScale.FillBounds
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