package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileDashboardHomeToolbarComponent(onNotifications: () -> Unit = {},
                                        onMessages: () -> Unit = {}) {
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
    TodoBox("DashboardHomeCharacterProgressComponent", Modifier.size(320.dp, 260.dp))
}

@Composable
fun MobileDashboardHomeTrophiesBarComponent() {
    TodoBox("DashboardHomeTrophiesBarComponent", Modifier.size(320.dp, 34.dp))
}

@Composable
fun MobileDashboardHomeWeekProgressComponent() {
    TodoBox("DashboardHomeWeekProgressComponent", Modifier.size(320.dp, 180.dp))
}

@Composable
fun MobileDashboardHomeActivityCalendarComponent() {
    TodoBox("DashboardHomeActivityCalendarComponent", Modifier.size(320.dp, 320.dp))
}

@Composable
fun MobileDashboardHomeActivityHourlyCalendarComponent() {
    TodoBox("DashboardHomeActivityHourlyCalendarComponent", Modifier.size(320.dp, 322.dp))
}

@Composable
fun MobileDashboardHomeUpcomingAppointmentsComponent() {
    TodoBox("DashboardHomeUpcomingAppointmentsComponent", Modifier.size(315.dp, 340.dp))
}

@Composable
fun MobileDashboardHomeGeneralStatisticsComponent() {
    TodoBox("DashboardHomeGeneralStatisticsComponent", Modifier.size(315.dp, 184.dp))
}

@Composable
fun MobileDashboardHomeMonthlyStatisticsComponent() {
    TodoBox("DashboardHomeMonthlyStatisticsComponent", Modifier.size(315.dp, 280.dp))
}

@Composable
fun MobileDashboardHomeProgressGridComponent() {
    TodoBox("DashboardHomeMonthlyStatisticsComponent", Modifier.size(320.dp, 412.dp))
}

@Composable
fun MobileDashboardHomeDailyExerciseGoalsComponent() {
    TodoBox("DashboardHomeDailyExerciseGoalsComponent", Modifier.size(320.dp, 316.dp))
}

@Composable
fun MobileDashboardHomeMealGoalsComponent() {
    TodoBox("DashboardHomeMealGoalsComponent", Modifier.size(320.dp, 358.dp))
}

@Composable
fun MobileDashboardHomeFeaturedExercisesComponent() {
    TodoBox("DashboardHomeFeaturedExercisesComponent", Modifier.size(320.dp, 316.dp))
}

@Composable
fun MobileDashboardHomeFeaturedTrainersComponent() {
    TodoBox("DashboardHomeFeaturedExercisesComponent", Modifier.size(320.dp, 280.dp))
}

@Composable
fun MobileDashboardHomeTrophyProfileComponent() {
    TodoBox("DashboardHomeFeaturedExercisesComponent", Modifier.size(98.dp, 112.dp))
}

@Composable
fun MobileDashboardHomeTrophyGridComponent() {
    TodoBox("DashboardHomeFeaturedExercisesComponent", Modifier.size(320.dp, 790.dp))
}

@Composable
fun MobileDashboardHomeTrainerStatisticsComponent() {
    TodoBox("DashboardHomeTrainerStatisticsComponent", Modifier.size(320.dp, 544.dp))
}

@Composable
fun MobileDashboardHomeTrainerNoClassComponent() {
    TodoBox("DashboardHomeTrainerNoClassComponent", Modifier.size(320.dp, 232.dp))
}

@Composable
fun MobileDashboardHomeTrainerClassScheduleComponent() {
    TodoBox("DashboardHomeTrainerClassScheduleComponent", Modifier.size(400.dp, 684.dp))
}

@Composable
fun MobileDashboardHomeAIAssistantComponent() {
    TodoBox("DashboardHomeAIAssistantComponent", Modifier.size(363.dp, 689.dp))
}

@Composable
fun MobileDashboardHomeAIAssistantHistoryComponent() {
    TodoBox("MobileDashboardHomeAIAssistantHistoryComponent", Modifier.size(363.dp, 689.dp))
}

@Composable
fun MobileDashboardHomeFacilityStatisticsComponent() {
    TodoBox("MobileDashboardHomeFacilityStatisticsComponent", Modifier.size(320.dp, 544.dp))
}

@Composable
fun MobileDashboardHomeFacilityNoClassComponent() {
    TodoBox("MobileDashboardHomeFacilityNoClassComponent", Modifier.size(320.dp, 232.dp))
}

