package com.vurgun.skyfit.feature.home.screen.user

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.home.model.UserHomeViewModel

internal class UserHomeScreen : Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<UserHomeViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> UserHomeExpanded(viewModel)
            else -> UserHomeCompact(viewModel)
        }
    }
}