package com.vurgun.main

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.main.screen.MainScreen

val mainScreenModule = screenModule {
    register<SharedScreen.Main> { MainScreen() }
}