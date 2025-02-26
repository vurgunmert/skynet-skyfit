package com.vurgun.skyfit.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun SkyFitImageComponent(
    url: String = "https://ik.imagekit.io/skynet2skyfit/character_carrot.png?updatedAt=1738866664880",
    modifier: Modifier = Modifier,
    contentDescription: String = ""
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}