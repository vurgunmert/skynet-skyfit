package com.vurgun.skyfit.feature.home.screen.facility

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.FacilityHomeAction
import com.vurgun.skyfit.feature.home.model.FacilityHomeUiState
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel
import com.vurgun.skyfit.feature.home.component.HomeNoUpcomingAppointmentCard
import com.vurgun.skyfit.feature.home.component.MobileDashboardHomeUpcomingAppointmentsComponent

class FacilityHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<FacilityHomeViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> FacilityHomeExpanded(viewModel)
            else ->  FacilityHomeCompact(viewModel)
        }
    }
}
