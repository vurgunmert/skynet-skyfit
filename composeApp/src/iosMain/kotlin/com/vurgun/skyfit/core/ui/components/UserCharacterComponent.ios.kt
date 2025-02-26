package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun UserCharacterComponent(modifier: Modifier) {
    SkyFitImageComponent(
        url =  "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
        modifier = modifier.size(240.dp)
    )
}