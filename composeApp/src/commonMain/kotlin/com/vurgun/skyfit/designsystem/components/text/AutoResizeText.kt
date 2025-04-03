package com.vurgun.skyfit.designsystem.components.text

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    style: TextStyle,
    maxFontSize: TextUnit = style.fontSize,
    minFontSize: TextUnit = 10.sp
) {
    BoxWithConstraints(modifier) {
        val textLength = text.length
        val containerWidth = maxWidth
        val scaleFactor = when {
            textLength > 30 -> 0.7f
            textLength > 20 -> 0.85f
            else -> 1f
        }

        Text(
            text = text,
            maxLines = 1,
            color = color,
            style = style.copy(fontSize = maxFontSize * scaleFactor)
        )
    }
}
