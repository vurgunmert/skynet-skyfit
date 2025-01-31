package com.vurgun.skyfit

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.vurgun.skyfit.construction.AppDependencyManager
import com.vurgun.skyfit.construction.SkyFitHostScreen
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    AppDependencyManager.loadSkyFitModules()
    //TODO: Import & Initiate Firebase

    ComposeViewport(document.body!!) {
        SkyFitHostScreen {
            //TODO: web navigation graph
        }
    }
}