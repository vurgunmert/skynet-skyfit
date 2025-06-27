package com.vurgun.skyfit.settings.facility.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.FitnessTagPickerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsEditProfileHeader
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class FacilityProfileSettingsScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityProfileSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val saveErrorDialog = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityProfileSettingsEffect.NavigateToBack -> navigator.pop()
                is FacilityProfileSettingsEffect.ShowSaveError -> saveErrorDialog.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityProfileSettingsUiState.Loading -> FullScreenLoaderContent()
            is FacilityProfileSettingsUiState.Error -> {
                val message = (uiState as FacilityProfileSettingsUiState.Error).message
                saveErrorDialog.show(message)
            }

            is FacilityProfileSettingsUiState.Content -> {
                val content = (uiState as FacilityProfileSettingsUiState.Content)
                FacilitySettingsContent(
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
private fun FacilitySettingsContent(
    content: FacilityProfileSettingsUiState.Content,
    onAction: (FacilityProfileSettingsUiAction) -> Unit,
) {
    val formState = content.form
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = formState.isUpdated,
                onClickSave = { onAction(FacilityProfileSettingsUiAction.SaveChanges) },
                onClickBack = { onAction(FacilityProfileSettingsUiAction.NavigateToBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            AccountSettingsEditableProfileImage(
                title = stringResource(Res.string.background_image_label),
                url = formState.backgroundImageUrl,
                modifier = Modifier.fillMaxWidth(),
                onClickDelete = {
                    onAction(FacilityProfileSettingsUiAction.DeleteBackgroundImage)
                },
                onImageChanged = { bytes, _ ->
                    onAction(FacilityProfileSettingsUiAction.UpdateBackgroundImage(bytes))
                }
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.mandatory_workplace_name_label),
                hint = stringResource(Res.string.workplace_name_hint),
                value = formState.name,
                onValueChange = { onAction(FacilityProfileSettingsUiAction.UpdateName(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_biography_label),
                hint = stringResource(Res.string.settings_edit_profile_biography_hint),
                value = formState.biography,
                onValueChange = { onAction(FacilityProfileSettingsUiAction.UpdateBiography(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_address_label),
                hint = stringResource(Res.string.settings_edit_profile_address_hint),
                value = formState.location,
                onValueChange = { onAction(FacilityProfileSettingsUiAction.UpdateLocation(it)) },
                rightIconRes = Res.drawable.ic_pencil
            )

            FitnessTagPickerComponent(
                availableTags = formState.allTags,
                selectedTags = formState.profileTags,
                onTagsSelected = { onAction(FacilityProfileSettingsUiAction.UpdateTags(it)) },
            )

            Spacer(Modifier.height(128.dp))
        }
    }
}

