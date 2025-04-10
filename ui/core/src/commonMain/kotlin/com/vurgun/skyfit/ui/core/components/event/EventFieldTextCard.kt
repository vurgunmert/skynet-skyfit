package com.vurgun.skyfit.ui.core.components.event

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.designsystem.components.text.CardFieldIconText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_clock
import skyfit.ui.core.generated.resources.ic_dashboard
import skyfit.ui.core.generated.resources.ic_lira
import skyfit.ui.core.generated.resources.ic_location_pin
import skyfit.ui.core.generated.resources.ic_note
import skyfit.ui.core.generated.resources.ic_posture
import skyfit.ui.core.generated.resources.ic_profile_fill

@Composable
fun EventTimeText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_clock, modifier = modifier)
}

@Composable
fun EventCategoryText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_dashboard, modifier = modifier)
}

@Composable
fun EventLocationText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_location_pin, modifier = modifier)
}

@Composable
fun EventTrainerText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_profile_fill, modifier = modifier)
}

@Composable
fun EventCapacityText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_posture, modifier = modifier)
}

@Composable
fun EventCostText(value: String, modifier: Modifier = Modifier) {
    CardFieldIconText(text = value, iconRes = Res.drawable.ic_lira, modifier = modifier)
}

@Composable
fun EventNoteText(value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_note),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = SkyFitColor.icon.secondary
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = value,
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier.weight(1f),
            color = SkyFitColor.text.secondary
        )
    }
}