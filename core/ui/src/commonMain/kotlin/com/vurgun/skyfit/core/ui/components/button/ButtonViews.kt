package com.vurgun.skyfit.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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

//@Composable
//fun PrimaryMediumButton(
//    text: String,
//    modifier: Modifier = Modifier.wrapContentWidth(),
//    leftIconPainter: Painter? = null,
//    rightIconPainter: Painter? = null,
//    isEnabled: Boolean = true,
//    isLoading: Boolean = false,
//    onClick: (() -> Unit)? = null
//) {
//    SkyFitButtonComponent(
//        text = text,
//        onClick = onClick,
//        variant = ButtonVariant.Primary,
//        size = ButtonSize.Medium,
//        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
//        modifier = modifier,
//        isEnabled = isEnabled,
//        leftIconPainter = leftIconPainter,
//        rightIconPainter = rightIconPainter
//    )
//}


//@Composable
//fun SecondaryMediumButton(
//    text: String,
//    modifier: Modifier = Modifier.wrapContentWidth(),
//    leftIconPainter: Painter? = null,
//    rightIconPainter: Painter? = null,
//    isEnabled: Boolean = true,
//    isLoading: Boolean = false,
//    onClick: () -> Unit
//) {
//    SkyFitButtonComponent(
//        text = text,
//        onClick = onClick,
//        variant = ButtonVariant.Secondary,
//        size = ButtonSize.Medium,
//        state = if (isLoading) ButtonState.Loading else ButtonState.Rest,
//        modifier = modifier,
//        isEnabled = isEnabled,
//        leftIconPainter = leftIconPainter,
//        rightIconPainter = rightIconPainter
//    )
//}


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

@Composable
fun PrimaryMicroButton(
    text: String,
    rightIconRes: DrawableResource? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(SkyFitColor.specialty.buttonBgRest)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.inverse)
        )
        rightIconRes?.let {
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = SkyFitColor.icon.inverse
            )
        }
    }
}

@Composable
fun PrimaryMediumButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    rightIconRes: DrawableResource? = null,
    onClick: () -> Unit
) {
    if (enabled) {
        Row(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() }
                .background(SkyFitColor.specialty.buttonBgRest)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.inverse)
            )
            rightIconRes?.let {
                Spacer(Modifier.width(4.dp))
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = SkyFitColor.icon.inverse
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .clip(CircleShape)
                .background(SkyFitColor.specialty.buttonBgDisabled)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.disabled)
            )
            rightIconRes?.let {
                Spacer(Modifier.width(4.dp))
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = SkyFitColor.icon.disabled
                )
            }
        }
    }
}


@Composable
fun SecondaryMediumButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    rightIconRes: DrawableResource? = null,
    onClick: () -> Unit
) {
    if (enabled) {
        Row(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() }
                .background(SkyFitColor.specialty.secondaryButtonRest)
                .border(1.dp, SkyFitColor.border.secondaryButton, CircleShape)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.default)
            )
            rightIconRes?.let {
                Spacer(Modifier.width(4.dp))
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = SkyFitColor.icon.inverse
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .clip(CircleShape)
                .background(SkyFitColor.specialty.secondaryButtonRest)
                .border(1.dp, SkyFitColor.border.secondaryButtonDisabled, CircleShape)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.disabled)
            )
            rightIconRes?.let {
                Spacer(Modifier.width(4.dp))
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = SkyFitColor.icon.disabled
                )
            }
        }
    }
}

@Composable
fun SecondaryDestructiveMicroButton(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(SkyFitColor.specialty.secondaryButtonRest)
            .border(1.dp, SkyFitColor.border.critical, CircleShape)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.criticalOnBgFill)
        )
    }
}