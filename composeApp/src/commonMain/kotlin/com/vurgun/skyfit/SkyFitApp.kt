package com.vurgun.skyfit

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.ui.core.styling.SkyFitTheme
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun SkyFitApp(
    platformModule: Module = Module(),
) {
    KoinApplication(
        application = {
            modules(platformModule, appModule)
        },
        content = {
            SkyFitTheme {
                AppNavigationGraph()
            }
        }
    )
}