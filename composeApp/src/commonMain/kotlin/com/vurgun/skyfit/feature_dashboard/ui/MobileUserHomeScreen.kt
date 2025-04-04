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
import com.vurgun.skyfit.core.domain.models.UserDetail
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserHomeScreen(
    userDetail: UserDetail,
    rootNavigator: Navigator
) {

    SkyFitScaffold(
        topBar = {
            MobileDashboardHomeToolbarComponent(
                onClickNotifications = {
                    rootNavigator.jumpAndStay(MobileNavRoute.UserNotifications)
                },
                onClickMessages = {
                    rootNavigator.jumpAndStay(MobileNavRoute.UserConversations)
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

            userDetail.characterType?.let { characterType ->
                MobileDashboardHomeCharacterProgressComponent(
                    characterType = characterType,
                    onClick = { rootNavigator.jumpAndStay(MobileNavRoute.DashboardProfile) }
                )
            }
            Spacer(Modifier.height(24.dp))

            MobileDashboardHomeTrophiesBarComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.UserTrophies) }
            )

            MobileDashboardHomeWeekProgressComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeActivityCalendarComponent(
                onClickShowAll = { rootNavigator.jumpAndStay(MobileNavRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeActivityHourlyCalendarComponent(
                onClickAdd = { rootNavigator.jumpAndStay(MobileNavRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeUpcomingAppointmentsComponent(
                onClickShowAll = { rootNavigator.jumpAndStay(MobileNavRoute.UserAppointments) }
            )

            MobileDashboardHomeGeneralStatisticsComponent()

            MobileDashboardHomeMonthlyStatisticsComponent()

            MobileDashboardHomeProgressGridComponent()

            MobileDashboardHomeDailyExerciseGoalsComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.ExerciseDetail) }
            )

            MobileDashboardHomeMealGoalsComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.DashboardNutrition) }
            )

            MobileDashboardHomeFeaturedExercisesComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.ExerciseDetail) }
            )

            MobileDashboardHomeFeaturedTrainersComponent(
                onClick = { rootNavigator.jumpAndStay(MobileNavRoute.TrainerProfileVisited) }
            )

            Spacer(Modifier.height(128.dp))
        }
    }
}