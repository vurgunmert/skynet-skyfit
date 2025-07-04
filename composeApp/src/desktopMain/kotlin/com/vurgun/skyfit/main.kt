package com.vurgun.skyfit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vurgun.skyfit.core.data.storage.LocalSessionStorage
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import com.vurgun.skyfit.DesktopNotifier
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import org.jetbrains.compose.resources.stringResource
import org.koin.dsl.module
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.app_name
import java.awt.Dimension

// Desktop Entry Point
fun main() = application {

    DesktopNotifier.initialize()

    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        val localDimens = LocalDimensions.current
        window.minimumSize = Dimension(localDimens.mobileMinWidthPx, localDimens.mobileMinHeightPx)

        AppRootScreen(
            platformModule = module {
                single<LocalSessionStorage> { provideLocalSettings(null) }
            }
        )
    }
}