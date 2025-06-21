package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_fiwe_logo_dark
import skyfit.core.ui.generated.resources.ic_fiwe_logo_light
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
            .size(52.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Hexagon()

        Image(
            painterResource(Res.drawable.ic_fiwe_logo_light),
            contentDescription = "ChatBot",
            modifier = Modifier.padding(top = 4.dp).padding(12.dp)
        )
    }
}