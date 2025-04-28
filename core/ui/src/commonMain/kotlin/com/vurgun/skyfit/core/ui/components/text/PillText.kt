package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PillText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = SkyFitColor.text.inverse,
    backgroundColor: Color = SkyFitColor.specialty.buttonBgRest,
    rightIconRes: DrawableResource? = null
) {
    Row(
        modifier
            .wrapContentSize()
            .background(backgroundColor, CircleShape)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier,
            color = textColor,
            style = SkyFitTypography.bodyMediumMedium
        )

        if (rightIconRes != null) {
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(rightIconRes),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}