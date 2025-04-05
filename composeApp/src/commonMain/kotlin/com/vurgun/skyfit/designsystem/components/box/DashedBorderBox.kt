package com.vurgun.skyfit.designsystem.components.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun DashedBorderBox(
    modifier: Modifier = Modifier,
    borderColor: Color = SkyFitColor.border.secondaryButton,
    dashLength: Float = 30f,
    gapLength: Float = 30f,
    cornerRadius: Dp = 30.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .drawBehind {
                val stroke = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
                )
                drawRoundRect(
                    color = borderColor,
                    size = size,
                    style = stroke,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
            .clip(RoundedCornerShape(cornerRadius))
            .background(SkyFitColor.background.fillTransparentSecondary)
            .padding(16.dp),
        content = content
    )
}
