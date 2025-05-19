package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Deprecated("Use SkyImage")
@Composable
fun CircleNetworkImage(
    url: String?,
    size: Dp = 24.dp,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .size(size),
        contentScale = ContentScale.Crop
    )
}