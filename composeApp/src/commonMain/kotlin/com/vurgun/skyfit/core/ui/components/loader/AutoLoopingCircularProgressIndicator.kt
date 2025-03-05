package com.vurgun.skyfit.core.ui.components.loader


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun AutoLoopingCircularProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    primaryColor: Color = SkyFitColor.specialty.buttonBgRest,
    backgroundColor: Color = SkyFitColor.border.default,
    animationDuration: Int = 1500
) {
    val infiniteTransition = rememberInfiniteTransition()

    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(124.dp)) {
        val size = size.minDimension
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // Background Circle
        drawArc(
            color = backgroundColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )

        // Animated Foreground Progress Arc
        drawArc(
            color = primaryColor,
            startAngle = -90f,
            sweepAngle = animatedProgress * 360f,
            useCenter = false,
            style = stroke
        )
    }
}
