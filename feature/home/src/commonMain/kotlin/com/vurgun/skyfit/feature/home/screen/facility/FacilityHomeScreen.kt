package com.vurgun.skyfit.feature.home.screen.facility

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel

class FacilityHomeScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<FacilityHomeViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> FacilityHomeExpanded(viewModel)
            else -> FacilityHomeCompact(viewModel)
        }
    }
}
