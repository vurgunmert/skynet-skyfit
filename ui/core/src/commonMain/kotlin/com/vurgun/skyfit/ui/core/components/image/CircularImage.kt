package com.vurgun.skyfit.ui.core.components.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_image

@Composable
fun CircularImage(
    url: String,
    modifier: Modifier = Modifier.size(32.dp),
    isAnimated: Boolean = true
) {
    var isImageLoaded by remember { mutableStateOf(!isAnimated) }

    if (isAnimated) {
        AnimatedVisibility(
            visible = isImageLoaded,
            enter = fadeIn(animationSpec = tween(500))
        ) {
            CircularImageContent(url, modifier) { isImageLoaded = true }
        }
    } else {
        CircularImageContent(url, modifier) { isImageLoaded = true }
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
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    cornerRadius: Dp = 8.dp,
    isAnimated: Boolean = true
) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val state by painter.state.collectAsState()

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is AsyncImagePainter.State.Loading,
            is AsyncImagePainter.State.Empty -> {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            }

            is AsyncImagePainter.State.Error -> {
                Icon(
                    painter = painterResource(Res.drawable.ic_image),
                    contentDescription = "Image failed",
                    tint = Color.Gray
                )
            }

            is AsyncImagePainter.State.Success -> {
                if (isAnimated) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500))
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Image(
                        painter = painter,
                        contentDescription = "Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun NetworkImageContent(
    imageUrl: String?,
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