package com.vurgun.skyfit.settings.facility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.settings.shared.SettingsViewModel

class FacilitySettingsScreen : Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<SettingsViewModel>()

        LaunchedEffect(viewModel) {
            viewModel.loadData()
        }

        when (windowSize) {
            WindowSize.EXPANDED -> FacilitySettingsExpanded(viewModel)
            else -> FacilitySettingsCompact(viewModel)
        }
    }
}