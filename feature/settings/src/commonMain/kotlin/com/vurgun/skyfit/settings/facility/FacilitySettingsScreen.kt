package com.vurgun.skyfit.settings.facility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.settings.shared.SettingsViewModel

class FacilitySettingsScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SettingsViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (windowSize) {
            WindowSize.EXPANDED -> FacilitySettingsExpanded(viewModel)
            else -> FacilitySettingsCompact(viewModel)
        }
    }
}