package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.utility.toMinutesOfDay
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlin.math.roundToInt

data class CalendarWorkoutTimeBlockItem(
    val name: String,
    val startTime: String
)

@Composable
fun CalendarWorkoutTimeBlockGrid(
    activities: List<CalendarWorkoutTimeBlockItem>,
    modifier: Modifier = Modifier,
) {
    val totalBlocks = 6
    val hours = listOf("06:00", "09:00", "12:00", "15:00", "18:00", "21:00")
    val hourRanges = listOf(360, 540, 720, 900, 1080, 1260) // [06:00, 09:00, ...]

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val blockWidth = maxWidth / totalBlocks

        Column(modifier = Modifier.fillMaxSize()) {
            // Top header with hours
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hours.forEachIndexed { index, hourLabel ->
                    val start = hourRanges[index]
                    val end = start + 180 // 3-hour blocks

                    val highlight = activities.any {
                        it.startTime.toMinutesOfDay() in start until end
                    }

                    Text(
                        text = hourLabel,
                        style = SkyFitTypography.bodyXSmallSemibold,
                        color = if (highlight) SkyFitColor.text.default else SkyFitColor.text.disabled,
                        modifier = Modifier.width(blockWidth),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Grid background + activity items
            Box(modifier = Modifier.fillMaxSize()) {
                // Grid lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val blockWidthPx = size.width / totalBlocks
                    for (i in 0..totalBlocks) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(i * blockWidthPx, 0f),
                            end = Offset(i * blockWidthPx, size.height),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    }
                }

                // Activity rows (visually placed)
                activities.sortedBy { it.startTime.toMinutesOfDay() }.forEachIndexed { index, activity ->
                    val blockIndex = hourRanges.indexOfLast {
                        activity.startTime.toMinutesOfDay() >= it
                    }.coerceAtLeast(0)

                    ActivityChipRow(
                        index = index,
                        activity = activity,
                        blockWidth = blockWidth,
                        blockIndex = blockIndex
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityChipRow(
    index: Int,
    activity: CalendarWorkoutTimeBlockItem,
    blockWidth: Dp,
    blockIndex: Int
) {
    val density = LocalDensity.current
    val xPosition = with(density) { (blockWidth * blockIndex).toPx() }
    val yOffset = 36.dp + (24.dp * index)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(
                x = with(density) { xPosition.toDp() },
                y = yOffset
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .background(SkyFitColor.specialty.buttonBgRest, shape = RoundedCornerShape(percent = 50))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = activity.name,
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.inverse)
            )
        }
    }
}

//@Composable
//fun DraggableActivityRow(
//    index: Int,
//    activity: CalendarWorkoutTimeBlockItem,
//    blockWidth: Dp,
//    isDraggable: Boolean = false,
//    onActivityUpdate: (CalendarWorkoutTimeBlockItem) -> Unit
//) {
//    val density = LocalDensity.current
//    val blockWidthPx = with(density) { blockWidth.toPx() }
//    val blockHeightPx = 24.dp * index
//
//    var xOffset by remember { mutableStateOf(0f) }
//    val initialX = activity.startBlock * blockWidthPx
//
//    val baseModifier = Modifier
//        .fillMaxWidth()
//        .offset(x = with(density) { (initialX + xOffset).toDp() }, y = 36.dp + blockHeightPx)
//        .padding(vertical = 8.dp, horizontal = 16.dp)
//
//    val dragModifier = if (isDraggable) {
//        baseModifier.draggable(
//            orientation = Orientation.Horizontal,
//            state = rememberDraggableState { delta -> xOffset += delta },
//            onDragStopped = {
//                val nearestBlock = ((initialX + xOffset) / blockWidthPx).roundToInt()
//                val newStartHourMinutes = nearestBlock * 60
//                onActivityUpdate(activity.copy(startBlock = nearestBlock, startHourMinutes = newStartHourMinutes))
//                xOffset = 0f
//            }
//        )
//    } else baseModifier
//
//    Row(
//        modifier = dragModifier,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Row(
//            modifier = Modifier
//                .background(SkyFitColor.specialty.buttonBgRest, shape = RoundedCornerShape(percent = 50))
//                .padding(horizontal = 8.dp, vertical = 4.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = activity.name,
//                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.inverse)
//            )
//        }
//    }
//}
