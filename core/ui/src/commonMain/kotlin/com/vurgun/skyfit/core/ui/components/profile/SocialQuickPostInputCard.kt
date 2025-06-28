package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_image
import fiwe.core.ui.generated.resources.send_action

@Composable
fun SocialQuickPostInputCard(
    creatorImageUrl: String? = null,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp)),
    onClickSend: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current // For closing keyboard

    Row(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Image
        NetworkImage(
            imageUrl = creatorImageUrl,
            modifier = Modifier
                .size(36.dp)
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
            },
            cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest)
        )

        FeatureVisible(false) {
            Icon(
                painter = painterResource(Res.drawable.ic_image),
                contentDescription = "Image Picker",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(20.dp)
            )
        }

        if (textFieldValue.count() > 6) {
            Spacer(Modifier.width(16.dp))
            SkyButton(
                label = stringResource(Res.string.send_action),
                size = SkyButtonSize.Micro,
                onClick = {
                    onClickSend(textFieldValue.trim())
                    textFieldValue = ""
                }
            )
        }
    }
}