package com.vurgun.skyfit.feature.social.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_image

@Composable
fun SocialQuickPostInputCard(
    modifier: Modifier = Modifier,
    onClickSend: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current // For closing keyboard

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Image
        NetworkImage(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
                .wrapContentHeight(), // Expands when needed
            textStyle = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default),

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (textFieldValue.isNotBlank()) {
                        onClickSend(textFieldValue.trim())
                        textFieldValue = ""
                        keyboardController?.hide()
                    }
                }
            ),
            singleLine = false,
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = "Bugünkü motivasyonunu paylaş",
                            style = SkyFitTypography.bodyLarge,
                            color = SkyFitColor.text.secondary
                        )
                    }
                    innerTextField()
                }
            }
        )

        Icon(
            painter = painterResource(Res.drawable.ic_image),
            contentDescription = "Image Picker",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(20.dp)
        )
    }
}