package com.vurgun.skyfit.feature.persona.components

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.persona.components.viewdata.BarGraphStatisticsViewData
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_calories
import skyfit.core.ui.generated.resources.ic_clock
import skyfit.core.ui.generated.resources.ic_path_distance

@Composable
fun BarGraphStatistics(viewData: BarGraphStatisticsViewData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatisticCard(
            title = "Kalori",
            value = viewData.calories,
            unit = viewData.calorieUnit,
            color = SkyFitColor.specialty.statisticOrange,
            iconRes = Res.drawable.ic_calories
        )
        Spacer(Modifier.width(16.dp))
        StatisticCard(
            title = "Zaman",
            value = viewData.time,
            unit = viewData.timeUnit,
            color = SkyFitColor.specialty.statisticBlue,
            iconRes = Res.drawable.ic_clock
        )
        Spacer(Modifier.width(16.dp))
        StatisticCard(
            title = "Mesafe",
            value = viewData.distance,
            unit = viewData.distanceUnit,
            color = SkyFitColor.specialty.statisticPink,
            iconRes = Res.drawable.ic_path_distance
        )
    }
}

@Composable
fun StatisticCard(
    title: String,
    value: String,
    unit: String,
    color: Color,
    iconRes: DrawableResource
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(color)
            .padding(top = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(40.dp).background(SkyFitColor.background.fillTransparentBlur, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(iconRes),
                    contentDescription = title,
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(text = title, style = SkyFitTypography.bodyMediumRegular, color = SkyFitColor.text.inverse)
        }
        Spacer(Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, style = SkyFitTypography.heading3, color = SkyFitColor.text.inverse)
            Text(text = unit, style = SkyFitTypography.bodyMediumSemibold, color = SkyFitColor.text.inverse)
        }
    }
}
