package com.vurgun.skyfit.core.ui.components.text.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_phone

@Composable
fun PhoneNumberTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    onKeyboardGoAction: () -> Unit = {}
) {
    val hint = "(987) 654-32-10"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_phone),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = SkyFitColor.icon.default
        )

        Text(
            text = "+90",
            style = SkyFitTypography.bodyMediumRegular,
            color = if (isEnabled) SkyFitColor.text.default else SkyFitColor.text.disabled
        )

        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged { if (it.isFocused && value.isNotEmpty()) focusRequester.freeFocus() },
            value = value,
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }.take(10)
                onValueChange(digitsOnly)
            },
            enabled = isEnabled,
            textStyle = SkyFitTypography.bodyMediumRegular
                .copy(color = if (isEnabled) SkyFitColor.text.default else SkyFitColor.text.disabled),
            singleLine = true,
            visualTransformation = PhoneNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(onGo = { onKeyboardGoAction() }),
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    SecondaryMediumText(hint)
                } else {
                    innerTextField()
                }
            },
            cursorBrush = SolidColor(SkyFitColor.text.default),
        )
    }
}

// Formatter for (XXX) XXX-XX-XX phone number formatting
private class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.filter { it.isDigit() }.take(10)

        var out = if (trimmed.isNotEmpty()) "(" else ""

        for (i in trimmed.indices) {
            if (i == 3) out += ") "
            if (i == 6) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> offset
                in 1..3 -> offset + 1 // Add 1 for (
                in 4..6 -> offset + 3 // Add 3 for ) and space
                else -> offset + 4 // Add 4 for ) space and -
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                0 -> offset
                in 1..5 -> offset - 1 // Remove (
                in 6..10 -> offset - 3 // Remove ) space
                else -> offset - 4 // Remove ) space and -
            }
    }
}
