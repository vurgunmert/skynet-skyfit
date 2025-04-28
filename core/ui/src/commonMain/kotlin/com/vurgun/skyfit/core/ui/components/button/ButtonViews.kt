package com.vurgun.skyfit.core.ui.components.button

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent

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
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    SkyFitButtonComponent(
        text = text,
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}

@Composable
fun PrimaryMediumButton(
    text: String,
    modifier: Modifier = Modifier.wrapContentWidth(),
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    SkyFitButtonComponent(
        text = text,
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Medium,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}


@Composable
fun SecondaryMediumButton(
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
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Medium,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}


@Composable
fun SecondaryMicroButton(
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
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Micro,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}

@Composable
fun PrimaryDialogButton(
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
        size = ButtonSize.MediumDialog,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}

@Composable
fun SecondaryDialogButton(
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
        variant = ButtonVariant.Secondary,
        size = ButtonSize.MediumDialog,
        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
        modifier = modifier,
        isEnabled = isEnabled,
        leftIconPainter = leftIconPainter,
        rightIconPainter = rightIconPainter
    )
}