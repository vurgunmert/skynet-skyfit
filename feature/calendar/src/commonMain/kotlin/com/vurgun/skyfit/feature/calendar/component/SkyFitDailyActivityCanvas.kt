package com.vurgun.skyfit.feature.calendar.component

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
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlin.math.roundToInt

data class SkyFitDailyActivityItem(
    val emoji: String,        // Aktivite simgesi (ör: 🍣, 🔥)
    val name: String,         // Aktivite adı (ör: Öğün Hazırlığı)
    val startHourMinutes: Int, // Başlangıç saati ve dakikası (ör: 600 = 6:00)
    val startBlock: Int       // Dikey griddeki başlangıç bloğu (ör: 0, 1, 2...)
)

@Composable
fun SkyFitDailyActivityCanvas(
    activities: List<SkyFitDailyActivityItem>,
    selectedBlock: Int,
    onActivityUpdate: (SkyFitDailyActivityItem) -> Unit
) {
    val totalBlocks = 6 // Dikey gridde 6 sütun
    val hours = listOf("06:00", "09:00", "12:00", "15:00", "18:00", "21:00") // Saat etiketi

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val gridWidth = this.maxWidth
        val blockWidth = gridWidth / totalBlocks // Her bloğun genişliği

        Column(modifier = Modifier.fillMaxSize()) {
            // Block Hours
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hours.forEachIndexed { index, hour ->

                    val textColor = if (index == selectedBlock) {
                        SkyFitColor.text.default
                    } else {
                        SkyFitColor.text.disabled
                    }

                    Text(
                        text = hour,
                        style = SkyFitTypography.bodyXSmallSemibold,
                        color = textColor,
                        modifier = Modifier.width(blockWidth),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Grid ve aktiviteler
            Box(modifier = Modifier.fillMaxSize()) {
                // Çizim düzlemi (dikey grid çizgileri)
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

                // Aktivite kutuları
                activities.sortedBy { it.startHourMinutes }.forEachIndexed { index, skyFitDailyActivityItem ->
                    DraggableActivityRow(
                        index = index,
                        activity = skyFitDailyActivityItem,
                        blockWidth = blockWidth,
                        onActivityUpdate = onActivityUpdate
                    )
                }
            }
        }
    }
}


@Composable
fun DraggableActivityRow(
    index: Int,
    activity: SkyFitDailyActivityItem,
    blockWidth: Dp,
    onActivityUpdate: (SkyFitDailyActivityItem) -> Unit
) {
    val density = LocalDensity.current
    val blockWidthPx = with(density) { blockWidth.toPx() } // Blok genişliği (px)
    val blockHeightPx = 24.dp * index

    var xOffset by remember { mutableStateOf(0f) } // Yatay sürükleme pozisyonu
    val initialX = activity.startBlock * blockWidthPx // Başlangıç pozisyonu (grid'e göre hizalı)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = with(density) { (initialX + xOffset).toDp() }, y = 36.dp + blockHeightPx) // Yatay konum
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    xOffset += delta // Sürükleme sırasında yatay offset güncelle
                },
                onDragStopped = {
                    // En yakın grid sütununu hesapla
                    val nearestBlock = ((initialX + xOffset) / blockWidthPx).roundToInt()

                    // Yeni x pozisyonuna hizala
                    val newStartHourMinutes = nearestBlock * 60 // Her sütun = 1 saat

                    // Aktiviteyi güncelle ve hizala
                    onActivityUpdate(activity.copy(startBlock = nearestBlock, startHourMinutes = newStartHourMinutes))
                    xOffset = 0f // Offset'i sıfırla
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .background(SkyFitColor.specialty.buttonBgRest, shape = RoundedCornerShape(percent = 50))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${activity.emoji}  ${activity.name}",
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.inverse)
            )
        }
    }
}

