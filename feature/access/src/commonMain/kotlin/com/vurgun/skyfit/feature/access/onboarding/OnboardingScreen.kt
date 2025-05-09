package com.vurgun.skyfit.feature.access.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<OnboardingViewModel>()
        val userTypeSelectionScreen = remember { UserTypeSelectionScreen(viewModel) }

        Navigator(userTypeSelectionScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}