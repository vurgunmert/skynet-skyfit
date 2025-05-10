package com.vurgun.skyfit.feature.dashboard.home.mobile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.schedule.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.event.TrainerHomeLessonEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.feature.dashboard.component.EmptyFacilityAppointmentContent
import com.vurgun.skyfit.feature.dashboard.component.UpcomingAppointmentsTitle
import com.vurgun.skyfit.feature.dashboard.home.TrainerHomeAction
import com.vurgun.skyfit.feature.dashboard.home.TrainerHomeEffect
import com.vurgun.skyfit.feature.dashboard.home.TrainerHomeUiState
import com.vurgun.skyfit.feature.dashboard.home.TrainerHomeViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*
import kotlin.math.sign

class TrainerHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<TrainerHomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                TrainerHomeEffect.NavigateToAppointments -> {
                    appNavigator.push(SharedScreen.TrainerAppointmentListing)
                }

                TrainerHomeEffect.NavigateToConversations -> {
                    appNavigator.push(SharedScreen.Conversations)
                }

                TrainerHomeEffect.NavigateToNotifications -> {
                    appNavigator.push(SharedScreen.Notifications)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            is TrainerHomeUiState.Loading -> FullScreenLoaderContent()
            is TrainerHomeUiState.Error -> {
                val message = (uiState as TrainerHomeUiState.Error).message
                ErrorScreen(
                    message = message,
                    confirmText = stringResource(Res.string.refresh_action),
                    onConfirm = { viewModel.loadData() }
                )
            }

            is TrainerHomeUiState.Content -> {
                val content = (uiState as TrainerHomeUiState.Content)
                TrainerHomeCompact(content, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun TrainerHomeCompact(
    content: TrainerHomeUiState.Content,
    onAction: (TrainerHomeAction) -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            MobileHomeTopBar(
                notificationsEnabled = false,
                onClickNotifications = { onAction(TrainerHomeAction.OnClickNotifications) },
                conversationsEnabled = false,
                onClickConversations = { onAction(TrainerHomeAction.OnClickConversations) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CharacterImage(
                characterType = content.trainer.characterType,
                modifier = Modifier
            )

            Spacer(Modifier.height(24.dp))

            TrainerUpcomingAppointments(
                content.profile.gymId,
                appointments = content.appointments,
                onClickShowAll = { onAction(TrainerHomeAction.OnClickAppointments) },
                onClickAddLesson = {}
            )

            Spacer(Modifier.height(128.dp))
        }
    }
}

data class HomeTrainerStat(val title: String, val value: String, val percentage: String?, val isPositive: Boolean?)

@Composable
fun MobileDashboardHomeTrainerStatisticsComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        MobileDashboardHomeTrainerStatisticsOverview()
        Spacer(modifier = Modifier.height(16.dp))
        MobileDashboardHomeTrainerGraph()
    }
}

@Composable
private fun MobileDashboardHomeTrainerStatisticsOverview() {
    TrainerGridStatsLayout(
        follower = HomeTrainerStat("Takipçi", "327", "+53%", true),
        activeClasses = HomeTrainerStat("Aktif Dersler", "12", "0%", null),
        earning = HomeTrainerStat("SkyFit Kazancın", "₺3120", "+53%", true),
        visits = HomeTrainerStat("Profil Görüntülenmesi", "213", "-2%", false)
    )
}

@Composable
private fun TrainerGridStatsLayout(
    follower: HomeTrainerStat,
    activeClasses: HomeTrainerStat,
    earning: HomeTrainerStat,
    visits: HomeTrainerStat
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MobileDashboardHomeStatCard(
                stat = follower,
                modifier = Modifier
                    .weight(1f)
                    .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            )
            MobileDashboardHomeStatCard(
                stat = activeClasses,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MobileDashboardHomeStatCard(
                stat = earning,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            MobileDashboardHomeStatCard(
                stat = visits,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun MobileDashboardHomeStatCard(stat: HomeTrainerStat, modifier: Modifier) {

    Column(modifier = modifier) {
        Text(
            text = stat.title,
            style = SkyFitTypography.bodyMediumMedium,
            color = SkyFitColor.text.secondary
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stat.value,
                style = SkyFitTypography.heading5
            )

            stat.percentage?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (stat.isPositive == true) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(
                                alpha = 0.2f
                            )
                        )
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
    var selectedPeriod by remember { mutableStateOf("1A") } // Default period
    val dataPoints = when (selectedPeriod) {
        "Y" -> listOf(30, 40, 35, 50, 55, 45, 60) // Example yearly data
        "6A" -> listOf(25, 30, 28, 33, 40, 35, 45) // Example 6-month data
        "3A" -> listOf(20, 22, 18, 27, 30, 25, 35) // Example 3-month data
        else -> listOf(12, 7, 15, 20, 14, 8, 10) // Default 1A (1-week) data
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Üye Değişimi Grafiği",
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TimeFilterSelector(selectedPeriod) { selectedPeriod = it }
        }
        Spacer(modifier = Modifier.height(8.dp))
        MemberChangeLineChart(
            dataPoints = dataPoints,
            labels = listOf("Pzts", "Sal", "Çar", "Per", "Cum", "Cmrts", "Paz")
        )
    }
}

@Composable
private fun TimeFilterSelector(selectedPeriod: String, onPeriodChange: (String) -> Unit) {
    Row {
        listOf("Y", "6A", "3A", "1A").forEach { period ->
            Text(
                text = period,
                color = if (selectedPeriod == period) Color.White else Color.Gray,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onPeriodChange(period) }
            )
        }
    }
}

@Composable
fun MemberChangeLineChart(dataPoints: List<Int>, labels: List<String>) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        val maxValue = dataPoints.maxOrNull() ?: 1
        val stepX = size.width / (dataPoints.size - 1)
        val stepY = size.height / maxValue.toFloat()
        val horizontalSteps = 3 // Number of horizontal lines

        // Draw horizontal grid lines with text labels
        for (i in 0..horizontalSteps) {
            val y = size.height - (i * size.height / horizontalSteps)

            drawLine(
                color = Color.Gray.copy(alpha = 0.4f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.dp.toPx()
            )

            // Draw text using `TextMeasurer`
            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString("${(i * maxValue / horizontalSteps)}"),
                style = TextStyle(color = Color.White, fontSize = 12.sp)
            )

            drawText(
                textLayoutResult,
                topLeft = Offset(10f, y - textLayoutResult.size.height / 2) // Center align with grid line
            )
        }

        // Smooth path for the chart using cubic Bézier curves only at turning points
        val path = Path().apply {
            moveTo(0f, size.height - (dataPoints[0] * stepY))

            for (i in 1 until dataPoints.size) {
                val x1 = (i - 1) * stepX
                val y1 = size.height - (dataPoints[i - 1] * stepY)
                val x2 = i * stepX
                val y2 = size.height - (dataPoints[i] * stepY)

                if (i > 1 && i < dataPoints.size - 1) {
                    // Detect turning points (peaks/valleys)
                    val prevDiff = dataPoints[i - 1] - dataPoints[i - 2]
                    val nextDiff = dataPoints[i] - dataPoints[i - 1]

                    if (prevDiff.sign != nextDiff.sign) {
                        // Smooth arc at turning points
                        cubicTo(
                            x1 + stepX / 2, y1,
                            x2 - stepX / 2, y2,
                            x2, y2
                        )
                        continue
                    }
                }

                // Default: Straight line for normal segments
                lineTo(x2, y2)
            }
        }

        drawPath(
            path,
            color = Color.Cyan,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }

    // Labels under the graph
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        labels.forEach { label ->
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun TrainerUpcomingAppointments(
    assignedFacilityId: Int?,
    appointments: List<LessonSessionItemViewData>,
    onClickShowAll: () -> Unit = {},
    onClickAddLesson: (facilityId: Int) -> Unit = {},
) {

    if (appointments.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            UpcomingAppointmentsTitle(onClickShowAll)

            appointments.forEach { appointment ->
                Spacer(modifier = Modifier.height(8.dp))
                TrainerHomeLessonEventItem(
                    title = appointment.title,
                    iconId = appointment.iconId,
                    date = appointment.date.toString(),
                    timePeriod = appointment.hours.toString(),
                    facility = appointment.facility.toString(),
                    onClick = onClickShowAll
                )
            }
        }
    } else {
        EmptyFacilityAppointmentContent(
            assignedFacilityId = assignedFacilityId,
            onClickAdd = onClickAddLesson
        )
    }
}




//TODO: REMOVE
@Composable
fun MobileDashboardHomeTrainerClassScheduleComponent() {
    val classList = listOf(
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Pilates", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Core", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Skipping Rope", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Push-Ups", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Kişisel Kuvvet Antrenmanı", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Skipping Rope", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım"),
        HomeTrainerClass("Push-Ups", Res.drawable.ic_dots_vertical, "07:00-08:00", "22 Kasım")
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        SearchAndFilterBar()
        Spacer(modifier = Modifier.height(8.dp))
        HomeClassScheduleTable(classList)
    }
}

@Composable
private fun SearchAndFilterBar() {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .background(
                SkyFitColor.background.surfaceSecondaryHover,
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Tabloda ara", color = SkyFitColor.text.secondary) },
                singleLine = true,
                textStyle = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier
                    .width(280.dp)
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(50))
                    .border(1.dp, SkyFitColor.border.secondaryButton, RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.weight(1f))

            Row(
                Modifier
                    .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = "Search",
                    tint = SkyFitColor.icon.inverseSecondary,
                    modifier = Modifier.size(16.dp)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_dots_vertical),
                    contentDescription = "Filter",
                    tint = SkyFitColor.icon.inverseSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
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
            .border(1.dp, SkyFitColor.border.secondaryButton, RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Text(text = label, style = SkyFitTypography.bodySmallSemibold)
    }
}

@Composable
private fun HomeClassScheduleTable(classList: List<HomeTrainerClass>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {}, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("Etkinliğin Adı", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.weight(1f))
            Text("Saat", fontSize = 14.sp, color = Color.Gray, modifier = Modifier)
            Text("Tarih", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.width(80.dp))
        }

        // Show at most 9 rows
        val limitedClassList = classList.take(9)

        limitedClassList.forEach { trainerClass ->
            HomeClassScheduleRow(trainerClass)
        }
    }
}


@Composable
private fun HomeClassScheduleRow(trainerClass: HomeTrainerClass) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = false, onCheckedChange = {},
            modifier = Modifier.size(24.dp),
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.Cyan,
                uncheckedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(trainerClass.icon),
                contentDescription = trainerClass.name,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = trainerClass.name,
                style = SkyFitTypography.bodyXSmallSemibold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = trainerClass.time,
            style = SkyFitTypography.bodyXSmallSemibold,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = trainerClass.date,
            style = SkyFitTypography.bodyXSmallSemibold,
            maxLines = 1,
        )
    }
}

private data class HomeTrainerClass(
    val name: String,
    val icon: DrawableResource,
    val time: String,
    val date: String
)
