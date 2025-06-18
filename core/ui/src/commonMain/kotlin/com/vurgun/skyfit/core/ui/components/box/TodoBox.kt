package com.vurgun.skyfit.core.ui.components.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType

@Composable
fun TodoBox(text: String, modifier: Modifier = Modifier.fillMaxSize()) {
    val backgroundColor = remember { randomColor() }

    Box(
        modifier = modifier
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        SkyText(
            text = text,
            styleType = TextStyleType.Heading1
        )
    }
}
private fun randomColor(): Color {
    val colors = listOf(
        Color(0xFFE57373), // Red
        Color(0xFF64B5F6), // Blue
        Color(0xFF81C784), // Green
        Color(0xFFFFD54F), // Yellow
        Color(0xFFBA68C8), // Purple
    )
    return colors.random()
}
