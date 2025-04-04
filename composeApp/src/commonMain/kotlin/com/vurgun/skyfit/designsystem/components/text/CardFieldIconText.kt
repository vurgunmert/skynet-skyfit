package com.vurgun.skyfit.designsystem.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardFieldIconText(
    iconRes: DrawableResource,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.default, RoundedCornerShape(8.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = SkyFitColor.icon.secondary
        )
        Spacer(Modifier.width(8.dp))

        Text(
            text = text,
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier.weight(1f),
            color = SkyFitColor.text.secondary
        )
    }
}
