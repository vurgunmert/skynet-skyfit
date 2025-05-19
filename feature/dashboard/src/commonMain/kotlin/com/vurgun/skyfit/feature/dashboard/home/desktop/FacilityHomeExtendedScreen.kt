package com.vurgun.skyfit.feature.dashboard.home.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.core.ui.components.special.HomeScreenResponsiveLayout
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.dashboard.explore.TimeFilterSelector
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeAction
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeUiState
import com.vurgun.skyfit.feature.dashboard.home.mobile.ChangeLineChart
import com.vurgun.skyfit.feature.dashboard.home.mobile.FacilityDashboardAppointments
import com.vurgun.skyfit.feature.dashboard.home.mobile.HomeFacilityStat
import com.vurgun.skyfit.feature.dashboard.home.mobile.MobileDashboardHomeTrainerClassScheduleComponent

@Composable
fun FacilityHomeExtendedScreen(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {

    HomeScreenResponsiveLayout(
        leftContent = {
            StatCardComponent(content)
            MobileDashboardHomeTrainerClassScheduleComponent()
        },
        rightContent = {
            FacilityDashboardAppointments(content, onAction)
        }
    )

//
//    AutoSideBySideLayout(
//        modifier = Modifier.fillMaxSize(),
//        leftContent = {
//        },
//        rightContent = {
//            FacilityDashboardAppointments(content, onAction)
//        }
//    )
}

@Composable
fun StatCardComponent(
    content: FacilityHomeUiState.Content
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SkyFitColor.background.default)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        StatCardGrid(content.stats)
        MobileDashboardHomeFacilityGraph()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatCardGrid(
    stats: List<HomeFacilityStat>,
    modifier: Modifier = Modifier
) {
    val columns = when (LocalWindowSize.current) {
        WindowSize.COMPACT -> 2
        WindowSize.MEDIUM, WindowSize.EXPANDED -> 4
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = columns,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stats.forEach { stat ->
            HomeFacilityStatCard(
                stat = stat,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 84.dp) // avoid max=84.dp (inverts logic)
            )
        }
    }
}


@Composable
fun HomeFacilityStatCard(
    stat: HomeFacilityStat,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SkyFitColor.background.fillTransparent)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stat.title,
            fontSize = 14.sp,
            color = SkyFitColor.text.secondary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stat.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SkyFitColor.text.default
            )

            stat.percentage?.let {
                val backgroundColor =
                    if (stat.isPositive == true) Color(0xFF1E7F4D).copy(alpha = 0.2f)
                    else Color(0xFF9C1B1B).copy(alpha = 0.2f)
                val textColor = if (stat.isPositive == true) Color(0xFF1E7F4D)
                else Color(0xFF9C1B1B)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = textColor
                    )
                }
            }
        }
    }
}


@Composable
fun MobileDashboardHomeFacilityGraph() {
    var selectedFilter by remember { mutableStateOf("H") }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkyText(
                text = "Üye Değişimi Grafiği",
                styleType = TextStyleType.BodyMediumMedium,
                color = SkyFitColor.text.secondary
            )
            TimeFilterSelector(
                selected = selectedFilter,
                onSelect = { selectedFilter = it }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ChangeLineChart(
            dataPoints = listOf(12, 9, 15, 20, 17, 8, 11),
            labels = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}