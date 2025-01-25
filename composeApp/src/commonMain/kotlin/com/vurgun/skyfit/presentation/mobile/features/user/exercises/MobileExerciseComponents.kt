package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileExerciseWorkoutItemComponent(modifier: Modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(text = "Jumping Jacks", style = SkyFitTypography.bodyMediumSemibold)
            Text(text = "x20", style = SkyFitTypography.bodySmall.copy(SkyFitColor.text.secondary))
        }
    }
}