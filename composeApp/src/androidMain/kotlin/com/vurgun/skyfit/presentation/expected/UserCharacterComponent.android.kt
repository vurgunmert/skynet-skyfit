package com.vurgun.skyfit.presentation.expected

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.RiveUserCharacterComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitImageComponent

@Composable
actual fun UserCharacterComponent(modifier: Modifier) {
//    RiveUserCharacterComponent(modifier.size(240.dp))
    SkyFitImageComponent(
        url =  "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
        modifier = modifier.size(240.dp)
    )
}