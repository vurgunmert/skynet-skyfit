package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_image
import fiwe.core.ui.generated.resources.im_placeholder_dark

enum class SkyImageSize(val dp: Dp) {
    Size24(24.dp),
    Size32(32.dp),
    Size36(36.dp),
    Size40(40.dp),
    Size48(48.dp),
    Size50(50.dp),
    Size60(60.dp),
    Size64(64.dp),
    Size72(72.dp),
    Size96(96.dp),
    Size100(100.dp)
}

enum class SkyImageShape(val defaultShape: Shape) {
    Circle(CircleShape),
    Rounded(RoundedCornerShape(12.dp)),
    Square(RoundedCornerShape(0.dp))
}

@Composable
fun SkyImage(
    url: String?,
    modifier: Modifier = Modifier,
    size: SkyImageSize = SkyImageSize.Size40,
    sizeOverride: Dp? = null,
    shape: SkyImageShape = SkyImageShape.Square,
    shapeOverride: Shape? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: DrawableResource? = null,
    error: DrawableResource? = null
) {
    val resolvedSize = sizeOverride ?: size.dp
    val resolvedShape = shapeOverride ?: shape.defaultShape

    val painter = rememberAsyncImagePainter(
        model = url,
        placeholder = placeholder?.let { painterResource(it) },
        error = error?.let { painterResource(it) },
        contentScale = contentScale
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
            .size(resolvedSize)
            .clip(resolvedShape)
    )
}


@Preview
@Composable
private fun SkyImagePreview() {
    SkyImage(
        url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ432ju-gdS2nl6CEobTaFXEe6_gRmK5DkWuQ&s",
        modifier = Modifier,
        size = SkyImageSize.Size100,
        placeholder = Res.drawable.ic_image,
        error = Res.drawable.im_placeholder_dark
    )
}