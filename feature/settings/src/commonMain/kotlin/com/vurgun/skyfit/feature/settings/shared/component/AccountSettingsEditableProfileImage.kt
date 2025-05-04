package com.vurgun.skyfit.feature.settings.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.core.ui.components.text.BodySmallSemiboldText
import com.vurgun.skyfit.core.ui.components.text.PillText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_action
import skyfit.core.ui.generated.resources.change_action
import skyfit.core.ui.generated.resources.delete_action
import skyfit.core.ui.generated.resources.ic_arrow_replay
import skyfit.core.ui.generated.resources.ic_image

@Composable
fun AccountSettingsEditableProfileImage(
    title: String,
    url: String?,
    modifier: Modifier = Modifier,
    onClickDelete: () -> Unit,
    onImageChanged: (ByteArray?, ImageBitmap?) -> Unit
) {
    var selectedImageByteArray by remember { mutableStateOf<ByteArray?>(null) }
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    Column(
        modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        BodySmallSemiboldText(
            text = title,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            when {
                selectedImage != null -> {
                    Image(
                        painter = BitmapPainter(selectedImage!!),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    ProfileImageActions(
                        onPick = { byteArray, bitmap ->
                            selectedImageByteArray = byteArray
                            selectedImage = bitmap
                        },
                        onDelete = onClickDelete,
                        isAdd = false
                    )
                    onImageChanged(selectedImageByteArray, selectedImage)
                }

                url.isNullOrEmpty() -> {
                    Icon(
                        painter = painterResource(Res.drawable.ic_image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    ProfileImageActions(
                        onPick = { byteArray, bitmap ->
                            selectedImageByteArray = byteArray
                            selectedImage = bitmap
                        },
                        onDelete = onClickDelete,
                        isAdd = true
                    )
                }

                else -> {
                    NetworkImage(
                        imageUrl = url,
                        modifier = Modifier.fillMaxSize(),
                    )
                    ProfileImageActions(
                        onPick = { byteArray, bitmap ->
                            selectedImageByteArray = byteArray
                            selectedImage = bitmap
                        },
                        onDelete = onClickDelete,
                        isAdd = false
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileImageActions(
    onPick: (ByteArray, ImageBitmap) -> Unit,
    onDelete: () -> Unit,
    isAdd: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkyFitPickImageWrapper(onImagesSelected = { byteArray, bitmap ->
            onPick(byteArray, bitmap)
        }) {
            PillText(
                text = stringResource(
                    if (isAdd) Res.string.add_action else Res.string.change_action
                ),
                backgroundColor = SkyFitColor.specialty.buttonBgRest,
                textColor = SkyFitColor.text.inverse,
                rightIconRes = if (isAdd) null else Res.drawable.ic_arrow_replay
            )
        }

        if (!isAdd) {
            Spacer(Modifier.height(8.dp))
            PillText(
                text = stringResource(Res.string.delete_action),
                modifier = Modifier.clickable(onClick = onDelete),
                backgroundColor = SkyFitColor.background.surfaceCritical,
                textColor = SkyFitColor.text.default
            )
        }
    }
}
