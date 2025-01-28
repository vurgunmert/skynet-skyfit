package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

data class ChatMessageItem(
    val content: String,
    val time: String,
    val isUser: Boolean
)

@Composable
fun SkyFitChatMessageBubble(chatMessage: ChatMessageItem, modifier: Modifier = Modifier) {

    val textColor = if (chatMessage.isUser) SkyFitColor.text.inverse else SkyFitColor.text.default
    val backgroundColor = if (chatMessage.isUser) SkyFitColor.border.secondaryButton else SkyFitColor.background.surfaceTertiary

    val userShape = RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp)
    val responderShape = RoundedCornerShape(bottomStart = 0.dp, topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp)

    val senderShape = if (chatMessage.isUser) userShape else responderShape
    val senderAlign = if (chatMessage.isUser) Alignment.TopEnd else Alignment.TopStart
    val columnAlign = if (chatMessage.isUser) Alignment.End else Alignment.Start

    Box(modifier.fillMaxWidth(), contentAlignment = senderAlign) {

        Column(horizontalAlignment = columnAlign) {
            Box(
                modifier = Modifier.background(
                    color = backgroundColor,
                    shape = senderShape
                ).padding(vertical = 12.dp, horizontal = 22.dp)

            ) {
                Text(
                    text = chatMessage.content,
                    style = SkyFitTypography.bodySmall,
                    color = textColor
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = chatMessage.time,
                style = SkyFitTypography.bodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }
}


@Composable
fun SkyFitChatMessageInputComponent(modifier: Modifier = Modifier, onSend: (String) -> Unit) {
    var userInput by remember { mutableStateOf("") }

    Box(
        modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(50))
            .padding(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                value = userInput,
                onValueChange = { userInput = it },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.default),
                visualTransformation = VisualTransformation.None,
                decorationBox = { innerTextField ->
                    if (userInput.isBlank()) {
                        Text(text = "Type a message...", color = SkyFitColor.text.secondary)
                    } else {
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
            )
            Spacer(Modifier.width(8.dp))
            SkyFitChatSendIconButton(Modifier, onClick = {
                onSend(userInput)
                userInput = ""
            })
        }
    }
}

@Composable
private fun SkyFitChatSendIconButton(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier
            .size(36.dp)
            .background(SkyFitColor.specialty.buttonBgRest, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Send",
            tint = SkyFitColor.icon.inverseSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}