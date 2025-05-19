package com.vurgun.skyfit.feature.connect.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.chat_bot_input_hint
import skyfit.core.ui.generated.resources.ic_send

data class ChatMessageItem(
    val content: String,
    val time: String,
    val isUser: Boolean
)

@Composable
fun SkyFitChatMessageBubble(chatMessage: ChatMessageItem, modifier: Modifier = Modifier) {

    val textColor = if (chatMessage.isUser) SkyFitColor.text.inverse else SkyFitColor.text.default
    val backgroundColor =
        if (chatMessage.isUser) SkyFitColor.border.secondaryButton else SkyFitColor.background.surfaceTertiary

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
fun SkyFitChatMessageInputComponent(
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit
) {
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
                modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
                value = userInput,
                onValueChange = { userInput = it },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.default),
                visualTransformation = VisualTransformation.None,
                decorationBox = { innerTextField ->
                    if (userInput.isBlank()) {
                        SkyText(
                            text = stringResource(Res.string.chat_bot_input_hint),
                            styleType = TextStyleType.BodySmall,
                            color = SkyFitColor.text.secondary
                        )
                    } else {
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
            )
            Spacer(Modifier.width(8.dp))
            SkyFitChatSendIconButton(
                enabled = enabled,
                onClick = {
                    onSend(userInput)
                    userInput = ""
                })
        }
    }
}

@Composable
private fun SkyFitChatSendIconButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    if (enabled) {
        PrimaryIconButton(
            painter = painterResource(Res.drawable.ic_send),
            modifier = Modifier.size(36.dp),
            onClick = onClick
        )
    } else {
        SecondaryIconButton(
            painter = painterResource(Res.drawable.ic_send),
            modifier = Modifier.size(36.dp),
            onClick = null
        )
    }

}