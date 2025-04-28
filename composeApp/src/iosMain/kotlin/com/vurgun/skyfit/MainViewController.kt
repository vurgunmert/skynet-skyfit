package com.vurgun.skyfit

import androidx.compose.ui.window.ComposeUIViewController
import com.vurgun.skyfit.core.data.storage.LocalSettingsStore
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController {
    SkyFitApp(
        platformModule = module {
            single<LocalSettingsStore> { provideLocalSettings(null) }
        }
    )
}