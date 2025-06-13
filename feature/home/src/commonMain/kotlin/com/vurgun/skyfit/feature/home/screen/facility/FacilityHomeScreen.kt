package com.vurgun.skyfit.feature.home.screen.facility

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel

class FacilityHomeScreen : Screen {

    override val key: ScreenKey = "home:facility"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<FacilityHomeViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> FacilityHomeExpanded(viewModel)
            else -> FacilityHomeCompact(viewModel)
        }
    }
}
