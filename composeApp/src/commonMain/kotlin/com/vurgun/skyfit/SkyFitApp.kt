package com.vurgun.skyfit

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnderDevelopmentScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitTheme
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSizeHelper
import com.vurgun.skyfit.feature.access.accessScreenModule
import com.vurgun.skyfit.feature.connect.connectScreenModule
import com.vurgun.skyfit.feature.dashboard.dashboardScreenModule
import com.vurgun.skyfit.feature.persona.personaScreenModule
import com.vurgun.skyfit.feature.schedule.scheduleScreenModule
import com.vurgun.skyfit.feature.wellbeign.wellbeingScreenModule
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun SkyFitApp(
    platformModule: Module = Module(),
) {
    val koinModules = listOf(platformModule, appModule)

    KoinApplication(
        application = { modules(koinModules) },
        content = {
            BoxWithConstraints {
                val windowSize = WindowSizeHelper.fromWidth(maxWidth)

                CompositionLocalProvider(
                    LocalWindowSize provides windowSize
                ) {
                    AppScreen()
                }
            }
        }
    )
}

@Composable
private fun AppScreen() {
    SkyFitTheme {
        ScreenRegistry {
            register<SharedScreen.UnderDevelopment> { UnderDevelopmentScreen() }
            accessScreenModule()
            connectScreenModule()
            dashboardScreenModule()
            personaScreenModule()
            scheduleScreenModule()
            wellbeingScreenModule()
        }

        val splashScreen = rememberScreen(SharedScreen.Splash)

        Navigator(splashScreen) { navigator ->
            CrossfadeTransition(navigator)
        }
    }
}
