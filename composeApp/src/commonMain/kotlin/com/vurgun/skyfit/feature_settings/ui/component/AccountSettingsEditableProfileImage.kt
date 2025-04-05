package com.vurgun.skyfit.feature_settings.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.picker.toImageBitmap
import com.vurgun.skyfit.core.ui.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.core.ui.components.text.BodySmallSemiboldText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.add_action
import skyfit.composeapp.generated.resources.change_action
import skyfit.composeapp.generated.resources.delete_action
import skyfit.composeapp.generated.resources.ic_arrow_replay
import skyfit.composeapp.generated.resources.ic_image

@Composable
private fun PillText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = SkyFitColor.text.inverse,
    backgroundColor: Color = SkyFitColor.specialty.buttonBgRest,
    rightIconRes: DrawableResource? = null
) {
    Row(
        modifier
            .wrapContentSize()
            .background(backgroundColor, CircleShape)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier,
            color = textColor,
            style = SkyFitTypography.bodyMediumMedium
        )

        if (rightIconRes != null) {
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(rightIconRes),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Composable
fun AccountSettingsEditableProfileImage2(
    title: String,
    url: String?,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit,
    onClickChange: () -> Unit,
    onClickDelete: () -> Unit
) {
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
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {

            if (selectedImage != null) {
                Image(
                    painter = BitmapPainter(image = selectedImage!!),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SkyFitPickImageWrapper(
                        onImagesSelected = {
                            selectedImage = it.firstOrNull()?.toImageBitmap()
                        }
                    ) {
                        PillText(
                            text = stringResource(Res.string.change_action),
                            modifier = Modifier,
                            backgroundColor = SkyFitColor.specialty.buttonBgRest,
                            textColor = SkyFitColor.text.inverse,
                            rightIconRes = Res.drawable.ic_arrow_replay
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    PillText(
                        text = stringResource(Res.string.delete_action),
                        modifier = Modifier.clickable(onClick = onClickDelete),
                        backgroundColor = SkyFitColor.background.surfaceCritical,
                        textColor = SkyFitColor.text.default
                    )
                }

            } else if (url.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                SkyFitPickImageWrapper(
                    onImagesSelected = {
                        selectedImage = it.firstOrNull()?.toImageBitmap()
                    }
                ) {
                    PillText(
                        text = stringResource(Res.string.add_action),
                        modifier = Modifier,
                        backgroundColor = SkyFitColor.specialty.buttonBgRest,
                        textColor = SkyFitColor.text.inverse
                    )
                }

            } else {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SkyFitPickImageWrapper(
                        onImagesSelected = {
                            selectedImage = it.firstOrNull()?.toImageBitmap()
                        }
                    ) {
                        PillText(
                            text = stringResource(Res.string.change_action),
                            modifier = Modifier,
                            backgroundColor = SkyFitColor.specialty.buttonBgRest,
                            textColor = SkyFitColor.text.inverse,
                            rightIconRes = Res.drawable.ic_arrow_replay
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    PillText(
                        text = stringResource(Res.string.delete_action),
                        modifier = Modifier.clickable(onClick = onClickDelete),
                        backgroundColor = SkyFitColor.background.surfaceCritical,
                        textColor = SkyFitColor.text.default
                    )
                }
            }
        }
    }
}

@Composable
fun AccountSettingsEditableProfileImage(
    title: String,
    url: String?,
    modifier: Modifier = Modifier,
    onClickDelete: () -> Unit,
    onImageChanged: (ImageBitmap) -> Unit
) {
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
                        onPick = { selectedImage = it.firstOrNull()?.toImageBitmap() },
                        onDelete = onClickDelete,
                        isAdd = false
                    )
                    onImageChanged(selectedImage!!)
                }

                url.isNullOrEmpty() -> {
                    Icon(
                        painter = painterResource(Res.drawable.ic_image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    ProfileImageActions(
                        onPick = { selectedImage = it.firstOrNull()?.toImageBitmap() },
                        onDelete = onClickDelete,
                        isAdd = true
                    )
                }

                else -> {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    ProfileImageActions(
                        onPick = { selectedImage = it.firstOrNull()?.toImageBitmap() },
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
    onPick: (List<ByteArray>) -> Unit,
    onDelete: () -> Unit,
    isAdd: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkyFitPickImageWrapper(onImagesSelected = onPick) {
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
