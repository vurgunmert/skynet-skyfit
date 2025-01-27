package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.Message
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject


@Composable
fun MobileUserToBotChatScreen(navigator: Navigator) {

    val viewModel: ChatbotViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            ChatInput(
                onSend = { userInput -> viewModel.sendQuery(userInput) }
            )
        }
    }
}

@Composable
fun ChatBubble(message: Message) {

    val textColor = if (message.isUser) SkyFitColor.text.inverse else SkyFitColor.text.default
    val backgroundColor = if (message.isUser) SkyFitColor.border.secondaryButton else SkyFitColor.background.surfaceTertiary

    val userShape = RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp)
    val responderShape = RoundedCornerShape(bottomStart = 0.dp, topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = if (message.isUser) userShape else responderShape
                )
                .padding(16.dp)
        ) {

            Text(
                text = message.text,
                modifier = Modifier,
                style = SkyFitTypography.bodySmall,
                color = textColor
            )
        }
    }
}

@Composable
fun ChatInput(onSend: (String) -> Unit) {
    var userInput by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message...") }
        )
        IconButton(onClick = {
            if (userInput.isNotBlank()) {
                onSend(userInput)
                userInput = ""
            }
        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
        }
    }
}