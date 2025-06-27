package com.vurgun.main.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.main.dashboard.DashboardViewModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class DashboardScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<DashboardViewModel>()

        if (windowSize == WindowSize.EXPANDED) {
            DashboardExpanded(viewModel)
        } else {
            DashboardCompact(viewModel)
        }
    }
}


