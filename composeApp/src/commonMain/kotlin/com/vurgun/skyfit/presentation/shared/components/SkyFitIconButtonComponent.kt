package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun SkyFitIconButton(painter: Painter, onClick: () -> Unit) {
    Box(
        Modifier
            .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Back",
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(16.dp)
        )
    }
}