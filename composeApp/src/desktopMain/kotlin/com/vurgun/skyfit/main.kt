package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import com.vurgun.skyfit.data.core.storage.provideLocalSettings
import com.vurgun.skyfit.feature_navigation.SkyFitApp
import com.vurgun.skyfit.ui.core.styling.LocalDimensions
import moe.tlaster.precompose.ProvidePreComposeLocals
import org.koin.dsl.module
import java.awt.Dimension

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "SkyFit",
    ) {
        val localDimens = LocalDimensions.current
        window.minimumSize = Dimension(localDimens.desktopMinWidthPx, localDimens.desktopMinHeightPx)

        ProvidePreComposeLocals {
            SkyFitApp(
                platformModule = module {
                    single<LocalSettingsStore> { provideLocalSettings(null) }
                }
            )
        }
    }
}