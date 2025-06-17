package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.core.data.storage.LocalSettingsStore
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import org.jetbrains.compose.resources.stringResource
import org.koin.dsl.module
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.app_name
import java.awt.Dimension

// Desktop Entry Point
fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        val localDimens = LocalDimensions.current
        window.minimumSize = Dimension(localDimens.desktopMinWidthPx, localDimens.desktopMinHeightPx)

        AppRootScreen(
            platformModule = module {
                single<LocalSettingsStore> { provideLocalSettings(null) }
            }
        )
    }
}