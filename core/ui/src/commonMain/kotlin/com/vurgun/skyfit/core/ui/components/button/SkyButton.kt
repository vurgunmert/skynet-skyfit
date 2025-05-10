package com.vurgun.skyfit.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

enum class SkyButtonVariant { Primary, Secondary, Plain, Destructive }
enum class SkyButtonSize { Micro, Medium, Large }
enum class SkyButtonState { Rest, Hover, Active, Pressed, Disabled, Loading }

data class SkyButtonStyle(
    val backgroundColor: Color?,
    val contentColor: Color,
    val borderColor: Color?,
    val typography: androidx.compose.ui.text.TextStyle,
    val paddingH: Dp,
    val paddingV: Dp,
    val iconSize: Dp,
    val shape: RoundedCornerShape = RoundedCornerShape(50)
)

object SkyButtonTokens {

    @Composable
    fun resolve(
        variant: SkyButtonVariant,
        size: SkyButtonSize,
        state: SkyButtonState
    ): SkyButtonStyle {
        val typography = when (size) {
            SkyButtonSize.Micro -> SkyFitTypography.bodyMediumMedium
            SkyButtonSize.Medium -> SkyFitTypography.bodyMediumMedium
            SkyButtonSize.Large -> SkyFitTypography.bodyLargeMedium
        }

        val (bg, content, border) = when (variant to state) {
            SkyButtonVariant.Primary to SkyButtonState.Rest -> Triple(
                SkyFitColor.specialty.buttonBgRest,
                SkyFitColor.text.inverse,
                null
            )

            SkyButtonVariant.Primary to SkyButtonState.Hover -> Triple(
                SkyFitColor.specialty.buttonBgHover,
                SkyFitColor.text.inverse,
                null
            )

            SkyButtonVariant.Primary to SkyButtonState.Active -> Triple(
                SkyFitColor.specialty.buttonBgActive,
                SkyFitColor.text.inverse,
                null
            )

            SkyButtonVariant.Primary to SkyButtonState.Pressed -> Triple(
                SkyFitColor.specialty.buttonBgPressed,
                SkyFitColor.text.inverse,
                null
            )

            SkyButtonVariant.Primary to SkyButtonState.Disabled -> Triple(
                SkyFitColor.specialty.buttonBgDisabled,
                SkyFitColor.text.disabled,
                null
            )

            SkyButtonVariant.Primary to SkyButtonState.Loading -> Triple(
                SkyFitColor.specialty.buttonBgLoading,
                SkyFitColor.text.disabled,
                null
            )

            SkyButtonVariant.Secondary to SkyButtonState.Rest -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.default,
                SkyFitColor.border.secondaryButton
            )

            SkyButtonVariant.Secondary to SkyButtonState.Hover -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.secondary,
                SkyFitColor.border.secondaryButtonHover
            )

            SkyButtonVariant.Secondary to SkyButtonState.Active -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.default,
                SkyFitColor.border.focus
            )

            SkyButtonVariant.Secondary to SkyButtonState.Pressed -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.secondary,
                SkyFitColor.border.default
            )

            SkyButtonVariant.Secondary to SkyButtonState.Disabled -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.disabled,
                SkyFitColor.border.secondaryButtonDisabled
            )

            SkyButtonVariant.Secondary to SkyButtonState.Loading -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.disabled,
                SkyFitColor.border.secondaryButtonHover
            )

            SkyButtonVariant.Plain to SkyButtonState.Rest -> Triple(null, SkyFitColor.text.linkInverse, null)
            SkyButtonVariant.Plain to SkyButtonState.Hover -> Triple(null, SkyFitColor.text.linkHover, null)
            SkyButtonVariant.Plain to SkyButtonState.Disabled -> Triple(null, SkyFitColor.text.disabled, null)

            SkyButtonVariant.Destructive to SkyButtonState.Rest -> Triple(
                SkyFitColor.specialty.secondaryButtonRest,
                SkyFitColor.text.criticalOnBgFill,
                SkyFitColor.border.critical
            )

            else -> Triple(SkyFitColor.specialty.buttonBgRest, SkyFitColor.text.default, null)
        }

        val (h, v) = when (size) {
            SkyButtonSize.Micro -> 16.dp to 6.dp
            SkyButtonSize.Medium -> 24.dp to 8.dp
            SkyButtonSize.Large -> 32.dp to 12.dp
        }

        return SkyButtonStyle(
            backgroundColor = bg,
            contentColor = content,
            borderColor = border,
            typography = typography,
            paddingH = h,
            paddingV = v,
            iconSize = 16.dp
        )
    }
}

@Composable
fun SkyButton(
    label: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: SkyButtonVariant = SkyButtonVariant.Primary,
    size: SkyButtonSize = SkyButtonSize.Medium,
    state: SkyButtonState = SkyButtonState.Rest,
    leftIcon: Painter? = null,
    rightIcon: Painter? = null,
    enabled: Boolean = true
) {
    val style = SkyButtonTokens.resolve(variant, size, if (!enabled) SkyButtonState.Disabled else state)

    Row(
        modifier = modifier
            .clip(style.shape)
            .then(
                if (style.backgroundColor != null)
                    Modifier.background(style.backgroundColor)
                else Modifier
            )
            .then(
                if (style.borderColor != null)
                    Modifier.border(1.dp, style.borderColor, style.shape)
                else Modifier
            )
            .clickable(enabled = enabled && state != SkyButtonState.Loading) { onClick() }
            .padding(horizontal = style.paddingH, vertical = style.paddingV),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (state == SkyButtonState.Loading) {
            CircularProgressIndicator(
                color = style.contentColor,
                modifier = Modifier.size(style.iconSize),
                strokeWidth = 2.dp
            )
        } else {
            leftIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = style.contentColor,
                    modifier = Modifier.size(style.iconSize)
                )
                Spacer(Modifier.width(6.dp))
            }

            label?.let {
                Text(text = it, style = style.typography.copy(color = style.contentColor))
            }

            rightIcon?.let {
                Spacer(Modifier.width(6.dp))
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = style.contentColor,
                    modifier = Modifier.size(style.iconSize)
                )
            }
        }
    }
}