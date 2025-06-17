package com.vurgun.skyfit.feature.home

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.screen.HomeScreen

val screenHomeModule = screenModule {
    register<SharedScreen.Home> { HomeScreen() }
}
