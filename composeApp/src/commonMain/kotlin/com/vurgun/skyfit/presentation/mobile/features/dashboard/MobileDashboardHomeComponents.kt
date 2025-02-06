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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ThumbUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityHourlyCalendarComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserTrophyItemComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitCalendarGridComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.now
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
fun MobileDashboardHomeCharacterProgressComponent() {
    val progressItems = listOf(
        ProgressItem(Icons.Outlined.Warning, 0.7f), // Steps
        ProgressItem(Icons.Outlined.Place, 0.5f), // Distance
        ProgressItem(Icons.Outlined.Favorite, 0.8f), // Calories
        ProgressItem(Icons.Outlined.DateRange, 0.4f), // Water Intake
        ProgressItem(Icons.Outlined.ThumbUp, 0.6f), // Activity Time
        ProgressItem(Icons.Outlined.Call, 0.5f) // Strength Training
    )

    Box(
        modifier = Modifier
            .size(320.dp, 260.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Centered Character Image
        AsyncImage(
            model = "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
        )

        // Circular Progress Icons around the character
        progressItems.forEachIndexed { index, progressItem ->
            val angle = index * (360f / progressItems.size)
            val position = polarToCartesian(120f, angle) // Position icons in a circular pattern

            Box(
                modifier = Modifier
                    .offset(x = position.x.dp, y = position.y.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                HomeCharacterCircularProgress(progressItem)
            }
        }
    }
}

@Composable
private fun HomeCharacterCircularProgress(progressItem: ProgressItem) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(Color.Black, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(36.dp)) {
            drawArc(
                color = Color.Cyan,
                startAngle = -90f,
                sweepAngle = progressItem.progress * 360f,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Icon(imageVector = progressItem.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
    }
}

private fun polarToCartesian(radius: Float, angle: Float): Offset {
    val radians = (angle * PI / 180).toFloat() // Convert degrees to radians
    return Offset(
        x = radius * cos(radians),
        y = radius * sin(radians)
    )
}

private data class ProgressItem(val icon: ImageVector, val progress: Float)

@Composable
fun MobileDashboardHomeTrophiesBarComponent() {
    var userTrophies = listOf(1, 2, 3)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // Center items
        ) {
            items(userTrophies) {
                Box(modifier = Modifier.padding(horizontal = 8.dp)) { // Half of 16.dp spacing
                    MobileUserTrophyItemComponent()
                }
            }
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
            .width(320.dp)
            .height(180.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
fun MobileDashboardHomeActivityHourlyCalendarComponent() {
    Column(Modifier.fillMaxWidth().height(400.dp)) {
        Row {
            Text("18 Haziran Cumartesi", modifier = Modifier.weight(1f))
            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "+ Ekle",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }
        MobileUserActivityHourlyCalendarComponent()
    }
}

@Composable
fun MobileDashboardHomeUpcomingAppointmentsComponent() {
    val appointments = listOf(
        HomeAppointment("Kişisel kuvvet antrenmanı", "08:00", "@ironstudio"),
        HomeAppointment("Kişisel kuvvet antrenmanı", "08:00", "@ironstudio"),
        HomeAppointment("Kişisel kuvvet antrenmanı", "08:00", "@ironstudio")
    )

    Column(
        modifier = Modifier
            .size(315.dp, 340.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Yaklaşan Randevular",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Hepsini Görüntüle",
                fontSize = 14.sp,
                color = Color.Cyan,
                modifier = Modifier.clickable { /* TODO: Navigate to all appointments */ }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(appointments) { appointment ->
                MobileDashboardHomeAppointmentCard(appointment)
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeAppointmentCard(appointment: HomeAppointment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Workout", tint = Color.White)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = appointment.title, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
        }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Details",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

private data class HomeAppointment(val title: String, val time: String, val location: String)


@Composable
fun MobileDashboardHomeGeneralStatisticsComponent() {
    val data = listOf(
        MobileDashboardStatisticData("Yakılan kaloriler", 33.4f, Color.Cyan, "+1,25"),
        MobileDashboardStatisticData("Proteinler", 23.02f, Color.Magenta, "+3,42"),
        MobileDashboardStatisticData("Karbonhidratlar", 11.24f, Color(0xFFE57373), "+2,12")
    )

    Row(
        modifier = Modifier
            .size(315.dp, 184.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        MobileDashboardHomeCircularStatisticsChart(data = data, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        MobileDashboardHomeStatisticsLegend(data)
    }
}

@Composable
private fun MobileDashboardHomeCircularStatisticsChart(data: List<MobileDashboardStatisticData>, modifier: Modifier = Modifier) {
    val total = data.sumOf { it.percentage.toDouble() }.toFloat()
    val sweepAngles = data.map { (it.percentage / total) * 360f }

    Canvas(
        modifier = modifier.size(120.dp)
    ) {
        var startAngle = -90f
        sweepAngles.forEachIndexed { index, angle ->
            drawArc(
                color = data[index].color,
                startAngle = startAngle,
                sweepAngle = angle,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
            startAngle += angle
        }
    }
}

@Composable
private fun MobileDashboardHomeStatisticsLegend(data: List<MobileDashboardStatisticData>) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        data.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(item.color, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${item.percentage}%",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.change,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (item.change.contains("+")) Color.Green else Color.Red
                )
            }
        }
    }
}

private data class MobileDashboardStatisticData(
    val title: String,
    val percentage: Float,
    val color: Color,
    val change: String
)


@Composable
fun MobileDashboardHomeMonthlyStatisticsComponent() {
    Column(
        modifier = Modifier
            .size(315.dp, 280.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        MobileDashboardHomeMonthSelector()
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
private fun MobileDashboardHomeMonthSelector() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar", tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Haziran", fontSize = 14.sp, color = Color.White)
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color.White)
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
private fun MobileDashboardHomeLineChart(dataPoints: List<Int>, color: Color) {
    Canvas(modifier = Modifier.size(120.dp, 50.dp)) {
        val maxValue = dataPoints.maxOrNull() ?: 1
        val stepX = size.width / (dataPoints.size - 1)
        val stepY = size.height / maxValue.toFloat()

        val path = Path().apply {
            moveTo(0f, size.height - (dataPoints[0] * stepY))
            dataPoints.forEachIndexed { index, value ->
                lineTo(index * stepX, size.height - (value * stepY))
            }
        }

        drawPath(path, color = color, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
    }
}


@Composable
fun MobileDashboardHomeProgressGridComponent() {
    val progressData = listOf(
        HomeGridProgressItem(Icons.Outlined.Person, "Adımlar", "5902"),
        HomeGridProgressItem(Icons.Outlined.Place, "Koşulan mesafe", "2.1 km"),
        HomeGridProgressItem(Icons.Outlined.Warning, "Su miktarı", "5L"),
        HomeGridProgressItem(Icons.Outlined.Notifications, "Tamamlanan antrenman", "1/3"),
        HomeGridProgressItem(Icons.Outlined.Face, "Yakılan kalori", "3201 kcal"),
        HomeGridProgressItem(Icons.Outlined.DateRange, "Aktiflik", "54 dk")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .size(320.dp, 412.dp)
            .background(Color.Black)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(progressData) { item ->
            MobileDashboardHomeProgressCard(item)
        }
    }
}

@Composable
private fun MobileDashboardHomeProgressCard(item: HomeGridProgressItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Icon(imageVector = item.icon, contentDescription = null, tint = Color.White)
        Column {
            Text(text = item.title, fontSize = 14.sp, color = Color.Gray)
            Text(text = item.value, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

private data class HomeGridProgressItem(val icon: ImageVector, val title: String, val value: String)

@Composable
fun MobileDashboardHomeDailyExerciseGoalsComponent() {
    val exerciseGoals = listOf(
        ExerciseGoal("Üst vücut antrenmanı", "Tamamlandı!", isCompleted = true),
        ExerciseGoal("Alt vücut antrenmanı", "3 set x 15 tekrar", isCompleted = false),
        ExerciseGoal("Karın & Core", "3 set x 1 dakika", isCompleted = false)
    )

    Column(
        modifier = Modifier
            .size(320.dp, 316.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Günlük antrenman hedeflerim",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        exerciseGoals.forEach { goal ->
            Spacer(Modifier.height(8.dp))
            MobileDashboardHomeExerciseCard(goal)
        }
    }
}

@Composable
private fun MobileDashboardHomeExerciseCard(goal: ExerciseGoal) {
    val backgroundColor = if (goal.isCompleted) Color.DarkGray else Color.Cyan.copy(alpha = 0.8f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = goal.title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = goal.details, fontSize = 14.sp, color = Color.DarkGray)
        }

        if (goal.isCompleted) {
            GoalCompletedBadge("💪 Bravo", Color.Black)
        } else {
            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "Basla",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Micro,
                initialState = ButtonState.Disabled
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
        Text(text, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

private data class ExerciseGoal(val title: String, val details: String, val isCompleted: Boolean)

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
            .size(320.dp, 358.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Öğünler",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mealGoals) { meal ->
                MobileDashboardHomeMealCard(meal)
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeMealCard(meal: HomeMealGoal) {
    val backgroundColor = if (meal.isSelected) Color.Cyan.copy(alpha = 0.8f) else Color.DarkGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = meal.title, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = meal.details, fontSize = 14.sp, color = Color.Gray)
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .clickable { /* TODO: Handle selection */ },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }

        if (meal.isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Cyan, CircleShape)
                    .padding(4.dp)
            )
        }
    }
}

private data class HomeMealGoal(val title: String, val details: String, val imageUrl: String, val isSelected: Boolean)


@Composable
fun MobileDashboardHomeFeaturedExercisesComponent() {
    val featuredExercises = listOf(
        FeaturedExercise("Alt vücut antrenmanı", "3 set x 15 tekrar"),
        FeaturedExercise("Karın & Core", "3 set x 1 dakika"),
        FeaturedExercise("Karın & Core", "3 set x 1 dakika")
    )

    Column(
        modifier = Modifier
            .size(320.dp, 316.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "İlgini çekebilecek antrenmanlar",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(featuredExercises) { exercise ->
                MobileDashboardHomeFeaturedExerciseCard(exercise)
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeFeaturedExerciseCard(exercise: FeaturedExercise) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Cyan.copy(alpha = 0.8f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = exercise.title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = exercise.details, fontSize = 14.sp, color = Color.DarkGray)
        }

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Ekle",
            onClick = { },
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            initialState = ButtonState.Disabled
        )
    }
}

private data class FeaturedExercise(val title: String, val details: String)

@Composable
fun MobileDashboardHomeFeaturedTrainersComponent() {
    val featuredTrainers = listOf(
        HomeFeaturedTrainer("Emily Rivera", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
        HomeFeaturedTrainer("David Thompson", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
        HomeFeaturedTrainer("Michael Foster", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680")
    )

    Column(
        modifier = Modifier
            .size(320.dp, 280.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "İlgini çekebilecek antrenörler",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(featuredTrainers) { trainer ->
                MobileDashboardHomeFeaturedTrainerCard(trainer)
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeFeaturedTrainerCard(trainer: HomeFeaturedTrainer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = trainer.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = trainer.name, fontSize = 16.sp, color = Color.White)
        }

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Ekle",
            onClick = { },
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            initialState = ButtonState.Disabled
        )
    }
}

private data class HomeFeaturedTrainer(val name: String, val imageUrl: String)

@Composable
fun MobileDashboardHomeTrainerStatisticsComponent() {
    Column(
        modifier = Modifier
            .size(320.dp, 544.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        MobileDashboardHomeTrainerStatisticsOverview()
        Spacer(modifier = Modifier.height(16.dp))
        MobileDashboardHomeTrainerGraph()
    }
}

@Composable
private fun MobileDashboardHomeTrainerStatisticsOverview() {
    val stats = listOf(
        HomeTrainerStat("Takipçi", "327", "+53%", true),
        HomeTrainerStat("Aktif Dersler", "12", "0%", null),
        HomeTrainerStat("SkyFit Kazancın", "₺3120", "+53%", true),
        HomeTrainerStat("Profil Görüntülenmesi", "213", "-2%", false)
    )

    TrainerGridStatsLayout(stats)
}

@Composable
private fun TrainerGridStatsLayout(stats: List<HomeTrainerStat>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(0, 2).forEach { MobileDashboardHomeStatCard(it) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(2, 4).forEach { MobileDashboardHomeStatCard(it) }
        }
    }
}

@Composable
private fun MobileDashboardHomeStatCard(stat: HomeTrainerStat) {
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
private fun MobileDashboardHomeTrainerGraph() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Üye Değişimi Grafiği", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
            TimeFilterSelector()
        }
        Spacer(modifier = Modifier.height(8.dp))
        MobileDashboardLineChart(
            dataPoints = listOf(12, 7, 15, 20, 14, 8, 10),
            labels = listOf("Pzts", "Sal", "Çar", "Per", "Cum", "Cmrts", "Paz")
        )
    }
}

@Composable
private fun TimeFilterSelector() {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        listOf("Y", "6A", "3A", "1A", "H").forEach { label ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (label == "H") Color.Cyan else Color.DarkGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = label, fontSize = 12.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun MobileDashboardLineChart(dataPoints: List<Int>, labels: List<String>) {
    Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        val maxValue = dataPoints.maxOrNull() ?: 1
        val stepX = size.width / (dataPoints.size - 1)
        val stepY = size.height / maxValue.toFloat()

        val path = Path().apply {
            moveTo(0f, size.height - (dataPoints[0] * stepY))
            dataPoints.forEachIndexed { index, value ->
                lineTo(index * stepX, size.height - (value * stepY))
            }
        }

        drawPath(path, color = Color.Cyan, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        labels.forEach { label ->
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

private data class HomeTrainerStat(val title: String, val value: String, val percentage: String?, val isPositive: Boolean?)

@Composable
fun MobileDashboardHomeTrainerNoClassComponent() {
    Box(
        modifier = Modifier
            .size(320.dp, 232.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Oluşturulmuş dersiniz bulunmamakta",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ders oluşturduğunuzda buradan görüntüleyebilirsiniz.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "Etkinlik Oluştur",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }

        // Search & Filter Buttons (Top Right)
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            IconButton(onClick = { /* TODO: Open Search */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Cyan)
            }
            IconButton(onClick = { /* TODO: Open Filters */ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Filter", tint = Color.Cyan)
            }
        }
    }
}


@Composable
fun MobileDashboardHomeTrainerClassScheduleComponent() {
    val classList = listOf(
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Pilates", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Core", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Skipping Rope", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Push-Ups", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Skipping Rope", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Push-Ups", Icons.Outlined.DateRange, "07:00-08:00", "22 Kasım")
    )

    Column(
        modifier = Modifier
            .size(400.dp, 684.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        SearchAndFilterBar()
        Spacer(modifier = Modifier.height(8.dp))
        HomeClassScheduleTable(classList)
    }
}

@Composable
private fun SearchAndFilterBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Tabloda ara", color = Color.Gray) },
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White
                )
            )

            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Cyan)
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Filter", tint = Color.Cyan)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HomeScheduleFilterChip("Aktif")
            HomeScheduleFilterChip("Pilates")
            HomeScheduleFilterChip("07:00-08:00")
            HomeScheduleFilterChip("22/11/2024")
        }
    }
}

@Composable
private fun HomeScheduleFilterChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Cyan)
    }
}

@Composable
private fun HomeClassScheduleTable(classList: List<HomeTrainerClass>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(checked = false, onCheckedChange = {})
                Text("Etkinliğin Adı", fontSize = 14.sp, color = Color.Gray)
                Text("Saat", fontSize = 14.sp, color = Color.Gray)
                Text("Tarih", fontSize = 14.sp, color = Color.Gray)
            }
        }

        items(classList) { trainerClass ->
            HomeClassScheduleRow(trainerClass)
        }
    }
}

@Composable
private fun HomeClassScheduleRow(trainerClass: HomeTrainerClass) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = false, onCheckedChange = {},
            colors = CheckboxDefaults.colors(
                checkmarkColor = SkyFitColor.specialty.buttonBgRest,
                uncheckedColor = SkyFitColor.specialty.buttonBgDisabled
            )
        )

        Row(
            modifier = Modifier.weight(1f), // Allowing proper width distribution
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = trainerClass.icon,
                contentDescription = trainerClass.name,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = trainerClass.name,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 1, // Restrict to one line
                overflow = TextOverflow.Ellipsis, // Add ellipsis when text overflows
                modifier = Modifier.weight(1f) // Ensuring text can take available space
            )
        }

        Text(
            text = trainerClass.time,
            fontSize = 14.sp,
            color = Color.Gray,
            softWrap = true,
            modifier = Modifier.wrapContentWidth()
        )

        Text(
            text = trainerClass.date,
            fontSize = 14.sp,
            color = Color.Gray,
            softWrap = true,
            modifier = Modifier.wrapContentWidth()
        )
    }
}

private data class HomeTrainerClass(val name: String, val icon: ImageVector, val time: String, val date: String)


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
            TimeFilterSelector()
        }
        Spacer(modifier = Modifier.height(8.dp))
        MobileDashboardLineChart(
            dataPoints = listOf(12, 7, 15, 20, 14, 8, 10),
            labels = listOf("Pzts", "Sal", "Çar", "Per", "Cum", "Cmrts", "Paz")
        )
    }
}

private data class HomeFacilityStat(val title: String, val value: String, val percentage: String?, val isPositive: Boolean?)


@Composable
fun MobileDashboardHomeFacilityNoClassComponent() {
    MobileDashboardHomeTrainerNoClassComponent()
}

