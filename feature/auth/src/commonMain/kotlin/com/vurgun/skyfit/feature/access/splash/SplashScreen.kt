package com.vurgun.skyfit.feature.access.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.special.FiweLogoDark
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEvent

internal class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SplashViewModel>()

        CollectEvent(viewModel.eventFlow) { event ->
            when (event) {
                SplashUiEvent.NavigateMain -> {
                    navigator.replaceAll(SharedScreen.Main)
                }

                SplashUiEvent.NavigateToAuth -> {
                    navigator.replace(SharedScreen.Authorization)
                }

                SplashUiEvent.NavigateToMaintenance -> {
                    navigator.replace(SharedScreen.Maintenance)
                }
            }
        }

        SplashLayout()
    }

    @Composable
    private fun SplashLayout() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SkyFitColor.background.default),
            contentAlignment = Alignment.Center
        ) {
            FiweLogoDark()
        }
    }
}