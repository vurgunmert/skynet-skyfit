package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.dialog.ProfileTagPicker
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

@Composable
fun MobileSettingsDeleteAccountBottomSheet(
    onDeleteClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SkyFitColor.background.surfaceSecondary,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.settings_delete_account_dialog_title),
            style = SkyFitTypography.heading4,
            color = SkyFitColor.text.default,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.settings_delete_account_dialog_message),
            style = SkyFitTypography.bodyLargeMedium,
            color = SkyFitColor.text.secondary,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(24.dp))
        PrimaryLargeButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = stringResource(Res.string.delete_account_action),
            onClick = onDeleteClicked,
            leftIconPainter = painterResource(Res.drawable.ic_delete)
        )
        Spacer(Modifier.height(24.dp))
        SecondaryLargeButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = stringResource(Res.string.cancel_action),
            onClick = onCancelClicked
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
                    contentDescription = null,
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
    availableTags: List<ProfileTag>,
    selectedTags: List<ProfileTag>,
    onTagsSelected: (List<ProfileTag>) -> Unit
) {
    var showTagPickerDialog by remember { mutableStateOf(false) }

    Box {
        Column(modifier.fillMaxWidth()) {
            AccountSettingsSelectToSetInputComponent(
                modifier = Modifier.clickable { showTagPickerDialog = true },
                title = stringResource(Res.string.profile_tags_label),
                hint = stringResource(Res.string.select_profile_tags_hint),
                value = null,
                rightIconRes = Res.drawable.ic_pencil
            )
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(
                        SkyFitColor.background.surfaceCautionActive,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_warning),
                            contentDescription = null,
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
                        text = stringResource(Res.string.select_profile_limit_message),
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
                        value = it.name,
                        onClick = { onTagsSelected(selectedTags - it) })
                }
            }
        }

        if (showTagPickerDialog) {
            ProfileTagPicker(
                availableTags = availableTags,
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
                .background(
                    SkyFitColor.background.surfaceSecondary,
                    shape = RoundedCornerShape(20.dp)
                )
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
