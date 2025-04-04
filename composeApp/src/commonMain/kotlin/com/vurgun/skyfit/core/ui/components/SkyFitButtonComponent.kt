package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun SkyFitButtonComponent(
    text: String,
    size: ButtonSize = ButtonSize.Large,
    variant: ButtonVariant = ButtonVariant.Primary,
    state: ButtonState = ButtonState.Rest,
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth(),
    maxLines: Int = 1,
    onClick: () -> Unit
) {

    val buttonStyle = getButtonStyle(variant, if (isEnabled) state else ButtonState.Disabled)
    val buttonTextStyle = when (size) {
        ButtonSize.Large -> SkyFitTypography.bodyLargeMedium
        ButtonSize.Medium -> SkyFitTypography.bodyMediumMedium
        ButtonSize.MediumDialog -> SkyFitTypography.bodyMediumMedium
        ButtonSize.Micro -> SkyFitTypography.bodyMediumMedium
    }

    Button(
        onClick = { if (state != ButtonState.Disabled && state != ButtonState.Loading) onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonStyle.backgroundColor,
            contentColor = buttonStyle.foregroundColor
        ),
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(1.dp, buttonStyle.borderColor),
        enabled = isEnabled || state != ButtonState.Disabled
    ) {
        if (state == ButtonState.Loading) {
            Row(
                modifier = Modifier.padding(
                    start = size.paddingStart,
                    end = size.paddingEnd,
                    top = size.paddingVertical,
                    bottom = size.paddingVertical
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = buttonStyle.foregroundColor,
                    modifier = Modifier.size(size.iconSize),
                    strokeWidth = 2.dp
                )
            }
        } else {

            Row(
                modifier = Modifier.padding(
                    start = size.paddingStart,
                    end = size.paddingEnd,
                    top = size.paddingVertical,
                    bottom = size.paddingVertical
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                leftIconPainter?.let {
                    Icon(
                        painter = leftIconPainter,
                        contentDescription = null,
                        modifier = Modifier.size(size.iconSize),
                        tint = buttonStyle.foregroundColor
                    )
                    Spacer(Modifier.width(size.iconPadding))
                }

                Text(
                    text = text,
                    style = buttonTextStyle,
                    color = buttonStyle.foregroundColor,
                    maxLines = 1
                )

                rightIconPainter?.let {
                    Spacer(Modifier.width(size.iconPadding))
                    Icon(
                        painter = rightIconPainter,
                        contentDescription = null,
                        modifier = Modifier.size(size.iconSize),
                        tint = buttonStyle.foregroundColor
                    )
                }
            }

        }
    }
}

sealed class ButtonSize(
    val paddingStart: Dp,
    val paddingEnd: Dp,
    val paddingVertical: Dp,
    val iconPadding: Dp,
    val iconSize: Dp
) {
    data object Large : ButtonSize(24.dp, 32.dp, 8.dp, 8.dp, 20.dp)
    data object Medium : ButtonSize(20.dp, 24.dp, 6.dp, 8.dp, 16.dp)
    data object MediumDialog : ButtonSize(8.dp, 8.dp, 6.dp, 8.dp, 16.dp)
    data object Micro : ButtonSize(16.dp, 12.dp, 4.dp, 4.dp, 16.dp)
}

enum class ButtonVariant {
    Primary, Secondary, Plain
}

enum class ButtonState { Rest, Hover, Active, Pressed, Disabled, Loading }


private fun getButtonStyle(variant: ButtonVariant, state: ButtonState): ButtonStyle {
    return when (variant) {
        ButtonVariant.Primary -> getPrimaryButtonStyle(state)
        ButtonVariant.Secondary -> getSecondaryButtonStyle(state)
        ButtonVariant.Plain -> getPlainButtonStyle(state)
    }
}

private data class ButtonStyle(
    val foregroundColor: Color,
    val backgroundColor: Color,
    val borderColor: Color
)

// PRIMARY BUTTON
private fun getPrimaryButtonStyle(state: ButtonState): ButtonStyle = when (state) {
    ButtonState.Rest -> ButtonStyle(SkyFitColor.text.inverse, SkyFitColor.specialty.buttonBgRest, SkyFitColor.transparent)
    ButtonState.Hover -> ButtonStyle(SkyFitColor.text.inverse, SkyFitColor.specialty.buttonBgHover, SkyFitColor.transparent)
    ButtonState.Active -> ButtonStyle(SkyFitColor.text.inverse, SkyFitColor.specialty.buttonBgActive, SkyFitColor.border.secondary)
    ButtonState.Pressed -> ButtonStyle(SkyFitColor.text.inverse, SkyFitColor.specialty.buttonBgPressed, SkyFitColor.border.default)
    ButtonState.Disabled -> ButtonStyle(SkyFitColor.text.disabled, SkyFitColor.specialty.buttonBgDisabled, SkyFitColor.specialty.buttonBgDisabled)
    ButtonState.Loading -> ButtonStyle(SkyFitColor.icon.disabled, SkyFitColor.specialty.buttonBgLoading, SkyFitColor.icon.disabled)
}

// SECONDARY BUTTON
private fun getSecondaryButtonStyle(state: ButtonState): ButtonStyle = when (state) {
    ButtonState.Rest -> ButtonStyle(SkyFitColor.text.default, SkyFitColor.transparent, SkyFitColor.border.secondaryButton)
    ButtonState.Hover -> ButtonStyle(
        SkyFitColor.text.secondary,
        SkyFitColor.specialty.secondaryButtonRest,
        SkyFitColor.border.secondaryButtonHover
    )

    ButtonState.Active -> ButtonStyle(SkyFitColor.text.default, SkyFitColor.specialty.secondaryButtonRest, SkyFitColor.border.focus)
    ButtonState.Pressed -> ButtonStyle(SkyFitColor.text.secondary, SkyFitColor.specialty.secondaryButtonRest, SkyFitColor.border.default)
    ButtonState.Disabled -> ButtonStyle(
        SkyFitColor.text.disabled,
        SkyFitColor.specialty.secondaryButtonRest,
        SkyFitColor.border.secondaryButtonDisabled
    )

    ButtonState.Loading -> ButtonStyle(
        SkyFitColor.icon.disabled,
        SkyFitColor.specialty.secondaryButtonRest,
        SkyFitColor.border.secondaryButtonHover
    )
}

// PLAIN BUTTON
private fun getPlainButtonStyle(state: ButtonState): ButtonStyle = when (state) {
    ButtonState.Rest -> ButtonStyle(SkyFitColor.text.linkInverse, SkyFitColor.transparent, SkyFitColor.transparent)
    ButtonState.Hover -> ButtonStyle(SkyFitColor.text.linkHover, SkyFitColor.transparent, SkyFitColor.transparent)
    ButtonState.Disabled -> ButtonStyle(SkyFitColor.text.disabled, SkyFitColor.transparent, SkyFitColor.transparent)
    else -> ButtonStyle(SkyFitColor.text.linkInverse, SkyFitColor.transparent, SkyFitColor.transparent)
}