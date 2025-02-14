package com.vurgun.skyfit.presentation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import com.vurgun.skyfit.R

@Composable
fun RiveUserCharacterComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var riveView by remember { mutableStateOf<RiveAnimationView?>(null) }
    val animations = listOf("Resting (Default)", "Arm Touched", "Patted") // List of animations to cycle through
    var currentAnimationIndex by remember { mutableStateOf(0) } // Track current animation index

    AndroidView(
        factory = { ctx ->
            RiveAnimationView(ctx).apply {
                setRiveResource(R.raw.carrot, fit = Fit.COVER)
                riveView = this
                play(animations[currentAnimationIndex]) // Start with the first animation
            }
        },
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    // Change to the next animation on tap
                    currentAnimationIndex = (currentAnimationIndex + 1) % animations.size
                    riveView?.play(animations[currentAnimationIndex])
                }
            }
    )
}
