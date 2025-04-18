package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.CharacterType
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.web.dom.Text

//@Composable
//actual fun AnimatedCharacterComponent(modifier: Modifier) {
//    SkyFitImageComponent(
//        url =  "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
//        modifier = modifier.size(240.dp)
//    )
//}

@Composable
actual fun AnimatedCharacterComponent(
    modifier: Modifier,
    characterType: CharacterType
) {
    Box(
        Modifier.size(100.dp)
            .background(SkyFitColor.background.surfaceSecondary)
    ) {
        Text("CHARACTER(${characterType})")
    }
}