package com.vurgun.skyfit.feature.dashboard.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class DashboardMainScreen : Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<DashboardViewModel>()

        if (windowSize == WindowSize.EXPANDED) {
            DashboardLayoutExpanded.Screen(viewModel)
        } else {
            DashboardLayoutCompact.Screen(viewModel)
        }
    }
}


