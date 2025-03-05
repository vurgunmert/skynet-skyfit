package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter

@Composable
fun CircularImage(
    avatarUrl: String,
    modifier: Modifier = Modifier.size(32.dp),
    isAnimated: Boolean = true
) {
    var isImageLoaded by remember { mutableStateOf(!isAnimated) }

    if (isAnimated) {
        AnimatedVisibility(
            visible = isImageLoaded,
            enter = fadeIn(animationSpec = tween(500))
        ) {
            CircularImageContent(avatarUrl, modifier) { isImageLoaded = true }
        }
    } else {
        CircularImageContent(avatarUrl, modifier) { isImageLoaded = true }
    }
}

@Composable
private fun CircularImageContent(
    avatarUrl: String,
    modifier: Modifier,
    onLoaded: () -> Unit
) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = "Image",
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Gray),
        contentScale = ContentScale.Crop,
        onState = { state ->
            if (state is AsyncImagePainter.State.Success) {
                onLoaded()
            }
        }
    )
}

@Composable
fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    cornerRadius: Dp = 8.dp,
    isAnimated: Boolean = true
) {
    var isImageLoaded by remember { mutableStateOf(!isAnimated) } // Default true if no animation

    if (isAnimated) {
        AnimatedVisibility(
            visible = isImageLoaded,
            enter = fadeIn(animationSpec = tween(500))
        ) {
            NetworkImageContent(imageUrl, size, cornerRadius, modifier) { isImageLoaded = true }
        }
    } else {
        NetworkImageContent(imageUrl, size, cornerRadius, modifier) { isImageLoaded = true }
    }
}

@Composable
private fun NetworkImageContent(
    imageUrl: String,
    size: Dp,
    cornerRadius: Dp,
    modifier: Modifier,
    onLoaded: () -> Unit
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image",
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.Gray),
        contentScale = ContentScale.Crop,
        onState = { state ->
            if (state is AsyncImagePainter.State.Success) {
                onLoaded()
            }
        }
    )
}