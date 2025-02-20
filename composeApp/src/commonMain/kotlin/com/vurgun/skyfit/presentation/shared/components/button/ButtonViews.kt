package com.vurgun.skyfit.presentation.shared.components.button

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent

@Composable
fun PrimaryLargeButton(
    text: String,
    modifier: Modifier = Modifier.wrapContentWidth(),
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    SkyFitButtonComponent(
        text = text,
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter
    )
}

@Composable
fun SecondaryLargeButton(
    text: String,
    modifier: Modifier = Modifier.wrapContentWidth(),
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    SkyFitButtonComponent(
        text = text,
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        state = ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter
    )
}