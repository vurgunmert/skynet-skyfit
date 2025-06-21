package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.DashboardStatCardModel

internal object HomeStatisticComponents {

    @Composable
    fun StatCardComponent(
        statistics: DashboardStatCardModel?,
        modifier: Modifier = Modifier
    ) {
        val metrics = statistics?.primaryMetrics.takeUnless { it.isNullOrEmpty() } ?: return

        var selectedStat by remember { mutableStateOf(metrics.first()) }

        Column(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(SkyFitColor.background.default)
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            InformativeCardGrid(metrics)

            statistics?.chartData.takeUnless { it.isNullOrEmpty() }?.let {
                InformativeGraph(selectedStat)
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun InformativeCardGrid(
        stats: List<StatisticCardUiData>,
        modifier: Modifier = Modifier
    ) {
        val columns = when (LocalWindowSize.current) {
            WindowSize.COMPACT, WindowSize.MEDIUM ->2
            WindowSize.EXPANDED -> 4
        }

        FlowRow(
            modifier = modifier.fillMaxWidth(),
            maxItemsInEachRow = columns,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stats.forEach { stat ->
                InformativeCard(
                    stat = stat,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 84.dp)
                )
            }
        }
    }

    @Composable
    fun InformativeCard(
        stat: StatisticCardUiData,
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
                    val isPositive = stat.changeDirection == StatisticCardUiData.ChangeDirection.UP
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
    fun InformativeGraph(
        stat: StatisticCardUiData // TODO: Not working - Connect
    ) {
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
                HomeLessonTableFilterSelector(selectedFilter) { selectedFilter = it }
            }
            Spacer(modifier = Modifier.height(8.dp))
            InformativeGraphLineChart(
                dataPoints = listOf(12, 9, 15, 20, 17, 8, 11),
                labels = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz"),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun InformativeGraphLineChart(
        dataPoints: List<Int>,
        labels: List<String>,
        modifier: Modifier = Modifier
    ) {
        val textMeasurer = rememberTextMeasurer()

        // Chart padding
        val chartPadding = 32.dp
        val chartPaddingPx = with(LocalDensity.current) { chartPadding.toPx() }

        Column(modifier = modifier) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val maxValue = dataPoints.maxOrNull()?.takeIf { it > 0 } ?: 1
                val points = dataPoints.mapIndexed { i, value ->
                    val x = chartPaddingPx + (size.width - 2 * chartPaddingPx) / (dataPoints.size - 1) * i
                    val y =
                        size.height - chartPaddingPx - (value / maxValue.toFloat()) * (size.height - 2 * chartPaddingPx)
                    Offset(x, y)
                }

                // Draw horizontal grid lines
                val lineCount = 4
                repeat(lineCount + 1) { i ->
                    val ratio = i / lineCount.toFloat()
                    val y = chartPaddingPx + (size.height - 2 * chartPaddingPx) * (1f - ratio)

                    drawLine(
                        color = Color.Gray.copy(alpha = 0.4f),
                        start = Offset(chartPaddingPx, y),
                        end = Offset(size.width - chartPaddingPx, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    val valueLabel = (ratio * maxValue).toInt().toString()

                    val textLayoutResult = textMeasurer.measure(
                        text = AnnotatedString(valueLabel),
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp)
                    )

                    drawText(
                        textLayoutResult,
                        topLeft = Offset(4.dp.toPx(), y - textLayoutResult.size.height / 2)
                    )
                }

                // Smooth path
                val path = Path().apply {
                    moveTo(points.first().x, points.first().y)

                    for (i in 1 until points.size) {
                        val prev = points[i - 1]
                        val curr = points[i]
                        val mid = Offset((prev.x + curr.x) / 2, (prev.y + curr.y) / 2)

                        quadraticBezierTo(prev.x, prev.y, mid.x, mid.y)

                        if (i == points.lastIndex) {
                            lineTo(curr.x, curr.y)
                        }
                    }
                }

                drawPath(
                    path = path,
                    color = Color.Cyan,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Labels under the chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = chartPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                labels.forEach { label ->
                    Text(text = label, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}