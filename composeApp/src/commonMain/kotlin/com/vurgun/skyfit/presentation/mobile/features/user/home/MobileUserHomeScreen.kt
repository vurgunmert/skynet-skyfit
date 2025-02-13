package com.vurgun.skyfit.presentation.mobile.features.user.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeProgressRowsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeToolbarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeTrophiesBarComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardHomeWeekProgressComponent
import com.vurgun.skyfit.presentation.mobile.features.user.messages.ChatBotButtonComponent
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserHomeScreen(rootNavigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            MobileDashboardHomeToolbarComponent(
                onClickNotifications = {
                    rootNavigator.jumpAndStay(SkyFitNavigationRoute.UserNotifications)
                },
                onClickMessages = {
                    rootNavigator.jumpAndStay(SkyFitNavigationRoute.UserConversations)
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
                onClickShowAll = { rootNavigator.jumpAndStay(SkyFitNavigationRoute.UserActivityCalendar) }
            )

            MobileDashboardHomeActivityHourlyCalendarComponent()

            MobileDashboardHomeUpcomingAppointmentsComponent()

            MobileDashboardHomeGeneralStatisticsComponent()

            MobileDashboardHomeMonthlyStatisticsComponent()

            MobileDashboardHomeProgressRowsComponent()

            MobileDashboardHomeDailyExerciseGoalsComponent()

            MobileDashboardHomeMealGoalsComponent()

            MobileDashboardHomeFeaturedExercisesComponent()

            MobileDashboardHomeFeaturedTrainersComponent()

            Spacer(Modifier.height(48.dp))
        }
    }
}