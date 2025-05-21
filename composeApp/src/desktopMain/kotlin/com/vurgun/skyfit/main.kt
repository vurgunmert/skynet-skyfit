package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.core.data.storage.LocalSettingsStore
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import org.koin.dsl.module
import java.awt.Dimension

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "SkyFit",
    ) {
        val localDimens = LocalDimensions.current
        window.minimumSize = Dimension(localDimens.mobileMinWidthPx, localDimens.mobileMinWidthPx)

        SkyFitApp(
            platformModule = module {
                single<LocalSettingsStore> { provideLocalSettings(null) }
            }
        )
    }
}