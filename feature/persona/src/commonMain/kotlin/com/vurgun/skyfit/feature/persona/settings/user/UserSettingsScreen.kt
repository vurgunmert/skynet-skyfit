package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class UserSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<UserSettingViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> UserSettingsExpanded(viewModel)
            else -> UserSettingsCompact(viewModel)
        }
    }
}