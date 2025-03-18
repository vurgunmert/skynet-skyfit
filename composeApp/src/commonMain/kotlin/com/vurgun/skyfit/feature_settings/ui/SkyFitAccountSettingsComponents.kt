package com.vurgun.skyfit.feature_settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.vurgun.skyfit.core.domain.models.FitnessTagType
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.FitnessTagPickerDialog
import com.vurgun.skyfit.core.ui.components.SkyFitAccountSettingsProfileTagItemComponent
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.ic_warning
import skyfit.composeapp.generated.resources.logo_skyfit


@Composable
fun MobileUserSettingsScreenPhotoEditComponent(
    urlString: String?,
    label: String,
    onImagePicked: (ByteArray) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedImage by remember { mutableStateOf<ByteArray?>(null) }

    val imagePickerLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { images ->
            if (images.isNotEmpty()) {
                selectedImage = images.first()
                onImagePicked(images.first())
            }
        }
    )

    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedImage != null) {
            Image(
                painter = BitmapPainter(selectedImage!!.toImageBitmap()),
                contentDescription = "Picked Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { imagePickerLauncher.launch() },
            )
        } else {
            AsyncImage(
                model = urlString,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .clickable { imagePickerLauncher.launch() },
            )
        }

        Spacer(Modifier.width(16.dp))

        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(),
            text = label,
            onClick = { imagePickerLauncher.launch() },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Medium,
            state = ButtonState.Rest,
            rightIconPainter = painterResource(Res.drawable.ic_pencil)
        )
    }
}

@Composable
fun MobileUserSettingsScreenSaveActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Değişiklikleri Kaydet",
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}

@Composable
fun MobileUserSettingsScreenDeleteActionsComponent(
    onDeleteClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(24.dp)
    ) {
        Text(
            text = "Emin misiniz?",
            style = SkyFitTypography.heading4,
            color = SkyFitColor.text.default,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Hesabınızı sildiğinizde bu işlemi geri alamayacaksınız. Profiliniz, fotoğraflarınız, notlarınız, tepkileriniz ve takipçileriniz dahil tüm verileriniz kaybolacak.",
            style = SkyFitTypography.bodyLargeMedium,
            color = SkyFitColor.text.secondary,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(24.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = "Hesabı Sil",
            onClick = onDeleteClicked,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.ic_delete)
        )
        Spacer(Modifier.height(24.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = "İptal",
            onClick = onCancelClicked,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
    }
}


@Composable
fun SkyFitSelectToEnterInputComponent(
    title: String = "Title",
    hint: String = "Hint",
    value: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: ((String) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
    rightIconRes: DrawableResource? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive }
        ?: SkyFitColor.background.surfaceSecondary

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(title, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = CircleShape)
                .clickable { focusRequester.requestFocus() }
                .padding(vertical = 18.dp, horizontal = 16.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                enabled = true,
                value = value.orEmpty(),
                onValueChange = { onValueChange?.invoke(it) },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = SkyFitColor.text.default
                ),
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { nextFocusRequester?.requestFocus() },
                    onDone = { keyboardController?.hide() }
                ),
                cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
                decorationBox = { innerTextField ->
                    if (value.isNullOrBlank()) {
                        Text(text = hint, color = SkyFitColor.text.secondary)
                    }
                    innerTextField()
                }
            )

            if (rightIconRes != null) {
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_pencil),
                    contentDescription = "Edit",
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
            }
        }

        error?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = error,
                style = SkyFitTypography.bodySmallSemibold
            )
        }
    }
}


@Composable
fun AccountSettingsSelectToSetInputComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String = "Title",
    hint: String = "Hint",
    value: String? = null,
    rightIconRes: DrawableResource? = null
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape)
                .padding(vertical = 18.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (value.isNullOrEmpty()) {
                Text(
                    text = hint,
                    modifier = Modifier.padding(start = 8.dp).weight(1f),
                    color = SkyFitColor.text.secondary,
                    style = SkyFitTypography.bodyMediumRegular
                )
            } else {
                Text(
                    text = value,
                    modifier = Modifier.weight(1f),
                    color = SkyFitColor.text.default,
                    style = SkyFitTypography.bodyMediumRegular
                )
            }

            rightIconRes?.let { iconRes ->
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FitnessTagPickerComponent(
    modifier: Modifier = Modifier,
    selectedTags: List<FitnessTagType>,
    onTagsSelected: (List<FitnessTagType>) -> Unit
) {
    var showTagPickerDialog by remember { mutableStateOf(false) }

    Box {
        Column(modifier.fillMaxWidth()) {
            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showTagPickerDialog = true },
                title = "Profil Etiketleri",
                hint = "Etiketlerini Sec örn: Pilates, Kondisyon",
                value = null,
                rightIconRes = Res.drawable.ic_pencil
            )
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(SkyFitColor.background.surfaceCautionActive, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_warning),
                            contentDescription = "Notification Icon",
                            modifier = Modifier.size(16.dp),
                            tint = SkyFitColor.icon.caution
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Etiket Sayısı",
                            color = SkyFitColor.text.default,
                            style = SkyFitTypography.bodySmallSemibold,
                            modifier = Modifier.weight(1f).padding(end = 16.dp),
                            maxLines = 2
                        )
                    }
                    Text(
                        text = "Profilinizde en fazla 5 etiket bulundurabilirsiniz.",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        color = SkyFitColor.text.default,
                        style = SkyFitTypography.bodySmall
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                selectedTags.forEach {
                    SkyFitAccountSettingsProfileTagItemComponent(
                        value = it.label,
                        onClick = { onTagsSelected(selectedTags - it) })
                }
            }
        }

        if (showTagPickerDialog) {
            FitnessTagPickerDialog(
                initialTags = selectedTags,
                onDismiss = { showTagPickerDialog = false },
                onTagsSelected = onTagsSelected
            )
        }
    }
}


@Composable
fun SkyFitSelectToEnterMultilineInputComponent(
    title: String = "Title",
    hint: String = "Hint",
    value: String? = null,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: ((String) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
    rightIconRes: DrawableResource? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(title, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, shape = RoundedCornerShape(20.dp))
                .clickable { focusRequester.requestFocus() }
                .padding(16.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                enabled = true,
                value = value.orEmpty(),
                onValueChange = { onValueChange?.invoke(it) },
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = SkyFitColor.text.default
                ),
                singleLine = false,
                maxLines = 5,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Default
                ),
                keyboardActions = KeyboardActions(
                    onNext = { nextFocusRequester?.requestFocus() },
                    onDone = { keyboardController?.hide() }
                ),
                cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
                decorationBox = { innerTextField ->
                    if (value.isNullOrBlank()) {
                        Text(text = hint, color = SkyFitColor.text.secondary)
                    }
                    innerTextField()
                }
            )

            if (rightIconRes != null) {
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_pencil),
                    contentDescription = "Edit",
                    tint = SkyFitColor.text.default,
                    modifier = Modifier.size(16.dp)
                )
            }

        }
    }
}
