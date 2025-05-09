package com.vurgun.skyfit.feature.wellbeign

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.wellbeign.posture.PostureAnalysisScreen

val wellbeingScreenModule = screenModule {
    register<SharedScreen.PostureAnalysis> { PostureAnalysisScreen() }
}