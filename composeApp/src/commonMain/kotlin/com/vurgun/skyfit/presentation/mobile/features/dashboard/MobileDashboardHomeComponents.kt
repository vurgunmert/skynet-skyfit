package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.trainer.home.MemberChangeLineChart
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityHourlyCalendarComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserTrophyItemComponent
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide.padding16
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitCircularProgressIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitImageComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitListItemCardComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitMonthPickerDropdownComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitCalendarGridComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.now
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileDashboardHomeToolbarComponent(
    onNotifications: () -> Unit = {},
    onMessages: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(20.dp).clickable(onClick = onNotifications)
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            tint = SkyFitColor.text.default,
            modifier = Modifier.size(20.dp).clickable(onClick = onMessages)
        )
    }
}

@Composable
fun MobileDashboardHomeCharacterProgressComponent(
    characterImageUrl: String = "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
    stepProgress: Float = 0.7f,
    distanceProgress: Float = 0.3f,
    waterProgress: Float = 0.2f,
    exerciseProgress: Float = 0.7f,
    calorieProgress: Float = 0.1f,
    activityProgress: Float = 0.5f,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        SkyFitImageComponent(
            url = characterImageUrl,
            modifier = Modifier.align(Alignment.Center).size(240.dp)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = stepProgress,
            modifier = Modifier.align(Alignment.TopStart)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = distanceProgress,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = waterProgress,
            modifier = Modifier.align(Alignment.BottomStart)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = exerciseProgress,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = calorieProgress,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        SkyFitCircularProgressIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            progress = activityProgress,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun MobileDashboardHomeTrophiesBarComponent(
    trophiesImageUrls: List<String> = listOf(
        "https://ik.imagekit.io/skynet2skyfit/badge_muscle_master.png?updatedAt=1738863832700",
        "https://ik.imagekit.io/skynet2skyfit/badge_mountain_climber.png?updatedAt=1738863832702",
        "https://ik.imagekit.io/skynet2skyfit/badge_muscle_master.png?updatedAt=1738863832700",
    ),
    onClick: () -> Unit = {}
) {
    Row(Modifier.clickable(onClick = onClick)) {
        trophiesImageUrls.take(3).forEach { url ->
            Spacer(Modifier.width(8.dp))
            MobileUserTrophyItemComponent(
                url = url,
                modifier = Modifier.size(34.dp, 53.dp)
            )
            Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
fun MobileDashboardHomeWeekProgressComponent() {
    val progressData = listOf(60, 42, 100, 0, 0, 0, 0) // Progress values for each day
    val daysOfWeek = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz") // Day labels
    val selectedDay = 2 // Highlighted day index (e.g., Wednesday - Çar)

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(148.dp)
    ) {
        Text(
            text = "Bu hafta",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            progressData.forEachIndexed { index, progress ->
                MobileDashboardHomeWeekProgressBar(
                    progress = progress,
                    dayLabel = daysOfWeek[index],
                    isSelected = index == selectedDay
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeWeekProgressBar(progress: Int, dayLabel: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (progress > 0) "$progress%" else "",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = if (progress > 0) 1f else 0.3f)
        )

        Box(
            modifier = Modifier
                .width(32.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, if (isSelected) Color.Cyan else Color.Gray, RoundedCornerShape(16.dp))
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((progress * 1.2).dp) // Scale height dynamically
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) Color.Cyan else Color.Gray.copy(alpha = 0.5f))
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = dayLabel,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = if (progress > 0) 1f else 0.3f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@Composable
fun MobileDashboardHomeActivityCalendarComponent() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    SkyFitCalendarGridComponent(
        initialSelectedDate = selectedDate,
        isSingleSelect = true,
        onDateSelected = { selectedDate = it }
    )
}

@Composable
fun MobileDashboardHomeActivityHourlyCalendarComponent(
    date: String = "18 Haziran Cumartesi",
    onClickAdd: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date,
                style = SkyFitTypography.bodyMediumSemibold,
                modifier = Modifier.weight(1f)
            )

            SkyFitButtonComponent(
                text = "+ Ekle",
                modifier = Modifier.wrapContentWidth(),
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Micro,
                state = ButtonState.Rest
            )
        }
        MobileUserActivityHourlyCalendarComponent()
    }
}

@Composable
fun MobileDashboardHomeUpcomingAppointmentsComponent(
    appointments: List<HomeAppointmentComponentItem> = listOf(
        HomeAppointmentComponentItem("", "Kişisel kuvvet antrenmanı", "08:00", "@ironstudio"),
        HomeAppointmentComponentItem("", "Kişisel kuvvet antrenmanı", "08:00", "@ironstudio"),
        HomeAppointmentComponentItem("", "Kişisel kuvvet antrenmanı", "08:00", "@ironstudio")
    ),
    onClickShowAll: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding16)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Yaklaşan Randevular",
                style = SkyFitTypography.bodyMediumSemibold
            )
            Text(
                text = "Hepsini Görüntüle",
                style = SkyFitTypography.bodyXSmall,
                color = SkyFitColor.border.secondaryButton,
                modifier = Modifier.clickable(onClick = onClickShowAll)
            )
        }

        appointments.forEach { appointment ->
            Spacer(modifier = Modifier.height(8.dp))
            MobileDashboardHomeAppointmentCard(appointment)
        }
    }
}

@Composable
private fun MobileDashboardHomeAppointmentCard(appointment: HomeAppointmentComponentItem) {
    SkyFitListItemCardComponent(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(SkyFitColor.background.default, RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Activity",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = appointment.title,
                style = SkyFitTypography.bodyMediumSemibold,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Time",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = appointment.time, fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Location",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = appointment.location, fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Enter",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

data class HomeAppointmentComponentItem(
    val icon: String,
    val title: String,
    val time: String,
    val location: String
)


@Composable
fun MobileDashboardHomeGeneralStatisticsComponent() {
    val data = listOf(
        StatisticsCircularChatComponentItem(
            title = "Yakılan kaloriler",
            percentage = 33.4f,
            color = SkyFitColor.specialty.statisticBlue,
            change = "+1,25"
        ),
        StatisticsCircularChatComponentItem(
            title = "Proteinler",
            percentage = 23.02f,
            color = SkyFitColor.specialty.statisticPink,
            change = "+3,42"
        ),
        StatisticsCircularChatComponentItem(
            title = "Karbonhidratlar",
            percentage = 11.24f,
            color = SkyFitColor.specialty.statisticOrange,
            change = "+2,12"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Genel bakış",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            MobileDashboardHomeCircularStatisticsChart(data = data)
            Spacer(modifier = Modifier.width(16.dp))
            MobileDashboardHomeStatisticsLegend(data)
        }
    }
}

@Composable
private fun MobileDashboardHomeCircularStatisticsChart(
    data: List<StatisticsCircularChatComponentItem>,
    modifier: Modifier = Modifier
) {
    val total = data.sumOf { it.percentage.toDouble() }.toFloat()
    val sweepAngles = data.map { (it.percentage / total) * 360f }

    Canvas(
        modifier = modifier.size(124.dp)
    ) {
        val strokeWidth = 8.dp.toPx()
        val circleSpacing = 2.dp.toPx() // Space between circles
        val maxSize = size.minDimension // Ensures circles stay within bounds
        val layerSpacing = strokeWidth + circleSpacing // Shrinking effect

        // Step 1: Draw stable background circles from largest to smallest
        data.forEachIndexed { index, stat ->
            val offset = index * layerSpacing
            val circleSize = Size(maxSize - (offset * 2), maxSize - (offset * 2)) // Shrink from all sides

            drawArc(
                color = stat.color.copy(alpha = 0.2f), // Background color with transparency
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offset, offset), // Adjust to shrink towards center
                size = circleSize
            )
        }

        // Step 2: Draw progress arcs on top (same concentric pattern)
        var startAngle = -90f
        data.forEachIndexed { index, stat ->
            val offset = index * layerSpacing
            val circleSize = Size(maxSize - (offset * 2), maxSize - (offset * 2))

            drawArc(
                color = stat.color,
                startAngle = startAngle,
                sweepAngle = sweepAngles[index],
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offset, offset), // Adjust to shrink towards center
                size = circleSize
            )
            startAngle += sweepAngles[index]
        }
    }
}


@Composable
private fun MobileDashboardHomeStatisticsLegend(data: List<StatisticsCircularChatComponentItem>) {
    Column(
        modifier = Modifier.size(124.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        data.forEach { item ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(item.color, shape = CircleShape)
                    )

                    Text(
                        text = item.title,
                        style = SkyFitTypography.bodyXSmall
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.percentage}%",
                        style = SkyFitTypography.bodyXSmall
                    )

                    Text(
                        text = item.change,
                        style = SkyFitTypography.bodyXSmall,
                        color = item.color
                    )
                }
            }
        }
    }
}

private data class StatisticsCircularChatComponentItem(
    val title: String,
    val percentage: Float,
    val color: Color,
    val change: String
)


@Composable
fun MobileDashboardHomeMonthlyStatisticsComponent() {

    var selectedMonth by remember { mutableStateOf("Ocak") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SkyFitMonthPickerDropdownComponent(
            selectedMonth = selectedMonth,
            onMonthSelected = { selectedMonth = it }
        )

        Spacer(modifier = Modifier.height(12.dp))
        MobileDashboardHomeWorkoutStatistics(
            title = "Tamamlanan antrenmanlar",
            count = 32,
            percentage = "0%",
            lineColor = Color.Cyan,
            dataPoints = listOf(2, 5, 8, 6, 14, 9, 10) // Sample Data
        )
        Spacer(modifier = Modifier.height(16.dp))
        MobileDashboardHomeWorkoutStatistics(
            title = "Bu haftaki antrenmanlar",
            count = 7,
            change = "+4",
            lineColor = Color(0xFFFFC107),
            dataPoints = listOf(1, 3, 6, 4, 12, 7, 8) // Sample Data
        )
    }
}

@Composable
private fun MobileDashboardHomeWorkoutStatistics(
    title: String,
    count: Int,
    percentage: String? = null,
    change: String? = null,
    lineColor: Color,
    dataPoints: List<Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = count.toString(), fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                if (percentage != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = percentage, fontSize = 12.sp, color = Color.Gray)
                }
                if (change != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(Color.Red.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = change, fontSize = 12.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        MobileDashboardHomeLineChart(dataPoints = dataPoints, color = lineColor)
    }
}


@Composable
fun MobileDashboardHomeLineChart(dataPoints: List<Int>, color: Color) {
    Canvas(modifier = Modifier.size(120.dp, 50.dp)) {
        val maxValue = dataPoints.maxOrNull() ?: 1
        val stepX = size.width / (dataPoints.size - 1)
        val stepY = size.height / maxValue.toFloat()

        val path = Path().apply {
            moveTo(0f, size.height - (dataPoints[0] * stepY))

            for (i in 1 until dataPoints.size) {
                val x1 = (i - 1) * stepX
                val y1 = size.height - (dataPoints[i - 1] * stepY)
                val x2 = i * stepX
                val y2 = size.height - (dataPoints[i] * stepY)

                cubicTo(
                    x1 + stepX / 2, y1,
                    x2 - stepX / 2, y2,
                    x2, y2
                )
            }
        }

        val fillPath = Path().apply {
            addPath(path)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = fillPath,
            color = color.copy(alpha = 0.3f) // Semi-transparent fill
        )

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun MobileDashboardHomeProgressRowsComponent() {
    val progressData = listOf(
        HomeGridProgressItem(Icons.Outlined.Person, "Adımlar", "5902"),
        HomeGridProgressItem(Icons.Outlined.Place, "Koşulan mesafe", "2.1 km"),
        HomeGridProgressItem(Icons.Outlined.Warning, "Su miktarı", "5L"),
        HomeGridProgressItem(Icons.Outlined.Notifications, "Tamamlanan antrenman", "1/3"),
        HomeGridProgressItem(Icons.Outlined.Face, "Yakılan kalori", "3201 kcal"),
        HomeGridProgressItem(Icons.Outlined.DateRange, "Aktiflik", "54 dk")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Adjusted spacing for uniformity
    ) {
        for (i in progressData.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp) // Adjusted spacing
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Ensures square-like equal heights
                ) {
                    MobileDashboardHomeProgressCard(progressData[i])
                }
                if (i + 1 < progressData.size) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        MobileDashboardHomeProgressCard(progressData[i + 1])
                    }
                }
            }
        }
    }
}


@Composable
private fun MobileDashboardHomeProgressCard(item: HomeGridProgressItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
        Text(
            text = item.title,
            style = SkyFitTypography.bodyMediumRegular,
            color = SkyFitColor.text.secondary
        )

        Text(
            text = item.value,
            style = SkyFitTypography.heading5
        )
    }
}

private data class HomeGridProgressItem(val icon: ImageVector, val title: String, val value: String)

@Composable
fun MobileDashboardHomeDailyExerciseGoalsComponent() {
    val exerciseGoals = listOf(
        HomeExerciseComponentItem("Üst vücut antrenmanı", "Tamamlandı!", isCompleted = true),
        HomeExerciseComponentItem("Alt vücut antrenmanı", "3 set x 15 tekrar", isCompleted = false),
        HomeExerciseComponentItem("Karın & Core", "3 set x 1 dakika", isCompleted = false)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Günlük antrenman hedeflerim",
            style = SkyFitTypography.bodyMediumSemibold
        )
        exerciseGoals.forEach { goal ->
            MobileDashboardHomeExerciseCard(goal)
        }
    }
}

@Composable
private fun MobileDashboardHomeExerciseCard(goal: HomeExerciseComponentItem) {
    val backgroundColor = if (goal.isCompleted) SkyFitColor.background.surfaceSecondary else SkyFitColor.specialty.buttonBgRest
    val textColor = if (goal.isCompleted) SkyFitColor.text.default else SkyFitColor.text.inverse

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = goal.title,
                style = SkyFitTypography.bodyLarge,
                color = textColor
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = goal.details,
                style = SkyFitTypography.bodySmallSemibold,
                color = textColor
            )
        }

        if (goal.isCompleted) {
            GoalCompletedBadge("💪 Bravo", Color.Black)
        } else {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Basla",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Micro,
                state = ButtonState.Disabled
            )
        }
    }
}

@Composable
private fun GoalCompletedBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, style = SkyFitTypography.bodySmall)
    }
}

data class HomeExerciseComponentItem(
    val title: String,
    val details: String,
    val isCompleted: Boolean = false
)

@Composable
fun MobileDashboardHomeMealGoalsComponent() {
    val mealGoals = listOf(
        HomeMealGoal(
            "Yogurt Granola Bowl",
            "12 gün kahvaltıda",
            "https://ik.imagekit.io/skynet2skyfit/meal_yogurt_granola.png?updatedAt=1738866100967",
            isSelected = true
        ),
        HomeMealGoal(
            "Peynirli Salata",
            "7 gün sadece kahvaltıda",
            "https://ik.imagekit.io/skynet2skyfit/meal_yogurt_granola.png?updatedAt=1738866100967",
            isSelected = false
        ),
        HomeMealGoal(
            "Tavuklu Salata",
            "12 gün sadece akşam yemeğinde",
            "https://ik.imagekit.io/skynet2skyfit/meal_yogurt_granola.png?updatedAt=1738866100967",
            isSelected = false
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Öğünler",
            style = SkyFitTypography.bodyMediumSemibold
        )

        mealGoals.forEach { meal ->
            MobileDashboardHomeMealCard(meal)
        }
    }
}

@Composable
private fun MobileDashboardHomeMealCard(meal: HomeMealGoal) {
    val backgroundColor = if (meal.isSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary
    val textColor = if (meal.isSelected) SkyFitColor.text.inverse else SkyFitColor.text.default

    Box(Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = meal.title,
                    style = SkyFitTypography.bodySmall,
                    color = textColor
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = meal.details,
                    style = SkyFitTypography.bodyLargeMedium,
                    color = textColor
                )
            }

            Spacer(Modifier.width(12.dp))

            AsyncImage(
                model = meal.imageUrl,
                contentDescription = "Meal",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape)
            )
        }

        if (meal.isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .background(Color.Cyan, CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
            )
        }
    }
}

private data class HomeMealGoal(val title: String, val details: String, val imageUrl: String, val isSelected: Boolean)


@Composable
fun MobileDashboardHomeFeaturedExercisesComponent(
    featuredExercises: List<HomeExerciseComponentItem> = listOf(
        HomeExerciseComponentItem("Alt vücut antrenmanı", "3 set x 15 tekrar"),
        HomeExerciseComponentItem("Karın & Core", "3 set x 1 dakika"),
        HomeExerciseComponentItem("Karın & Core", "3 set x 1 dakika")
    )
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "İlgini çekebilecek antrenmanlar",
            style = SkyFitTypography.bodyMediumSemibold
        )

        featuredExercises.forEach { exercise ->
            MobileDashboardHomeExerciseCard(exercise)
        }
    }
}

@Composable
fun MobileDashboardHomeFeaturedTrainersComponent() {
    val featuredTrainers = listOf(
        HomeFeaturedTrainer("Emily Rivera", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
        HomeFeaturedTrainer("David Thompson", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
        HomeFeaturedTrainer("Michael Foster", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "İlgini çekebilecek antrenörler",
            style = SkyFitTypography.bodyMediumSemibold
        )

        featuredTrainers.forEach { trainer ->
            MobileDashboardHomeFeaturedTrainerCard(trainer)
        }
    }
}

@Composable
private fun MobileDashboardHomeFeaturedTrainerCard(
    trainer: HomeFeaturedTrainer,
    onClickAdd: () -> Unit = {}
) {
    SkyFitListItemCardComponent(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        AsyncImage(
            model = trainer.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = trainer.name,
            style = SkyFitTypography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Ekle",
            onClick = onClickAdd,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

private data class HomeFeaturedTrainer(val name: String, val imageUrl: String)


@Composable
fun MobileDashboardHomeFacilityStatisticsComponent() {
    Column(
        modifier = Modifier
            .size(320.dp, 544.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        MobileDashboardHomeFacilityStatisticsOverview()
        Spacer(modifier = Modifier.height(16.dp))
        MobileDashboardHomeFacilityGraph()
    }
}

@Composable
private fun MobileDashboardHomeFacilityStatisticsOverview() {
    val stats = listOf(
        HomeFacilityStat("Aktif Üye", "327", "+53%", true),
        HomeFacilityStat("Aktif Dersler", "12", "0%", null),
        HomeFacilityStat("SkyFit Kazancın", "₺3120", "+53%", true),
        HomeFacilityStat("Profil Görüntülenmesi", "213", "-2%", false)
    )

    FacilityGridStatsLayout(stats)
}

@Composable
private fun FacilityGridStatsLayout(stats: List<HomeFacilityStat>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(0, 2).forEach { MobileDashboardHomeFacilityStatCard(it) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(2, 4).forEach { MobileDashboardHomeFacilityStatCard(it) }
        }
    }
}

@Composable
private fun MobileDashboardHomeFacilityStatCard(stat: HomeFacilityStat) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stat.title, fontSize = 14.sp, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stat.value, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            stat.percentage?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (stat.isPositive == true) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = it, fontSize = 12.sp, color = if (stat.isPositive == true) Color.Green else Color.Red)
                }
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeFacilityGraph() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Üye Değişimi Grafiği", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
//            TimeFilterSelector()
        }
        Spacer(modifier = Modifier.height(8.dp))
        MemberChangeLineChart(
            dataPoints = listOf(12, 7, 15, 20, 14, 8, 10),
            labels = listOf("Pzts", "Sal", "Çar", "Per", "Cum", "Cmrts", "Paz")
        )
    }
}

private data class HomeFacilityStat(val title: String, val value: String, val percentage: String?, val isPositive: Boolean?)
