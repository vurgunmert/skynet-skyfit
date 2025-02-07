package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit


@Composable
fun SkyFitChipComponent(text: String, onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.wrapContentWidth(),
        text = text,
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Micro,
        state = ButtonState.Rest,
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}