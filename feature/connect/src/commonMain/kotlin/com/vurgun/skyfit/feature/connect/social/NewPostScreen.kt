package com.vurgun.skyfit.feature.connect.social

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconButton
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_close_circle
import fiwe.core.ui.generated.resources.share_action

class NewPostScreen : Screen {

    override val key: ScreenKey = SharedScreen.NewPost.key

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val overlayController = LocalCompactOverlayController.current
        val viewModel = koinScreenModel<NewPostViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.uiEffect) { effect ->
            when (effect) {
                NewPostUiEffect.NavigateBack -> {
                    overlayController?.invoke(null)
                    navigator.pop()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (val state = uiState) {
            NewPostUiState.Loading ->
                FullScreenLoaderContent()

            is NewPostUiState.Error ->
                ErrorScreen(message = state.message, onConfirm = {
                    overlayController?.invoke(null)
                    navigator.pop()
                })

            is NewPostUiState.Content -> {
                NewPostCompact(state, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun NewPostCompact(
    content: NewPostUiState.Content,
    onAction: (NewPostUiAction) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    SkyFitMobileScaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkyIconButton(
                    res = Res.drawable.ic_close_circle,
                    onClick = { onAction(NewPostUiAction.OnClickCancel) }
                )

                SkyButton(
                    label = stringResource(Res.string.share_action),
                    size = SkyButtonSize.Medium,
                    onClick = { onAction(NewPostUiAction.OnClickSend(textFieldValue.trim())) },
                    enabled = textFieldValue.isNotEmpty()
                )
            }
        },
        content = {
            Row(
                modifier = Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.Top
            ) {
                SkyImage(
                    url = content.photoImageUrl,
                    size = SkyImageSize.Size32,
                    shape = SkyImageShape.Circle
                )

                Spacer(Modifier.width(8.dp))

                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    modifier = Modifier
                        .padding(top = 8.dp)
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
                                onAction(NewPostUiAction.OnClickSend(textFieldValue.trim()))
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
                                    color = SkyFitColor.text.disabled
                                )
                            }
                            innerTextField()
                        }
                    },
                    cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}