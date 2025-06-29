package com.vurgun.skyfit

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitTheme
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSizeHelper
import com.vurgun.skyfit.module.AppScreenRegistry
import com.vurgun.skyfit.module.appDataModule
import kotlinx.coroutines.delay
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun AppRootScreen(
    platformModule: Module = Module(),
) {
    val koinModules = listOf(platformModule, appDataModule)

    KoinApplication(application = { modules(koinModules) }) {
        BoxWithConstraints {
            val windowSize = WindowSizeHelper.fromWidth(maxWidth)

            CompositionLocalProvider(LocalWindowSize provides windowSize) {
                SkyFitTheme {
                    AppContent()
                }
            }
        }
    }
}

@Composable
private fun AppContent() {
    AppScreenRegistry()

    val splashScreen = rememberScreen(SharedScreen.Splash)

    Navigator(splashScreen) { navigator ->
        CrossfadeTransition(navigator)
    }

    LaunchedEffect(Unit) {
        delay(5000L)
        CoreNotifier.subscribeTopic()
        CoreNotifier.sendGreetingsNotification()
    }
}