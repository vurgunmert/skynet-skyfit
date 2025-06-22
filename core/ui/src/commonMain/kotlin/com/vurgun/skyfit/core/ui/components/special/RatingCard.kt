package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_star_filled

@Composable
fun RatingButton(rating: Float, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(SkyFitColor.background.fillSemiTransparent, CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rating",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            painter = painterResource(Res.drawable.ic_star_filled),
            contentDescription = "Star",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}