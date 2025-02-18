package com.vurgun.skyfit.presentation.mobile.features.user.settings

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.ImageBitmap
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
import com.vurgun.skyfit.presentation.shared.components.BodyTypePickerDialog
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.FitnessTagPickerDialog
import com.vurgun.skyfit.presentation.shared.components.HeightPickerDialog
import com.vurgun.skyfit.presentation.shared.components.SkyFitAccountSettingsProfileTagItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.WeightPickerDialog
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitUserAccountSettingsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_lock
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.ic_warning
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserSettingsAccountScreen(navigator: Navigator) {

    val viewModel: SkyFitUserAccountSettingsViewModel = koinInject()

    val userAccountState by viewModel.userAccountState.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showWeightDialog by remember { mutableStateOf(false) }
    var showHeightDialog by remember { mutableStateOf(false) }
    var showBodyTypeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Hesap Ayarlari", onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileUserSettingsScreenDeleteActionsComponent(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = {}
                )
            } else if (userAccountState.isUpdated) {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    MobileUserSettingsScreenSaveActionComponent(onClick = viewModel::saveChanges)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            MobileUserSettingsScreenPhotoEditComponent(
                urlString = null,
                label = "Fotoğrafı Düzenle",
                onImagePicked = {
                    //TODO: Picked image should be stored
                }
            )

            MobileUserSettingsScreenPhotoEditComponent(
                urlString = null,
                label = "Arkaplanı Düzenle",
                onImagePicked = {
                    //TODO: Picked image should be stored
                }
            )

            AccountSettingsSelectToEnterInputComponent(
                title = "Kullanıcı Adı *",
                hint = "Kullanıcı Adı",
                value = userAccountState.userName,
                onValueChange = { viewModel.updateUserName(it) }
            )

            AccountSettingsSelectToEnterInputComponent(
                title = "Ad Soyad *",
                hint = "Ad Soyad",
                value = userAccountState.fullName,
                onValueChange = { viewModel.updateFullName(it) }
            )

            AccountSettingsSelectToEnterInputComponent(
                title = "Email *",
                hint = "Email",
                value = userAccountState.email,
                onValueChange = { viewModel.updateEmail(it) }
            )

            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showWeightDialog = true },
                title = "Boy (${userAccountState.heightUnit})",
                hint = "Boyunuzu girin",
                value = userAccountState.height?.toString() ?: ""
            )

            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showWeightDialog = true },
                title = "Kilo (${userAccountState.weightUnit})",
                hint = "Kilonuzu girin",
                value = userAccountState.weight?.toString() ?: ""
            )

            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showBodyTypeDialog = true },
                title = "Vücut Tipi",
                hint = "Ecto",
                value = userAccountState.bodyType.turkishShort
            )

            MobileUserSettingsActivityTagEditComponent(
                selectedTags = userAccountState.profileTags,
                onTagsSelected = viewModel::updateTags
            )

            MobileSettingsMenuItemComponent(
                text = "Şifremi Değiştir",
                iconRes = Res.drawable.ic_lock,
                onClick = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSettingsChangePassword) }
            )

            MobileSettingsMenuItemComponent(
                text = "Hesabı Sil",
                iconRes = Res.drawable.ic_delete,
                onClick =  { showDeleteConfirm = true  }
            )

            Spacer(Modifier.height(124.dp))
        }

        if (showWeightDialog) {
            WeightPickerDialog(
                initialWeight = userAccountState.weight,
                initialUnit = userAccountState.weightUnit,
                onDismiss = { showWeightDialog = false },
                onWeightSelected = { weight, unit ->
                    viewModel.updateWeight(weight)
                    viewModel.updateWeightUnit(unit)
                }
            )
        }

        if (showHeightDialog) {
            HeightPickerDialog(
                initialHeight = userAccountState.height,
                initialUnit = userAccountState.heightUnit,
                onDismiss = { showHeightDialog = false },
                onHeightSelected = { height, unit ->
                    viewModel.updateHeight(height)
                    viewModel.updateHeightUnit(unit)
                }
            )
        }

        if (showBodyTypeDialog) {
            BodyTypePickerDialog(
                initialBodyType = userAccountState.bodyType,
                onDismiss = { showBodyTypeDialog = false },
                onBodyTypeSelected = { viewModel.updateBodyType(it) }
            )
        }
    }
}

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
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
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
fun AccountSettingsSelectToEnterInputComponent(
    title: String = "Title",
    hint: String = "Hint",
    value: String? = null,
    error: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundColor = error?.let { SkyFitColor.background.surfaceCriticalActive }
        ?: SkyFitColor.background.surfaceSecondary

    Column(
        modifier = Modifier.fillMaxWidth(),
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
                        Text(text = hint, color = SkyFitColor.text.default)
                    } else {
                        innerTextField()
                    }
                }
            )

            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_pencil),
                contentDescription = "Edit",
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
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
    modifier: Modifier = Modifier,
    title: String = "Title",
    hint: String = "Hint",
    value: String? = null,
    rightIconRes: DrawableResource? = Res.drawable.ic_pencil
) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = modifier
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
fun MobileUserSettingsActivityTagEditComponent(
    selectedTags: List<String>,
    onTagsSelected: (List<String>) -> Unit
) {
    var showTagPickerDialog by remember { mutableStateOf(false) }

    Box {

        Column(Modifier.fillMaxWidth()) {
            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showTagPickerDialog = true },
                title = "Profil Etiketleri",
                hint = "Etiketlerini Sec örn: Pilates, Kondisyon",
                value = null
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
                    SkyFitAccountSettingsProfileTagItemComponent(value = it,
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
