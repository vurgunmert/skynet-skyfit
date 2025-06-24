@file:Suppress("FunctionName")

package com.vurgun.skyfit

import androidx.compose.ui.window.ComposeUIViewController
import com.vurgun.skyfit.core.data.storage.LocalSessionStorage
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import org.koin.dsl.module

// iOS Entry Point
fun MainViewController() = ComposeUIViewController {
    AppRootScreen(
        platformModule = module {
            single<LocalSessionStorage> { provideLocalSettings(null) }
        }
    )
}