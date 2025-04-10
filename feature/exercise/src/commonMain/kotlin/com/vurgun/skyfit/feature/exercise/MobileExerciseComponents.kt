package com.vurgun.skyfit.feature.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun MobileExerciseWorkoutItemComponent(modifier: Modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier.size(48.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(text = "Jumping Jacks", style = SkyFitTypography.bodyMediumSemibold)
            Text(text = "x20", style = SkyFitTypography.bodySmall.copy(SkyFitColor.text.secondary))
        }
    }
}