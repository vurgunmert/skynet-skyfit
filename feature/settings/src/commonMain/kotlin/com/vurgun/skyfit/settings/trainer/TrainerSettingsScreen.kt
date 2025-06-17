package com.vurgun.skyfit.feature.persona.settings.trainer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.settings.shared.SettingsViewModel
import com.vurgun.skyfit.settings.trainer.TrainerSettingsCompact
import com.vurgun.skyfit.settings.trainer.TrainerSettingsExpanded

class TrainerSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<SettingsViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> TrainerSettingsExpanded(viewModel)
            else -> TrainerSettingsCompact(viewModel)
        }
    }
}