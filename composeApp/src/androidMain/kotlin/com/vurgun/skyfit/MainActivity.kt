package com.vurgun.skyfit

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.vurgun.skyfit.core.data.storage.LocalSettingsStore
import com.vurgun.skyfit.core.data.storage.provideLocalSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = when {
            isPhone() -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

//         Enable edge-to-edge drawing (draw behind the status bar)
//        WindowCompat.setDecorFitsSystemWindows(window, true)
        //TODO: Investigate how to get correct heights of bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false

        setContent {
            AppRootScreen(
                platformModule = module {
                    single<Context> { applicationContext }
                    single<LocalSettingsStore> { provideLocalSettings(androidContext()) }
                }
            )
        }
    }

    private fun isPhone(): Boolean {
        val metrics = resources.displayMetrics
        val widthInDp = metrics.widthPixels / metrics.density
        val heightInDp = metrics.heightPixels / metrics.density
        val smallestWidth = minOf(widthInDp, heightInDp)

        return smallestWidth < 600
    }
}