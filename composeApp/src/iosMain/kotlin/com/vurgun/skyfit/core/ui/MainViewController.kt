package com.vurgun.skyfit.core.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.vurgun.skyfit.navigation.MobileNavigationGraph

fun MainViewController() = ComposeUIViewController {
    SkyFitHostScreen {
        MobileNavigationGraph()
    }
}