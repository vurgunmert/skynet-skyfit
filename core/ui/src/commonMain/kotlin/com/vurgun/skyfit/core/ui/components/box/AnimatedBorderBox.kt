package com.vurgun.skyfit.core.ui.components.box

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBorderBox(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 16.dp,
    colors: List<Color> = listOf(Color.Red, Color.Yellow, Color.Green),
    backgroundColor: Color = Color.Transparent,
    animationDuration: Int = 4000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "animated-border")

    val animatedValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "offset"
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = animatedValue, y = animatedValue)
    )

    Box(
        modifier = modifier
            .border(BorderStroke(borderWidth, brush), shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
    ) {
        content()
    }
}


@Composable
fun rememberAnimatedBorderBrush(
    enabled: Boolean,
    colors: List<Color> = listOf(Color.Cyan, Color.Blue, Color.Magenta),
    duration: Int = 3000
): Brush {
    if (!enabled) {
        return Brush.linearGradient(colors)
    }

    val transition = rememberInfiniteTransition(label = "BorderAnim")
    val animatedOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Offset"
    )

    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = Offset(x = animatedOffset, y = animatedOffset)
    )
}
