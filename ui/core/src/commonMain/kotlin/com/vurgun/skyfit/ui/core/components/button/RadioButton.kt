package com.vurgun.skyfit.ui.core.components.button

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.text.BodyMediumRegularText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor

@Composable
fun RadioButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(
                color = SkyFitColor.icon.default,
                radius = size.minDimension / 2,
                style = Stroke(width = 2.dp.toPx())
            )
            if (selected) {
                drawCircle(
                    color = SkyFitColor.icon.default,
                    radius = size.minDimension / 4
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        BodyMediumRegularText(text)
    }
}