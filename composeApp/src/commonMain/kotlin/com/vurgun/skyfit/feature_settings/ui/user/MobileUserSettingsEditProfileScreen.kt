package com.vurgun.skyfit.feature_settings.ui.user

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
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.designsystem.components.picker.HeightAndUnitPickerDialog
import com.vurgun.skyfit.designsystem.components.picker.WeightAndUnitPickerDialog
import com.vurgun.skyfit.designsystem.components.text.SingleLineInputText
import com.vurgun.skyfit.designsystem.components.text.TitledMediumRegularText
import com.vurgun.skyfit.feature_settings.ui.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature_settings.ui.component.SettingsEditProfileHeader
import com.vurgun.skyfit.feature_settings.ui.component.SettingsSelectBodyTypePopupMenu
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_chevron_down
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.settings_edit_profile_body_type_label
import skyfit.composeapp.generated.resources.settings_edit_profile_height_label
import skyfit.composeapp.generated.resources.settings_edit_profile_username_hint
import skyfit.composeapp.generated.resources.settings_edit_profile_username_label
import skyfit.composeapp.generated.resources.settings_edit_profile_weight_label
import skyfit.composeapp.generated.resources.user_first_name_hint
import skyfit.composeapp.generated.resources.user_first_name_mandatory_label
import skyfit.composeapp.generated.resources.user_last_name_hint
import skyfit.composeapp.generated.resources.user_last_name_mandatory_label

@Composable
fun MobileUserSettingsEditProfileScreen(navigator: Navigator) {

    val viewModel: SkyFitUserAccountSettingsViewModel = koinInject()

    val userAccountState by viewModel.accountState.collectAsState()

    var showWeightDialog by remember { mutableStateOf(false) }
    var showHeightDialog by remember { mutableStateOf(false) }
    var showBodyTypeDialog by remember { mutableStateOf(false) }

    val userNameFocusRequester = remember { FocusRequester() }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = userAccountState.isUpdated,
                onClickSave = viewModel::saveChanges,
                onClickBack = { navigator.popBackStack() })
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
                    url = null,
                    modifier = Modifier.weight(1f),
                    onClickDelete = { },
                    onImageChanged = {}
                )
                Spacer(Modifier.width(16.dp))
                AccountSettingsEditableProfileImage(
                    title = "Profil Resmi",
                    url = null,
                    modifier = Modifier.weight(1f),
                    onClickDelete = { },
                    onImageChanged = {
                        //TODO: Picked image should be stored
                    }
                )
            }

            SingleLineInputText(
                title = stringResource(Res.string.settings_edit_profile_username_label),
                hint = stringResource(Res.string.settings_edit_profile_username_hint),
                value = userAccountState.userName,
                onValueChange = { viewModel.updateUserName(it) },
                rightIconRes = Res.drawable.ic_pencil,
                focusRequester = userNameFocusRequester,
                nextFocusRequester = firstNameFocusRequester
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_first_name_mandatory_label),
                    hint = stringResource(Res.string.user_first_name_hint),
                    value = userAccountState.firstName,
                    onValueChange = { viewModel.updateFirstName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = firstNameFocusRequester,
                    nextFocusRequester = lastNameFocusRequester
                )
                Spacer(Modifier.width(16.dp))
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_last_name_mandatory_label),
                    hint = stringResource(Res.string.user_last_name_hint),
                    value = userAccountState.lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = lastNameFocusRequester
                )
            }

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showHeightDialog = true },
                title = stringResource(Res.string.settings_edit_profile_height_label, userAccountState.heightUnit.label),
                value = userAccountState.height.toString(),
                rightIconRes = Res.drawable.ic_chevron_down
            )

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showWeightDialog = true },
                title = stringResource(Res.string.settings_edit_profile_weight_label, userAccountState.weightUnit.shortLabel),
                value = userAccountState.weight.toString(),
                rightIconRes = Res.drawable.ic_chevron_down
            )

            TitledMediumRegularText(
                modifier = Modifier.fillMaxWidth().clickable { showBodyTypeDialog = true },
                title = stringResource(Res.string.settings_edit_profile_body_type_label),
                value = userAccountState.bodyType.turkishShort,
                rightIconRes = Res.drawable.ic_chevron_down
            )
            if (showBodyTypeDialog) {
                Spacer(Modifier.height(6.dp))
                SettingsSelectBodyTypePopupMenu(
                    modifier = Modifier.fillMaxWidth(),
                    isOpen = showBodyTypeDialog,
                    onDismiss = { showBodyTypeDialog = false },
                    selectedBodyType = userAccountState.bodyType,
                    onSelectionChanged = viewModel::updateBodyType
                )
            }
        }

        if (showHeightDialog) {
            HeightAndUnitPickerDialog(
                height = userAccountState.height,
                heightUnit = userAccountState.heightUnit,
                onConfirm = { height, unit ->
                    viewModel.updateHeight(height)
                    viewModel.updateHeightUnit(unit)
                },
                onDismiss = { showHeightDialog = false }
            )
        }

        if (showWeightDialog) {
            WeightAndUnitPickerDialog(
                weight = userAccountState.weight,
                weightUnit = userAccountState.weightUnit,
                onConfirm = { weight, unit ->
                    viewModel.updateWeight(weight)
                    viewModel.updateWeightUnit(unit)
                },
                onDismiss = { showWeightDialog = false }
            )
        }
    }
}
