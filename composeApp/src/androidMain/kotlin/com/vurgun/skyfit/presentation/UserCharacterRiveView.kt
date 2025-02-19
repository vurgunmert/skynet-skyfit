package com.vurgun.skyfit.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import com.vurgun.skyfit.R

@Composable
fun RiveUserCharacterComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var riveView by remember { mutableStateOf<RiveAnimationView?>(null) }
    val animations = listOf("Resting (Default)", "Weight Lift", "Patted") // List of animations to cycle through
    var currentAnimationIndex by remember { mutableStateOf(0) } // Track current animation index

    AndroidView(
        factory = { ctx ->
            RiveAnimationView(ctx).apply {
                setRiveResource(R.raw.character_panda, fit = Fit.NONE)
                riveView = this
                play("Weight Lift", loop = Loop.LOOP)
            }
        },
        modifier = modifier
            .fillMaxSize()
    )
}
