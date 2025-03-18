package com.vurgun.skyfit.core.ui.components

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
import com.vurgun.skyfit.core.domain.models.CharacterType
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.feature_dashboard.ui.unmanagedUser

@Composable
fun AndroidRiveUserCharacterComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var riveView by remember { mutableStateOf<RiveAnimationView?>(null) }
    val animations = listOf("Resting (Default)", "Weight Lift", "Patted") // List of animations to cycle through
    var currentAnimationIndex by remember { mutableStateOf(0) } // Track current animation index

    val characterResource = when(unmanagedUser?.characterType) {
        CharacterType.Carrot -> R.raw.character_carrot
        CharacterType.Koala -> R.raw.character_koala
        CharacterType.Panda -> R.raw.character_panda
        null -> R.raw.character_carrot
    }

    AndroidView(
        factory = { ctx ->
            RiveAnimationView(ctx).apply {
                setRiveResource(characterResource, fit = Fit.NONE)
                autoplay = true
                riveView = this
//                play("Weight Lift", loop = Loop.LOOP)
            }
        },
        modifier = modifier
            .fillMaxSize()
    )
}
