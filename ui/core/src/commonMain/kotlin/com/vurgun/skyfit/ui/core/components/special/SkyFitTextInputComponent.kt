package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun SkyFitTextInputComponent(
    hint: String,
    value: String? = null,
    error: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    rightIconComposed: (@Composable () -> Unit)? = null,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onKeyboardGoAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive } ?: SkyFitColor.background.surfaceSecondary

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = CircleShape)
                .padding(vertical = 18.dp, horizontal = 16.dp)
        ) {

            leftIconPainter?.let {
                Icon(
                    painter = leftIconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
                Spacer(Modifier.width(8.dp))
            }

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { if (it.isFocused && value?.isNotEmpty() == true) focusRequester.freeFocus() },
                enabled = isEnabled,
                value = value.orEmpty(),
                onValueChange = { onValueChange?.invoke(it) },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(color = if (isEnabled) SkyFitColor.text.default else SkyFitColor.text.secondary),
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(onGo = { onKeyboardGoAction() }),
                decorationBox = { innerTextField ->
                    if (value.isNullOrBlank()) {
                        Text(text = hint, color = SkyFitColor.text.default)
                    } else {
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
            )

            if (rightIconComposed != null) {
                Spacer(Modifier.width(8.dp))
                rightIconComposed.invoke()
            }

            rightIconPainter?.let {
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = rightIconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
            }
        }
        error?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = error,
                style = SkyFitTypography.bodySmallSemibold
            )
        }
    }
}
