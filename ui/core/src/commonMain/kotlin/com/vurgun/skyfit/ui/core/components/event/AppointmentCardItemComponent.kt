package com.vurgun.skyfit.ui.core.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.special.SkyFitCheckBoxComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_clock
import skyfit.ui.core.generated.resources.ic_dashboard
import skyfit.ui.core.generated.resources.ic_exercises
import skyfit.ui.core.generated.resources.ic_lira
import skyfit.ui.core.generated.resources.ic_location_pin
import skyfit.ui.core.generated.resources.ic_note
import skyfit.ui.core.generated.resources.ic_posture
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.logo_skyfit

data class AppointmentCardViewData(
    val iconId: Int,
    val title: String,
    val date: String,
    val hours: String,
    val category: String,
    val location: String,
    val trainer: String,
    val capacity: String? = null,
    val cost: String? = null,
    val note: String? = null,
    val isFull: Boolean? = null,
    val canNotify: Boolean = false,
    val status: String? = null,
    val attended: Boolean = false
)

@Composable
fun AppointmentCardItemComponent(item: AppointmentCardViewData, modifier: Modifier) {

    var notifyOnAvailability by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(color = SkyFitColor.background.fillTransparentSecondary, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = SkyFitAsset.getPainter(iconId = item.iconId),
                contentDescription = "Exercise",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = item.title,
                maxLines = 1,
                style = SkyFitTypography.bodyMediumSemibold,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = item.date,
                style = SkyFitTypography.bodyMediumSemibold,
                color = SkyFitColor.text.secondary
            )
        }

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AppointmentSingleDataBoxComponent(
                text = item.hours,
                iconRes = Res.drawable.ic_clock,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            AppointmentSingleDataBoxComponent(
                text = item.category,
                iconRes = Res.drawable.ic_dashboard,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))
        Row {
            AppointmentSingleDataBoxComponent(
                text = item.location,
                iconRes = Res.drawable.ic_location_pin,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            AppointmentSingleDataBoxComponent(
                text = item.trainer,
                iconRes = Res.drawable.ic_profile,
                modifier = Modifier.weight(1f)
            )
        }

        if (item.capacity != null && item.cost != null) {
            Spacer(Modifier.height(8.dp))
            Row {
                AppointmentSingleDataBoxComponent(
                    text = item.capacity,
                    iconRes = Res.drawable.ic_posture,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                AppointmentSingleDataBoxComponent(
                    text = item.cost,
                    iconRes = Res.drawable.ic_lira,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (item.note != null) {
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Icon(
                    painter = painterResource(Res.drawable.ic_note),
                    contentDescription = "Note",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.note,
                    style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
                )
            }
        }

        if (item.isFull == true) {
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth().background(SkyFitColor.background.surfaceCautionActive, RoundedCornerShape(12.dp)).padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = "Warning",
                        tint = SkyFitColor.icon.caution,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Üzgünüz, bu kursun kontenjanı şu an için dolu.",
                        style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            SkyFitCheckBoxComponent(
                checked = notifyOnAvailability,
                onCheckedChange = { notifyOnAvailability = it },
                label = "Yeni bir yer açıldığında bana haber ver.",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AppointmentSingleDataBoxComponent(
    text: String,
    iconRes: DrawableResource = Res.drawable.ic_exercises,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.background(SkyFitColor.background.default, shape = RoundedCornerShape(8.dp)).padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = "",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

