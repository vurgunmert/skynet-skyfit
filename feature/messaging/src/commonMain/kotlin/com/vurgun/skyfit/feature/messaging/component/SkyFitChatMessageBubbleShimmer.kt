package com.vurgun.skyfit.feature.messaging.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor


@Composable
fun SkyFitChatMessageBubbleShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ShimmerBrush())
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SkyFitColor.background.default)
                .padding(12.dp)
                .width(180.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(0.8f)
                    .background(ShimmerBrush())
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth()
                    .background(ShimmerBrush())
            )
        }
    }
}


@Composable
fun ShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition()
    val shimmerTranslate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    return Brush.linearGradient(
        colors = listOf(Color.LightGray.copy(alpha = 0.5f), Color.Gray.copy(alpha = 0.2f), Color.LightGray.copy(alpha = 0.5f)),
        start = Offset(x = shimmerTranslate, y = 0f),
        end = Offset(x = shimmerTranslate + 300f, y = 0f)
    )
}
