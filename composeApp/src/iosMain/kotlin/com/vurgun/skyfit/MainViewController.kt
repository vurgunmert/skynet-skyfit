package com.vurgun.skyfit

import androidx.compose.ui.window.ComposeUIViewController
import com.vurgun.skyfit.construction.SkyFitHostScreen
import com.vurgun.skyfit.presentation.mobile.navigation.MobileNavigationGraph

fun MainViewController() = ComposeUIViewController {
    SkyFitHostScreen {
        MobileNavigationGraph()
    }
}