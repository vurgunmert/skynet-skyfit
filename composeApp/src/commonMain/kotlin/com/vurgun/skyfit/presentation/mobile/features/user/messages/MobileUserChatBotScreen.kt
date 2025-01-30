package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.background_chatbot
import skyfit.composeapp.generated.resources.compose_multiplatform
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserChatBotScreenStep {
    INTRO,
    SHORTCUT
}

@Composable
fun MobileUserChatBotScreen(navigator: Navigator) {

    val viewModel: ChatbotViewModel = koinInject()
    val step = MobileUserChatBotScreenStep.SHORTCUT

    SkyFitScaffold(
        topBar = {
            SkyFitScreenHeader("Chatbot", onBackClick = { navigator.popBackStack() })
        }
    ) {
        Box(Modifier.fillMaxSize()) {

            MobileUserChatBotScreenBackgroundComponent()

            when (step) {
                MobileUserChatBotScreenStep.INTRO -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserChatBotScreenLogoComponent()

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenIntroComponent()
                    }
                }

                MobileUserChatBotScreenStep.SHORTCUT -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserChatBotScreenLogoComponent()

                        MobileUserChatBotScreenActionGroupComponent(
                            onClickShortcut = {
                                navigator.jumpAndStay(SkyFitNavigationRoute.UserBodyAnalysis)
                            },
                            onClickChatHistory = {
                                navigator.jumpAndStay(SkyFitNavigationRoute.UserToBotChat)
                            },
                            onClickChat = {
                                navigator.jumpAndStay(SkyFitNavigationRoute.UserToBotChat)
                            }
                        )
                    }

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
private fun MobileUserChatBotScreenLogoComponent() {
    Icon(
        painter = painterResource(Res.drawable.logo_skyfit),
        contentDescription = null,
        modifier = Modifier.size(86.dp, 94.dp),
        tint = SkyFitColor.icon.default
    )
}

@Composable
private fun MobileUserChatBotScreenIntroComponent() {
    TodoBox("MobileUserChatBotScreenIntroComponent", Modifier.size(430.dp, 345.dp))
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

        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
        ) {
            item {
                ShortcutCardBox(
                    Modifier,
                    leftRes = Res.drawable.logo_skyfit,
                    rightRes = Res.drawable.compose_multiplatform,
                    title = "Vücut Analizi",
                    onClick = onClickShortcut
                )
            }

            item {
                ShortcutCardBox(
                    Modifier,
                    leftRes = Res.drawable.logo_skyfit,
                    rightRes = Res.drawable.compose_multiplatform,
                    title = "Beslenme Raporu",
                    onClick = onClickShortcut
                )
            }
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
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(30.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
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
        modifier = modifier.size(180.dp).clickable(onClick = onClick),
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