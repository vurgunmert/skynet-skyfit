package com.vurgun.skyfit.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.HomeScreenResponsiveLayout
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

@Composable
fun FacilityHomeExpandedScreen(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {

    HomeScreenResponsiveLayout(
        leftContent = {
            StatCardComponent(content)
            ProviderLessonTable.ExpandedHomeFacilityLessonTable(content.allLessons)
        },
        rightContent = {
            FacilityDashboardAppointments(content, onAction)
        }
    )
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
        StatCardGrid(content.statistics.primaryMetrics)

        if (content.statistics.chartData != null) {
            MobileDashboardHomeFacilityGraph()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatCardGrid(
    stats: List<DashboardStatMetric>,
    modifier: Modifier = Modifier
) {
    val columns = when (LocalWindowSize.current) {
        WindowSize.COMPACT -> 2
        WindowSize.MEDIUM, WindowSize.EXPANDED -> 4
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = columns,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stats.forEach { stat ->
            HomeFacilityStatCard(
                stat = stat,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 84.dp)
            )
        }
    }
}


@Composable
fun HomeFacilityStatCard(
    stat: DashboardStatMetric,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SkyFitColor.background.fillTransparent)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SkyText(
            text = stat.title,
            styleType = TextStyleType.BodyMediumMedium,
            color = SkyFitColor.text.secondary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkyText(
                text = stat.value,
                styleType = TextStyleType.Heading5
            )

            stat.changePercent?.let {
                val isPositive = stat.changeDirection == DashboardStatMetric.ChangeDirection.UP
                val backgroundColor = Color(if (isPositive) 0xFF1E7F4D else 0xFF9C1B1B).copy(alpha = 0.2f)
                val textColor = Color(if (isPositive) 0xFF1E7F4D else 0xFF9C1B1B)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    SkyText(
                        text = stat.value,
                        styleType = TextStyleType.BodySmallSemibold,
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