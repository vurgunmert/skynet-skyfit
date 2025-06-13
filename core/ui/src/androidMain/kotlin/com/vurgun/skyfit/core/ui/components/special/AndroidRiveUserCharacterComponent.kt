//package com.vurgun.skyfit.core.ui.components.special
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import app.rive.runtime.kotlin.RiveAnimationView
//import app.rive.runtime.kotlin.core.Fit
//import com.vurgun.skyfit.core.ui.R
//import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
//
//@Composable
//fun AndroidRiveAnimatedCharacterComponent(modifier: Modifier = Modifier, characterType: CharacterType) {
////    val context = LocalContext.current
//    var riveView by remember { mutableStateOf<RiveAnimationView?>(null) }
////    val animations = listOf("Resting (Default)", "Weight Lift", "Patted") // List of animations to cycle through
////    var currentAnimationIndex by remember { mutableStateOf(0) } // Track current animation index
//
//    val characterResource = when(characterType) {
//        CharacterType.Carrot -> R.raw.character_carrot
//        CharacterType.Koala -> R.raw.character_koala
//        CharacterType.Panda -> R.raw.character_panda
//    }
//
//    AndroidView(
//        factory = { ctx ->
//            RiveAnimationView(ctx).apply {
//                setRiveResource(characterResource, fit = Fit.NONE)
//                autoplay = true
//                riveView = this
////                play("Weight Lift", loop = Loop.LOOP)
//            }
//        },
//        modifier = modifier
//            .fillMaxSize()
//    )
//}
