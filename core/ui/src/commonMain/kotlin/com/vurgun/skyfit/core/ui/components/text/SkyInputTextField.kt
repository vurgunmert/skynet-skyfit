package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.foundation.focusable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun SkyInputTextField(
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val actualModifier = if (!enabled) {
        modifier
            .pointerInput(Unit) {} // Prevent touch
            .focusable(false)      // Prevent keyboard/tab focus
    } else {
        modifier
    }

    BasicTextField(
        modifier = actualModifier,
        enabled = enabled,
        value = value.orEmpty(),
        onValueChange = { onValueChange?.invoke(it) },
        textStyle = SkyFitTypography.bodyMediumRegular.copy(
            color = SkyFitColor.text.default,
        ),
        singleLine = singleLine,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default,
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
