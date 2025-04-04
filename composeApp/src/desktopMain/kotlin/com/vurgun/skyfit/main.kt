package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.core.di.initKoin
import com.vurgun.skyfit.feature_navigation.MobileApp
import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "SkyFit",
    ) {

        ProvidePreComposeLocals {
            MobileApp()
        }
    }
}