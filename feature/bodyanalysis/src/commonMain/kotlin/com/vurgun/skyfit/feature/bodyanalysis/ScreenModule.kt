package com.vurgun.skyfit.feature.bodyanalysis

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.bodyanalysis.screen.PostureAnalysisScreen

val bodyAnalysisScreenModule = screenModule {
    register<SharedScreen.PostureAnalysis> { PostureAnalysisScreen() }
}