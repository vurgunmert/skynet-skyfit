package com.vurgun.skyfit.presentation.shared.components.text.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_lock
import skyfit.composeapp.generated.resources.ic_visibility_hide
import skyfit.composeapp.generated.resources.ic_visibility_show

@Composable
fun PasswordTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Şifrenizi girin",
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    error: String? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onKeyboardNextAction: (() -> Unit)? = null,
    onKeyboardDoneAction: (() -> Unit)? = null
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }

    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive }
        ?: SkyFitColor.background.surfaceSecondary

    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = CircleShape)
                .clickable {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_lock),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
                value = value,
                onValueChange = onValueChange,
                enabled = isEnabled,
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = if (isEnabled) SkyFitColor.text.default else SkyFitColor.text.disabled
                ),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = if (onKeyboardDoneAction == null) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { onKeyboardNextAction?.invoke() },
                    onDone = { onKeyboardDoneAction?.invoke() }
                ),
                decorationBox = { innerTextField ->
                    if (value.isBlank() && !isFocused) {
                        Text(text = hint, style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary))
                    } else {
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(SkyFitColor.text.default),
            )

            Icon(
                painter = painterResource(if (isPasswordVisible) Res.drawable.ic_visibility_hide else Res.drawable.ic_visibility_show),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { isPasswordVisible = !isPasswordVisible },
                tint = SkyFitColor.icon.default
            )
        }

        error?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = error,
                style = SkyFitTypography.bodySmallSemibold,
                color = SkyFitColor.text.criticalOnBgFill
            )
        }
    }
}
