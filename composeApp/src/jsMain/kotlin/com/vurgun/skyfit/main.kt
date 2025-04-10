package com.vurgun.skyfit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow
import com.vurgun.skyfit.core.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()

    onWasmReady {
        CanvasBasedWindow(
            canvasElementId = "ComposeTarget",
            title = "SkyFit Web",
            content = {
//                MobileApp()
                Box(Modifier.fillMaxSize().background(Color.Black)) {
                    Text(
                        text = "TODO: precompose for web",
                        fontSize = 124.sp,
                        color = Color.White
                    )
                }
            }
        )
    }
}