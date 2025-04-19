package com.vurgun.skyfit.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeToolbarComponent
import com.vurgun.skyfit.feature.home.component.MobileUserHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.ui.core.components.special.CharacterImage
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

    val appointments by viewModel.appointments.collectAsStateWithLifecycle()

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

            viewModel.characterType?.let { characterType ->
                CharacterImage(
                    characterType = characterType,
                    modifier = Modifier
                )
            }

            if (appointments.isNotEmpty()) {
                MobileUserHomeUpcomingAppointmentsComponent(
                    appointments = appointments,
                    onClickShowAll = goToAppointments
                )
            }
        }
    }
}