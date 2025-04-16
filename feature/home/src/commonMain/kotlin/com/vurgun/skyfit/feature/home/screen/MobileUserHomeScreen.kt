package com.vurgun.skyfit.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeCharacterProgressComponent
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeToolbarComponent
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileUserHomeScreen(
    goToNotifications: () -> Unit,
    goToMessages: () -> Unit,
    goToExplore: () -> Unit,
    goToSocial: () -> Unit,
    goToProfile: () -> Unit,
    goToActivityCalendar: () -> Unit,
    goToAppointments: () -> Unit,
    viewModel: UserHomeViewModel = koinViewModel()
) {
    val characterType = viewModel.characterType.collectAsState(null).value

    SkyFitMobileScaffold(
        topBar = {
            MobileDashboardHomeToolbarComponent(
                onClickNotifications = goToNotifications,
                onClickMessages = goToMessages
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            characterType?.let { characterType ->
                MobileDashboardHomeCharacterProgressComponent(
                    characterType = characterType,
                    onClick = goToProfile
                )
            }

            MobileDashboardHomeUpcomingAppointmentsComponent(
                appointments = viewModel.appointments,
                onClickShowAll = goToAppointments
            )
        }
    }
}