package com.vurgun.skyfit.core.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.vurgun.skyfit.feature_navigation.MobileNavigationGraph

fun MainViewController() = ComposeUIViewController {
    SkyFitHostScreen {
        MobileNavigationGraph()
    }
}