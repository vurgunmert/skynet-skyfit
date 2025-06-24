package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun SkyInputTextField(
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    nextFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val actualModifier = if (!enabled) {
        modifier
            .pointerInput(Unit) {} // Prevent touch
            .focusable(false)      // Prevent keyboard/tab focus
    } else {
        modifier
    }

    BasicTextField(
        modifier = actualModifier
            .focusRequester(focusRequester),
        enabled = enabled,
        value = value.orEmpty(),
        onValueChange = { onValueChange?.invoke(it) },
        textStyle = SkyFitTypography.bodyMediumRegular.copy(
            color = SkyFitColor.text.default,
        ),
        singleLine = singleLine,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { nextFocusRequester?.requestFocus() },
            onDone = { keyboardController?.hide() }
        ),
        cursorBrush = SolidColor(SkyFitColor.icon.default),
        decorationBox = { innerTextField ->
            if (value.isNullOrBlank()) {
                SkyText(
                    text = hint,
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun SkyDigitInputTextField(
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val actualModifier = if (!enabled) {
        modifier
            .pointerInput(Unit) {} // Eats all gestures
            .focusable(false)      // Prevents keyboard/tab focus
    } else {
        modifier
    }

    BasicTextField(
        modifier = actualModifier,
        enabled = enabled,
        value = value.orEmpty(),
        onValueChange = { rawInput ->
            val digitsOnly = rawInput.filter { it.isDigit() }
            val safeValue = if (digitsOnly.isBlank()) "0" else digitsOnly
            onValueChange?.invoke(safeValue)
        },
        textStyle = SkyFitTypography.bodyMediumRegular.copy(
            color = SkyFitColor.text.default
        ),
        singleLine = singleLine,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        cursorBrush = SolidColor(SkyFitColor.icon.default),
        decorationBox = { innerTextField ->
            if (value.isNullOrBlank() || value == "0") {
                SkyText(
                    text = hint,
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
            innerTextField()
        }
    )
}


@Composable
fun NumberInputFormField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number, // Number | Decimal
    imeAction: ImeAction = ImeAction.Next,
    onNext: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = { raw ->
            val filtered = when (keyboardType) {
                KeyboardType.Number -> raw.filter { it.isDigit() }

                KeyboardType.Decimal -> {
                    var dotUsed = false
                    buildString {
                        raw.forEach { c ->
                            when {
                                c.isDigit() -> append(c)
                                c == '.' && !dotUsed -> {
                                    append('.')
                                    dotUsed = true
                                }
                            }
                        }
                    }
                }

                else -> raw
            }
            onValueChange(filtered)
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        textStyle = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.default),
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext?.invoke() ?: focusRequester.freeFocus()
            },
            onDone = { keyboardController?.hide() }
        ),
        cursorBrush = SolidColor(SkyFitColor.icon.default),
        decorationBox = { innerTextField ->
            if (value.isBlank()) {
                SkyText(
                    text = hint,
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
            innerTextField()
        }
    )
}

