package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.construction.AppDependencyManager
import com.vurgun.skyfit.construction.SkyFitHostScreen
import moe.tlaster.precompose.PreComposeApp

fun main() = application {

    AppDependencyManager.loadSkyFitModules()

    PreComposeApp {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SkyFit",
        ) {
            SkyFitHostScreen {
                //TODO: desktop navigation graph
            }
        }
    }
}