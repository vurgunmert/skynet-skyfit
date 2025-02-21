package com.vurgun.skyfit.presentation.mobile.features.user.home

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
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeActivityCalendarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeActivityHourlyCalendarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeCharacterProgressComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeDailyExerciseGoalsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeFeaturedExercisesComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeFeaturedTrainersComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeGeneralStatisticsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeMealGoalsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeMonthlyStatisticsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeProgressGridComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeToolbarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeTrophiesBarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeWeekProgressComponent
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
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
                .widthIn(max = MobileStyleGuide.screenWithMax)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileDashboardHomeCharacterProgressComponent()

            Spacer(Modifier.height(24.dp))

            MobileDashboardHomeTrophiesBarComponent()

            MobileDashboardHomeWeekProgressComponent()

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