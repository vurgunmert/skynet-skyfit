package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_pencil

@Composable
fun SingleLineInputText(
    title: String,
    hint: String,
    value: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: ((String) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
    rightIconRes: DrawableResource? = null,
    enabled: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive }
        ?: SkyFitColor.background.surfaceSecondary

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(title, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = CircleShape)
                .clickable { focusRequester.requestFocus() }
                .padding(vertical = 18.dp, horizontal = 16.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                enabled = enabled,
                value = value.orEmpty(),
                onValueChange = { onValueChange?.invoke(it) },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = SkyFitColor.text.default
                ),
                singleLine = true,
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
                        Text(text = hint, color = SkyFitColor.text.secondary)
                    }
                    innerTextField()
                }
            )

            if (rightIconRes != null) {
                Spacer(Modifier.width(8.dp))
                ActionIcon(Res.drawable.ic_pencil) { focusRequester.requestFocus() }
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
