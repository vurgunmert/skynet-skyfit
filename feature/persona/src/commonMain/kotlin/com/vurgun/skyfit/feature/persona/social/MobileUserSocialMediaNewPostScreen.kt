package com.vurgun.skyfit.feature.persona.social

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
fun MobileUserSocialMediaNewPostScreen(
    goToBack: () -> Unit
) {

    val isSendEnabled by remember { mutableStateOf(true) }
    val keyboardState by keyboardAsState()

    SkyFitMobileScaffold(
        topBar = {
            MobileUserSocialMediaNewPostScreenToolbarComponent(
                onClickCancel = goToBack,
                isSendEnabled = isSendEnabled,
                onClickSend = {}
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            MobileUserSocialMediaNewPostScreenInputComponent(
                onClickSend = {}
            )

            if (keyboardState.heightPx > 0) {
                MobileUserSocialMediaNewPostActionGroupComponent(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = keyboardState.heightDp),
                    onClickAddVoice = { },
                    onClickAddImage = { },
                    onClickAddLocation = { },
                    onClickAddFile = { }
                )
            }
        }
    }
}

@Composable
private fun MobileUserSocialMediaNewPostScreenToolbarComponent(
    onClickCancel: () -> Unit,
    isSendEnabled: Boolean = false,
    onClickSend: () -> Unit
) {

    Row(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "İptal",
            style = SkyFitTypography.bodyLarge,
            modifier = Modifier
                .clickable(onClick = onClickCancel)
        )

        Spacer(Modifier.weight(1f))

        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Paylaş",
            onClick = onClickSend,
            size = ButtonSize.Micro,
            isEnabled = isSendEnabled
        )
    }
}

@Composable
private fun MobileUserSocialMediaNewPostScreenInputComponent(
    onClickSend: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current // For closing keyboard

    Row(
        Modifier.fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        // User Profile Image
        NetworkImage(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))

        // TextField - No Background, No Underline, Expands Vertically
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
                    if (textFieldValue.isNotBlank()) { // Only send if text is not empty
                        onClickSend(textFieldValue.trim())
                        textFieldValue = "" // Clear input
                        keyboardController?.hide() // Close keyboard ✅
                    }
                }
            ),
            singleLine = false, // Allows multi-line input
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
    }
}


@Composable
private fun MobileUserSocialMediaNewPostActionGroupComponent(
    modifier: Modifier,
    onClickAddVoice: () -> Unit,
    onClickAddImage: () -> Unit,
    onClickAddLocation: () -> Unit,
    onClickAddFile: () -> Unit,
) {

    Row(
        modifier.fillMaxWidth()
            .border(1.dp, SkyFitColor.border.default)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Voice",
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(24.dp).clickable(onClick = onClickAddVoice)
        )
        Spacer(Modifier.width(17.dp))
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Image",
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(24.dp).clickable(onClick = onClickAddImage)
        )
        Spacer(Modifier.width(17.dp))
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Location",
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(24.dp).clickable(onClick = onClickAddLocation)
        )
        Spacer(Modifier.weight(1f))
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "+",
            onClick = onClickAddFile,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro
        )
    }
}