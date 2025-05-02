package com.vurgun.skyfit.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeToolbarComponent
import com.vurgun.skyfit.feature.home.component.MobileUserHomeUpcomingAppointmentsComponent

class UserHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<UserHomeViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserHomeEffect.NavigateToConversations -> {
                    appNavigator.push(SharedScreen.Conversations)
                }

                UserHomeEffect.NavigateToAppointments -> {
                    appNavigator.push(SharedScreen.UserAppointmentListing)
                }

                UserHomeEffect.NavigateToNotifications -> {
                    appNavigator.push(SharedScreen.Notifications)
                }
            }
        }

        if (windowSize == WindowSize.EXPANDED) {
            UserHomeCompact(viewModel) //TODO: Expanded
        } else {
            UserHomeCompact(viewModel)
        }
    }

}

@Composable
private fun UserHomeCompact(
    viewModel: UserHomeViewModel
) {
    val appointments by viewModel.appointments.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            MobileDashboardHomeToolbarComponent(
                onClickNotifications = { viewModel.onAction(UserHomeAction.NavigateToNotifications) },
                onClickMessages = { viewModel.onAction(UserHomeAction.NavigateToConversations) }
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
                characterType = viewModel.characterType,
                modifier = Modifier
            )

            if (appointments.isNotEmpty()) {
                MobileUserHomeUpcomingAppointmentsComponent(
                    appointments = appointments,
                    onClickShowAll = { viewModel.onAction(UserHomeAction.NavigateToAppointments) }
                )
            }
        }
    }
}