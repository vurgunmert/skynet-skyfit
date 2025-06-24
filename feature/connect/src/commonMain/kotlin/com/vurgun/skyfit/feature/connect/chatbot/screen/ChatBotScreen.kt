package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotMessage
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_lightning

class ChatBotScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<ChatbotViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadData(windowSize)
        }

        when (windowSize) {
            WindowSize.EXPANDED -> ChatBotExpanded(viewModel)
            else -> ChatbotCompact(viewModel)
        }
    }
}

internal object ChatBotComponent {

    @Composable
    fun ChatHistoryItem(message: ChatbotMessage, onClick: () -> Unit) {
        Row(
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentActive, RoundedCornerShape(20.dp))
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            SkyIcon(
                res = Res.drawable.ic_lightning,
                size = SkyIconSize.Small,
                modifier = Modifier.padding(2.dp)
            )

            Spacer(Modifier.width(12.dp))

            SkyText(
                text = message.content,
                styleType = TextStyleType.BodySmall,
                modifier = Modifier.weight(1f)
            )
        }
    }
}