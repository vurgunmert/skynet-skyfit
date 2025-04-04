package com.vurgun.skyfit.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowInsetsControllerCompat
import com.vurgun.skyfit.feature_navigation.MobileApp

class AndroidHostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge drawing (draw behind the status bar)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        //TODO: Investigate how to get correct heights of bars

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false

        setContent {
            MobileApp()
        }
    }
}
