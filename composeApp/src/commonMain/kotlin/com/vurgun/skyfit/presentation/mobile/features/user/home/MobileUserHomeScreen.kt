package com.vurgun.skyfit.presentation.mobile.features.user.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserHomeScreen(rootNavigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileDashboardHomeToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileDashboardHomeCharacterProgressComponent()

            Spacer(Modifier.height(24.dp))

            MobileDashboardHomeTrophiesBarComponent()

            MobileDashboardHomeWeekProgressComponent()

            MobileDashboardHomeActivityCalendarComponent()

            MobileDashboardHomeActivityHourlyCalendarComponent()

            MobileDashboardHomeUpcomingAppointmentsComponent()

            MobileDashboardHomeGeneralStatisticsComponent()

            MobileDashboardHomeMonthlyStatisticsComponent()

            MobileDashboardHomeProgressGridComponent()

            MobileDashboardHomeDailyExerciseGoalsComponent()

            MobileDashboardHomeMealGoalsComponent()

            MobileDashboardHomeFeaturedExercisesComponent()

            MobileDashboardHomeFeaturedTrainersComponent()

            Spacer(Modifier.height(48.dp))
        }
    }
}