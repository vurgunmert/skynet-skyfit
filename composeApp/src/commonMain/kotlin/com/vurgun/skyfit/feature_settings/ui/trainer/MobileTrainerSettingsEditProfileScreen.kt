package com.vurgun.skyfit.feature_settings.ui.trainer

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.designsystem.components.text.MultiLineInputText
import com.vurgun.skyfit.designsystem.components.text.SingleLineInputText
import com.vurgun.skyfit.feature_settings.ui.FitnessTagPickerComponent
import com.vurgun.skyfit.feature_settings.ui.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature_settings.ui.component.SettingsEditProfileHeader
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.background_image_label
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.profile_image_label
import skyfit.composeapp.generated.resources.settings_edit_profile_biography_hint
import skyfit.composeapp.generated.resources.settings_edit_profile_biography_label
import skyfit.composeapp.generated.resources.settings_edit_profile_username_hint
import skyfit.composeapp.generated.resources.settings_edit_profile_username_label
import skyfit.composeapp.generated.resources.user_first_name_hint
import skyfit.composeapp.generated.resources.user_first_name_mandatory_label
import skyfit.composeapp.generated.resources.user_last_name_hint
import skyfit.composeapp.generated.resources.user_last_name_mandatory_label

@Composable
fun MobileTrainerSettingsEditProfileScreen(navigator: Navigator) {

    val viewModel: TrainerAccountSettingsViewModel = koinInject()

    val accountState by viewModel.accountState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val userNameFocusRequester = remember { FocusRequester() }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    SkyFitMobileScaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = accountState.isUpdated,
                onClickSave = viewModel::saveChanges,
                onClickBack = { navigator.popBackStack() })
        }) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                AccountSettingsEditableProfileImage(
                    title = stringResource(Res.string.background_image_label),
                    url = null,
                    modifier = Modifier.weight(1f),
                    onClickDelete = { },
                    onImageChanged = {}
                )
                Spacer(Modifier.width(16.dp))
                AccountSettingsEditableProfileImage(
                    title = stringResource(Res.string.profile_image_label),
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
                value = accountState.userName,
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
                    value = accountState.firstName,
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
                    value = accountState.lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = lastNameFocusRequester
                )
            }

            MultiLineInputText(
                title = stringResource(Res.string.settings_edit_profile_biography_label),
                hint = stringResource(Res.string.settings_edit_profile_biography_hint),
                value = accountState.biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            FitnessTagPickerComponent(
                selectedTags = accountState.profileTags,
                onTagsSelected = viewModel::updateTags
            )

            Spacer(Modifier.height(124.dp))
        }
    }
}

