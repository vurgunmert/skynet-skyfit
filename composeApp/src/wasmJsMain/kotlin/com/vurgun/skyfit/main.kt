package com.vurgun.skyfit

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.vurgun.skyfit.construction.AppDependencyManager
import com.vurgun.skyfit.construction.SkyFitHostScreen
import kotlinx.browser.document
import com.vurgun.skyfit.presentation.web.WebHostScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    AppDependencyManager.loadSkyFitModules()

    ComposeViewport(document.body!!) {
        SkyFitHostScreen {
            //TODO: web navigation graph
        }
    }
}