package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun SkyTextField(
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        modifier = modifier,
        value = value.orEmpty(),
        onValueChange = { onValueChange?.invoke(it) },
        textStyle = SkyFitTypography.bodyMediumRegular.copy(
            color = SkyFitColor.text.default
        ),
        singleLine = true,
        visualTransformation = VisualTransformation.None,
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
