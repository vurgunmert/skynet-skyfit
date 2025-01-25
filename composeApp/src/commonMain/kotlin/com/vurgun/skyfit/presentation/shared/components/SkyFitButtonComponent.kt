package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography

@Composable
fun SkyFitButtonComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    onClick: () -> Unit,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.Large,
    initialState: ButtonState = ButtonState.Rest,
    leftIconPainter: Painter? = null,
    rightIconPainter: Painter? = null,
    isEnabled: Boolean = true
) {

    var state by remember { mutableStateOf(if(isEnabled) initialState else ButtonState.Disabled) }

    // Determine colors and styles based on the button's state and variant
    val backgroundColor = when (state) {
        ButtonState.Pressed -> variant.pressedBackgroundColor
        ButtonState.Disabled -> variant.disabledBackgroundColor
        else -> variant.defaultBackgroundColor
    }

    val textColor = when (state) {
        ButtonState.Pressed -> variant.pressedTextColor
        ButtonState.Disabled -> variant.disabledTextColor
        else -> variant.defaultTextColor
    }

    val borderColor = when (state) {
        ButtonState.Pressed -> variant.pressedBorderColor
        ButtonState.Disabled -> variant.disabledBorderColor
        else -> variant.defaultBorderColor
    }

    // Render the button with dynamic press state handling
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(size.height)
            .border(width = if (borderColor != Color.Transparent) 1.dp else 0.dp, color = borderColor, shape = CircleShape)
            .background(backgroundColor, shape = CircleShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        state = ButtonState.Pressed
                        tryAwaitRelease() // Wait until the press is released
                        state = initialState // Revert to the initial state (Rest or any other specified)
                    },
                    onTap = { if (initialState != ButtonState.Disabled && isEnabled) onClick() }
                )
            }
            .padding(horizontal = size.horizontalPadding, vertical = size.verticalPadding)
    ) {
        if (state == ButtonState.Loading) {
            CircularProgressIndicator(color = textColor, modifier = Modifier.size(16.dp))
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                leftIconPainter?.let {
                    Icon(
                        painter = leftIconPainter,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = textColor
                    )
                    Spacer(Modifier.width(6.dp))
                }

                Text(text, color = textColor, style = SkyFitTypography.bodyLargeMedium)

                rightIconPainter?.let {
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        painter = rightIconPainter,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = textColor
                    )
                }
            }
        }
    }
}


// Supporting Enums and Data Classes
enum class ButtonVariant(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val defaultTextColor: Color,
    val pressedTextColor: Color,
    val disabledTextColor: Color,
    val defaultBorderColor: Color,
    val pressedBorderColor: Color,
    val disabledBorderColor: Color
) {
    Primary(
        defaultBackgroundColor = SkyFitColor.specialty.buttonBgRest,
        pressedBackgroundColor = SkyFitColor.specialty.buttonBgPressed,
        disabledBackgroundColor = SkyFitColor.specialty.buttonBgDisabled,
        defaultTextColor = SkyFitColor.text.inverse,
        pressedTextColor = SkyFitColor.text.inverse,
        disabledTextColor = SkyFitColor.text.disabled,
        defaultBorderColor = SkyFitColor.transparent,
        pressedBorderColor = SkyFitColor.border.default,
        disabledBorderColor = SkyFitColor.transparent,
    ),
    Secondary(
        defaultBackgroundColor = SkyFitColor.background.fillTransparentActive,
        pressedBackgroundColor = SkyFitColor.background.fillTransparentActive,
        disabledBackgroundColor = SkyFitColor.background.fillTransparentActive,
        defaultTextColor = SkyFitColor.text.default,
        pressedTextColor = SkyFitColor.text.secondary,
        disabledTextColor = SkyFitColor.text.disabled,
        defaultBorderColor = SkyFitColor.border.secondaryButton,
        pressedBorderColor = SkyFitColor.border.default,
        disabledBorderColor = SkyFitColor.border.default,
    ),
    Plain(
        defaultBackgroundColor = Color.Transparent,
        pressedBackgroundColor = Color.LightGray,
        disabledBackgroundColor = Color.Transparent,
        defaultTextColor = Color(0xFF4CD9CC),
        pressedTextColor = Color.Gray,
        disabledTextColor = Color.Gray,
        defaultBorderColor = SkyFitColor.transparent,
        pressedBorderColor = SkyFitColor.transparent,
        disabledBorderColor = SkyFitColor.transparent,
    )
}

enum class ButtonSize(val height: Dp, val horizontalPadding: Dp, val verticalPadding: Dp) {
    Large(48.dp, 20.dp, 12.dp),
    Medium(40.dp, 16.dp, 10.dp),
    Micro(32.dp, 12.dp, 8.dp)
}

enum class ButtonState { Rest, Hover, Active, Pressed, Disabled, Loading }
