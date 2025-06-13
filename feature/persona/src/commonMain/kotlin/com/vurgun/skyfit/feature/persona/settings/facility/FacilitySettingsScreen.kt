package com.vurgun.skyfit.feature.persona.settings.facility

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsViewModel

class FacilitySettingsScreen: Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<SettingsViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> FacilitySettingsExpanded(viewModel)
            else -> FacilitySettingsCompact(viewModel)
        }
    }
}