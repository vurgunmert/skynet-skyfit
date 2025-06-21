package com.vurgun.skyfit.feature.connect.chatbot.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_lightning

class ChatBotScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<ChatbotViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> ChatBotExpanded(viewModel)
            else -> ChatbotCompact(viewModel)
        }
    }
}

internal object ChatBotComponent {

    @Composable
    fun ChatHistoryItem(text: String, onClick: () -> Unit) {
        Row(
            Modifier
                .clip(RoundedCornerShape(20.dp))
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
}