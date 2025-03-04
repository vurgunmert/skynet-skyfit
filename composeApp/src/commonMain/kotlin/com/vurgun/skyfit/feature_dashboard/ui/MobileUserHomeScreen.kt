package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserHomeScreen(rootNavigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            MobileDashboardHomeToolbarComponent(
                onClickNotifications = {
                    rootNavigator.jumpAndStay(NavigationRoute.UserNotifications)
                },
                onClickMessages = {
                    rootNavigator.jumpAndStay(NavigationRoute.UserConversations)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileDashboardHomeCharacterProgressComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.DashboardProfile) }
            )

            Spacer(Modifier.height(24.dp))

            MobileDashboardHomeTrophiesBarComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.UserTrophies) }
            )

            MobileDashboardHomeWeekProgressComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeActivityCalendarComponent(
                onClickShowAll = { rootNavigator.jumpAndStay(NavigationRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeActivityHourlyCalendarComponent(
                onClickAdd = { rootNavigator.jumpAndStay(NavigationRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeUpcomingAppointmentsComponent(
                onClickShowAll = { rootNavigator.jumpAndStay(NavigationRoute.UserAppointments) }
            )

            MobileDashboardHomeGeneralStatisticsComponent()

            MobileDashboardHomeMonthlyStatisticsComponent()

            MobileDashboardHomeProgressGridComponent()

            MobileDashboardHomeDailyExerciseGoalsComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.UserExerciseDetail) }
            )

            MobileDashboardHomeMealGoalsComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.DashboardNutrition) }
            )

            MobileDashboardHomeFeaturedExercisesComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.UserExerciseDetail) }
            )

            MobileDashboardHomeFeaturedTrainersComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.TrainerProfileVisited) }
            )

            Spacer(Modifier.height(128.dp))
        }
    }
}