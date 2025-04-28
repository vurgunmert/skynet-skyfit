package com.vurgun.skyfit

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.vurgun.skyfit.core.data.storage.LocalSettingsStore
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AndroidHostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         Enable edge-to-edge drawing (draw behind the status bar)
//        WindowCompat.setDecorFitsSystemWindows(window, true)
        //TODO: Investigate how to get correct heights of bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false

        setContent {
            SkyFitApp(
                platformModule = module {
                    single<Context> { applicationContext }
                    single<LocalSettingsStore> { provideLocalSettings(androidContext()) }
                }
            )
        }
    }
}