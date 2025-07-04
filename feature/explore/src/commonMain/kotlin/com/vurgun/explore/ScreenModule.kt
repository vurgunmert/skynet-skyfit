package com.vurgun.explore

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.explore.screen.ExploreScreen

val screenExploreModule = screenModule {
    register<SharedScreen.Explore> { ExploreScreen() }
}