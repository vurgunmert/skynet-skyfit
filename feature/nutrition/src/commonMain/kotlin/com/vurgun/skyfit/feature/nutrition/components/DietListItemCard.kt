package com.vurgun.skyfit.feature.nutrition.components

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun DietListItemCard() {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Peynirli Salata",
                style = SkyFitTypography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "7 gün sadece kahvaltıda",
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.default)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "234 kcal",
                style = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default)
            )
        }
        Spacer(Modifier.width(12.dp))
        AsyncImage(
            model = "https://ik.imagekit.io/skynet2skyfit/meal_yogurt_granola.png?updatedAt=1738866100967",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(74.dp)
                .clip(CircleShape)
        )
    }
}