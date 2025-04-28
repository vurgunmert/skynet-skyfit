package com.vurgun.skyfit.feature.splash

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.splash.screen.MaintenanceScreen
import com.vurgun.skyfit.feature.splash.screen.SplashScreen

val splashScreenModule = screenModule {
    register<SharedScreen.Splash> { SplashScreen() }
    register<SharedScreen.Maintenance> { MaintenanceScreen() }
}