package com.vurgun.skyfit.feature.persona.settings.user.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.picker.HeightAndUnitPickerDialog
import com.vurgun.skyfit.core.ui.components.picker.WeightAndUnitPickerDialog
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.components.text.TitledMediumRegularText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsEditProfileHeader
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsSelectBodyTypePopupMenu
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_down
import skyfit.core.ui.generated.resources.ic_pencil
import skyfit.core.ui.generated.resources.settings_edit_profile_body_type_label
import skyfit.core.ui.generated.resources.settings_edit_profile_height_label
import skyfit.core.ui.generated.resources.settings_edit_profile_username_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_username_label
import skyfit.core.ui.generated.resources.settings_edit_profile_weight_label
import skyfit.core.ui.generated.resources.user_first_name_hint
import skyfit.core.ui.generated.resources.user_first_name_mandatory_label
import skyfit.core.ui.generated.resources.user_last_name_hint
import skyfit.core.ui.generated.resources.user_last_name_mandatory_label

class UserProfileSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserProfileSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val saveError = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserEditProfileEffect.NavigateToBack -> navigator.pop()
                is UserEditProfileEffect.ShowSaveError -> saveError.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            UserEditProfileUiState.Loading -> FullScreenLoaderContent()
            is UserEditProfileUiState.Error -> {
                val message = (uiState as UserEditProfileUiState.Error).message
                ErrorScreen(
                    message = message,
                    onConfirm = { navigator.pop() }
                )
            }

            is UserEditProfileUiState.Content -> {
                val formState = (uiState as UserEditProfileUiState.Content).form

                MobileUserSettingsEditProfileScreen(
                    viewModel = viewModel,
                    formState = formState
                )
            }
        }

        saveError.message?.let { message ->
            ErrorDialog(
                title = "PROFIL guncelleme hatasi",
                message = message,
                onDismiss = saveError::dismiss
            )
        }
    }
}

@Composable
private fun MobileUserSettingsEditProfileScreen(
    viewModel: UserProfileSettingsViewModel,
    formState: UserEditProfileFormState
) {

    var showWeightDialog by remember { mutableStateOf(false) }
    var showHeightDialog by remember { mutableStateOf(false) }
    var showBodyTypeDialog by remember { mutableStateOf(false) }

    val userNameFocusRequester = remember { FocusRequester() }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = formState.isUpdated,
                onClickSave = { viewModel.onAction(UserEditProfileAction.SaveChanges) },
                onClickBack = { viewModel.onAction(UserEditProfileAction.NavigateToBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row {
                AccountSettingsEditableProfileImage(
                    title = "Arkaplan Resmi",
                    url = formState.backgroundImageUrl,
                    modifier = Modifier.weight(1f),
                    onClickDelete = {
                        viewModel.onAction(UserEditProfileAction.DeleteBackgroundImage)
                    },
                    onImageChanged = { bytes, imageBitmap ->
                        viewModel.onAction(UserEditProfileAction.UpdateBackgroundImage(bytes))
                    }
                )
                Spacer(Modifier.width(16.dp))
                AccountSettingsEditableProfileImage(
                    title = "Profil Resmi",
                    url = formState.profileImageUrl,
                    modifier = Modifier.weight(1f),
                    onClickDelete = {
                        viewModel.onAction(UserEditProfileAction.DeleteProfileImage)
                    },
                    onImageChanged = { bytes, imageBitmap ->
                        viewModel.onAction(UserEditProfileAction.UpdateProfileImage(bytes))
                    }
                )
            }

            SingleLineInputText(
                title = stringResource(Res.string.settings_edit_profile_username_label),
                hint = stringResource(Res.string.settings_edit_profile_username_hint),
                value = formState.userName,
                onValueChange = { viewModel.onAction(UserEditProfileAction.UpdateUserName(it)) },
                rightIconRes = Res.drawable.ic_pencil,
                focusRequester = userNameFocusRequester,
                nextFocusRequester = firstNameFocusRequester
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_first_name_mandatory_label),
                    hint = stringResource(Res.string.user_first_name_hint),
                    value = formState.firstName,
                    onValueChange = { viewModel.onAction(UserEditProfileAction.UpdateFirstName(it)) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = firstNameFocusRequester,
                    nextFocusRequester = lastNameFocusRequester
                )
                Spacer(Modifier.width(16.dp))
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_last_name_mandatory_label),
                    hint = stringResource(Res.string.user_last_name_hint),
                    value = formState.lastName,
                    onValueChange = { viewModel.onAction(UserEditProfileAction.UpdateLastName(it)) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = lastNameFocusRequester
                )
            }

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showHeightDialog = true },
                title = stringResource(Res.string.settings_edit_profile_height_label, formState.heightUnit.label),
                value = formState.height.toString(),
                rightIconRes = Res.drawable.ic_chevron_down
            )

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showWeightDialog = true },
                title = stringResource(Res.string.settings_edit_profile_weight_label, formState.weightUnit.shortLabel),
                value = formState.weight.toString(),
                rightIconRes = Res.drawable.ic_chevron_down
            )

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showBodyTypeDialog = true },
                title = stringResource(Res.string.settings_edit_profile_body_type_label),
                value = formState.bodyType.turkishShort,
                rightIconRes = Res.drawable.ic_chevron_down
            )
            if (showBodyTypeDialog) {
                Spacer(Modifier.height(6.dp))
                SettingsSelectBodyTypePopupMenu(
                    modifier = Modifier.fillMaxWidth(),
                    isOpen = showBodyTypeDialog,
                    onDismiss = { showBodyTypeDialog = false },
                    selectedBodyType = formState.bodyType,
                    onSelectionChanged = { viewModel.onAction(UserEditProfileAction.UpdateBodyType(it)) },
                )
            }
        }

        if (showHeightDialog) {
            HeightAndUnitPickerDialog(
                height = formState.height,
                heightUnit = formState.heightUnit,
                onConfirm = { height, unit ->
                    viewModel.onAction(UserEditProfileAction.UpdateHeight(height))
                },
                onDismiss = { showHeightDialog = false }
            )
        }

        if (showWeightDialog) {
            WeightAndUnitPickerDialog(
                weight = formState.weight,
                weightUnit = formState.weightUnit,
                onConfirm = { weight, unit ->
                    viewModel.onAction(UserEditProfileAction.UpdateWeight(weight))
                },
                onDismiss = { showWeightDialog = false }
            )
        }
    }
}
