package com.vurgun.skyfit.designsystem.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_lock
import skyfit.ui.core.generated.resources.ic_visibility_hide
import skyfit.ui.core.generated.resources.ic_visibility_show

@Composable
fun PasswordInputText(
    title: String? = null,
    hint: String,
    value: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    onKeyboardGoAction: () -> Unit = {},
    onValueChange: ((String) -> Unit)? = null
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive } ?: SkyFitColor.background.surfaceSecondary
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        title.takeUnless { it.isNullOrEmpty() }?.let {
            Text(it, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = CircleShape)
                .padding(vertical = 18.dp, horizontal = 16.dp)
        ) {

            Icon(
                painter = painterResource(Res.drawable.ic_lock),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.width(8.dp))

            BasicTextField(
                value = value.orEmpty(),
                onValueChange = { onValueChange?.invoke(it) },
                textStyle = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .padding(horizontal = 4.dp),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { nextFocusRequester?.requestFocus() },
                    onDone = { keyboardController?.hide() }
                ),
                decorationBox = { innerTextField ->
                    if (value.isNullOrEmpty()) {
                        Text(text = hint, style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary))
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(SkyFitColor.text.default)
            )

            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(if (isPasswordVisible) Res.drawable.ic_visibility_hide else Res.drawable.ic_visibility_show),
                contentDescription = null,
                modifier = Modifier.size(16.dp).clickable {
                    isPasswordVisible = !isPasswordVisible
                },
                tint = SkyFitColor.icon.default
            )
        }

        error.takeUnless { it.isNullOrEmpty() }?.let {
            Text(modifier = Modifier.padding(start = 8.dp), text = it, style = SkyFitTypography.bodySmallSemibold)
        }
    }
}