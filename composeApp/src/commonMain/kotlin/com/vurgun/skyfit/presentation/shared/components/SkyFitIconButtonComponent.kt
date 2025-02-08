package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun SkyFitIconButton(
    painter: Painter = painterResource(Res.drawable.logo_skyfit),
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SkyFitCircularProgressIconButton(
    painter: Painter = painterResource(Res.drawable.logo_skyfit),
    progress: Float,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            drawArc(
                color = SkyFitColor.border.default,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx())
            )
            drawArc(
                color = SkyFitColor.border.secondaryButton,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}