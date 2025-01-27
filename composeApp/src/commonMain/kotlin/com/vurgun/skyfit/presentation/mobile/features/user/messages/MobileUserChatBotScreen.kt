package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

private enum class MobileUserChatBotScreenStep {
    INTRO,
    SHORTCUT,
    CHAT
}

@Composable
fun MobileUserChatBotScreen(navigator: Navigator) {

    val viewModel: ChatbotViewModel = koinInject()
    val step = MobileUserChatBotScreenStep.CHAT

    SkyFitScaffold {
        Box {
            when(step){
                MobileUserChatBotScreenStep.INTRO -> {
                    MobileUserChatBotScreenBackgroundComponent()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserChatBotScreenToolbarComponent()

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenLogoComponent()

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenIntroComponent()
                    }
                }
                MobileUserChatBotScreenStep.SHORTCUT -> {
                    MobileUserChatBotScreenBackgroundComponent()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MobileUserChatBotScreenToolbarComponent()

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenLogoComponent()

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenShortCutComponent()
                    }

                }
                MobileUserChatBotScreenStep.CHAT -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SkyFitScreenHeader("Chatbot", onBackClick = {})

                        Spacer(Modifier.weight(1f))

                        MobileUserChatBotScreenChatsComponent()

                        Spacer(Modifier.height(44.dp))

                        MobileUserChatBotScreenChatInputComponent()
                    }
                }
            }
        }
    }
}

@Composable
private fun MobileUserChatBotScreenBackgroundComponent() {
    TodoBox("MobileUserChatBotScreenBackgroundComponent", Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.1f)))
}

@Composable
private fun MobileUserChatBotScreenToolbarComponent() {
    TodoBox("MobileUserChatBotScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}


@Composable
private fun MobileUserChatBotScreenLogoComponent() {
    TodoBox("MobileUserChatBotScreenLogoComponent", Modifier.size(220.dp, 241.dp))
}

@Composable
private fun MobileUserChatBotScreenIntroComponent() {
    TodoBox("MobileUserChatBotScreenIntroComponent", Modifier.size(430.dp, 345.dp))
}

@Composable
private fun MobileUserChatBotScreenShortCutComponent() {
    TodoBox("MobileUserChatBotScreenShortCutComponent", Modifier.size(430.dp, 670.dp))
}

@Composable
private fun MobileUserChatBotScreenChatsComponent() {
    TodoBox("MobileUserChatBotScreenChatsComponent", Modifier.size(382.dp, 372.dp))
}
@Composable
private fun MobileUserChatBotScreenChatInputComponent() {
    TodoBox("MobileUserChatBotScreenChatInputComponent", Modifier.size(382.dp, 52.dp))
    SkyFitSearchTextInputComponent("Ask anything...")
}