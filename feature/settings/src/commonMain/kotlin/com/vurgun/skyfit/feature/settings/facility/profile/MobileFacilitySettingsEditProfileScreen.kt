package com.vurgun.skyfit.feature.settings.facility.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.vurgun.skyfit.core.ui.components.special.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.settings.shared.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature.settings.shared.component.SettingsEditProfileHeader
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_image_label
import skyfit.core.ui.generated.resources.error_save_profile_title
import skyfit.core.ui.generated.resources.ic_pencil
import skyfit.core.ui.generated.resources.mandatory_workplace_name_label
import skyfit.core.ui.generated.resources.settings_edit_profile_address_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_address_label
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_label
import skyfit.core.ui.generated.resources.workplace_name_hint

class FacilitySettingsEditProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityEditProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val saveErrorDialog = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityEditProfileEffect.NavigateToBack -> navigator.pop()
                is FacilityEditProfileEffect.ShowSaveError -> saveErrorDialog.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityEditProfileUiState.Loading -> FullScreenLoaderContent()
            is FacilityEditProfileUiState.Error -> {
                val message = (uiState as FacilityEditProfileUiState.Error).message
                saveErrorDialog.show(message)
            }

            is FacilityEditProfileUiState.Content -> {
                val content = (uiState as FacilityEditProfileUiState.Content)
                MobileFacilitySettingsEditProfileScreen(
                    content = content,
                    onAction = viewModel::onAction
                )
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
fun MobileFacilitySettingsEditProfileScreen(
    content: FacilityEditProfileUiState.Content,
    onAction: (FacilityEditProfileAction) -> Unit,
) {
    val formState = content.form
    val scrollState = rememberScrollState()

    SkyFitMobileScaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = formState.isUpdated,
                onClickSave = { onAction(FacilityEditProfileAction.SaveChanges) },
                onClickBack = { onAction(FacilityEditProfileAction.NavigateToBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            AccountSettingsEditableProfileImage(
                title = stringResource(Res.string.background_image_label),
                url = null,
                modifier = Modifier.fillMaxWidth(),
                onClickDelete = {
                    onAction(FacilityEditProfileAction.DeleteBackgroundImage)
                },
                onImageChanged = { bytes, _ ->
                    onAction(FacilityEditProfileAction.UpdateBackgroundImage(bytes))
                }
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.mandatory_workplace_name_label),
                hint = stringResource(Res.string.workplace_name_hint),
                value = formState.name,
                onValueChange = { onAction(FacilityEditProfileAction.UpdateName(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_biography_label),
                hint = stringResource(Res.string.settings_edit_profile_biography_hint),
                value = formState.biography,
                onValueChange = { onAction(FacilityEditProfileAction.UpdateBiography(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_address_label),
                hint = stringResource(Res.string.settings_edit_profile_address_hint),
                value = formState.location,
                onValueChange = { onAction(FacilityEditProfileAction.UpdateLocation(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            FitnessTagPickerComponent(
                selectedTags = formState.profileTags,
                onTagsSelected = { onAction(FacilityEditProfileAction.UpdateTags(it)) },
            )

            Spacer(Modifier.height(124.dp))
        }
    }
}

