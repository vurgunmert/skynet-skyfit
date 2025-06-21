package com.vurgun.skyfit.feature.home.screen.trainer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel

internal class TrainerHomeScreen : Screen {

    override val key: ScreenKey = "home:trainer"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<TrainerHomeViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> TrainerHomeExpanded(viewModel)
            else -> TrainerHomeCompact(viewModel)
        }
    }
}