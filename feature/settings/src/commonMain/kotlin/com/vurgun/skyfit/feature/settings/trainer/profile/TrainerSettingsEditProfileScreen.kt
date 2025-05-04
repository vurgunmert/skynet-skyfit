package com.vurgun.skyfit.feature.settings.trainer.profile

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.FitnessTagPickerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.MultiLineInputText
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.settings.shared.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature.settings.shared.component.SettingsEditProfileHeader
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_image_label
import skyfit.core.ui.generated.resources.error_save_profile_title
import skyfit.core.ui.generated.resources.ic_pencil
import skyfit.core.ui.generated.resources.profile_image_label
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_label
import skyfit.core.ui.generated.resources.settings_edit_profile_username_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_username_label
import skyfit.core.ui.generated.resources.user_first_name_hint
import skyfit.core.ui.generated.resources.user_first_name_mandatory_label
import skyfit.core.ui.generated.resources.user_last_name_hint
import skyfit.core.ui.generated.resources.user_last_name_mandatory_label

class TrainerSettingsEditProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerSettingsEditProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val saveErrorDialog = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when(effect) {
                TrainerEditProfileEffect.NavigateToBack -> navigator.pop()
                is TrainerEditProfileEffect.ShowSaveError -> saveErrorDialog.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            is TrainerEditProfileUiState.Loading -> FullScreenLoaderContent()

            is TrainerEditProfileUiState.Error -> ErrorScreen(
                message = (uiState as TrainerEditProfileUiState.Error).message,
                onConfirm = { navigator.pop() }
            )

            is TrainerEditProfileUiState.Content -> {
                val account = (uiState as TrainerEditProfileUiState.Content).form
                MobileTrainerSettingsEditProfileScreen(viewModel, account)
            }
        }

        saveErrorDialog.message?.let { message ->
            ErrorDialog(
                title = stringResource(Res.string.error_save_profile_title),
                message = message,
                onDismiss = saveErrorDialog::dismiss
            )
        }
    }
}

@Composable
fun MobileTrainerSettingsEditProfileScreen(
    viewModel: TrainerSettingsEditProfileViewModel,
    account: TrainerEditProfileFormState
) {
    val userNameFocusRequester = remember { FocusRequester() }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    SkyFitMobileScaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = account.isUpdated,
                onClickSave = { viewModel.onAction(TrainerEditProfileAction.SaveChanges) },
                onClickBack = { viewModel.onAction(TrainerEditProfileAction.NavigateToBack) }
            )
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
                    onImageChanged = { bytes, imageBitmap ->  }
                )
                Spacer(Modifier.width(16.dp))
                AccountSettingsEditableProfileImage(
                    title = stringResource(Res.string.profile_image_label),
                    url = null,
                    modifier = Modifier.weight(1f),
                    onClickDelete = { },
                    onImageChanged = { bytes, imageBitmap ->  }
                )
            }

            SingleLineInputText(
                title = stringResource(Res.string.settings_edit_profile_username_label),
                hint = stringResource(Res.string.settings_edit_profile_username_hint),
                value = account.userName,
                onValueChange = { viewModel.onAction(TrainerEditProfileAction.UpdateUserName(it)) },
                rightIconRes = Res.drawable.ic_pencil,
                focusRequester = userNameFocusRequester,
                nextFocusRequester = firstNameFocusRequester
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_first_name_mandatory_label),
                    hint = stringResource(Res.string.user_first_name_hint),
                    value = account.firstName,
                    onValueChange = { viewModel.onAction(TrainerEditProfileAction.UpdateFirstName(it)) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = firstNameFocusRequester,
                    nextFocusRequester = lastNameFocusRequester
                )
                Spacer(Modifier.width(16.dp))
                SingleLineInputText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.user_last_name_mandatory_label),
                    hint = stringResource(Res.string.user_last_name_hint),
                    value = account.lastName,
                    onValueChange = { viewModel.onAction(TrainerEditProfileAction.UpdateLastName(it)) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = lastNameFocusRequester
                )
            }

            MultiLineInputText(
                title = stringResource(Res.string.settings_edit_profile_biography_label),
                hint = stringResource(Res.string.settings_edit_profile_biography_hint),
                value = account.biography,
                onValueChange = { viewModel.onAction(TrainerEditProfileAction.UpdateBiography(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            FitnessTagPickerComponent(
                selectedTags = account.profileTags,
                onTagsSelected = { viewModel.onAction(TrainerEditProfileAction.UpdateTags(it)) }
            )

            Spacer(Modifier.height(124.dp))
        }
    }
}

