package com.vurgun.skyfit.profile.trainer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class TrainerProfileScreen(private val trainerId: Int? = null) : Screen {

    override val key: ScreenKey
        get() = "profile:trainer:screen:$trainerId"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<TrainerProfileViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadData(trainerId)
        }

        when (windowSize) {
            WindowSize.EXPANDED -> TrainerProfileExpanded(viewModel)
            else -> TrainerProfileCompact(viewModel)
        }
    }
}