package com.vurgun.skyfit.feature_chatbot.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit
import kotlin.math.sqrt

@Composable
private fun Hexagon(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val size = this.size.width
        val triangleHeight = (sqrt(3.0f) * size / 2f)
        val centerX = size / 2f
        val centerY = size / 2f

        val hexagonPath = Path().apply {
            moveTo(centerX, centerY + size / 2)
            lineTo(centerX - triangleHeight / 2, centerY + size / 4)
            lineTo(centerX - triangleHeight / 2, centerY - size / 4)
            lineTo(centerX, centerY - size / 2)
            lineTo(centerX + triangleHeight / 2, centerY - size / 4)
            lineTo(centerX + triangleHeight / 2, centerY + size / 4)
            close()
        }

        drawIntoCanvas { canvas ->
            canvas.drawPath(
                path = hexagonPath,
                paint = Paint().apply {
                    color = SkyFitColor.specialty.buttonBgRest.copy(alpha = 0.8f)
                    pathEffect = PathEffect.cornerPathEffect(12f) // Adjust for smoother edges
                }
            )
        }
    }
}

@Composable
fun ChatBotButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(44.dp, 48.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Hexagon()

        Image(
            painterResource(Res.drawable.logo_skyfit),
            contentDescription = "ChatBot Button",
            modifier = Modifier
                .padding(top = 2.dp)
                .size(29.dp, 32.dp)
        )
    }
}