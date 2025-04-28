package com.vurgun.skyfit.feature.settings.facility.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.settings.component.AccountSettingsEditableProfileImage
import com.vurgun.skyfit.feature.settings.component.SettingsEditProfileHeader
import com.vurgun.skyfit.core.ui.components.special.FitnessTagPickerComponent
import com.vurgun.skyfit.core.ui.components.special.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.core.ui.components.special.MobileUserSettingsScreenSaveActionComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitSelectToEnterMultilineInputComponent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_image_label
import skyfit.core.ui.generated.resources.ic_pencil
import skyfit.core.ui.generated.resources.mandatory_workplace_name_label
import skyfit.core.ui.generated.resources.settings_edit_profile_address_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_address_label
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_hint
import skyfit.core.ui.generated.resources.settings_edit_profile_biography_label
import skyfit.core.ui.generated.resources.workplace_name_hint

@Composable
fun MobileFacilitySettingsEditProfileScreen(
    goToBack: () -> Unit,
    viewModel: FacilityManageProfileViewModel = koinViewModel()
) {
    val accountState by viewModel.accountState.collectAsState()
    val scrollState = rememberScrollState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            SettingsEditProfileHeader(
                showSave = accountState.isUpdated,
                onClickSave = viewModel::saveChanges,
                onClickBack = goToBack
            )
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = {}
                )
            } else if (accountState.isUpdated) {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    MobileUserSettingsScreenSaveActionComponent(onClick = viewModel::saveChanges)
                }
            }
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
                onClickDelete = { },
                onImageChanged = { }
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.mandatory_workplace_name_label),
                hint = stringResource(Res.string.workplace_name_hint),
                value = accountState.name,
                onValueChange = { viewModel.updateName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_biography_label),
                hint = stringResource(Res.string.settings_edit_profile_biography_hint),
                value = accountState.biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.settings_edit_profile_address_label),
                hint = stringResource(Res.string.settings_edit_profile_address_hint),
                value = accountState.location,
                onValueChange = { viewModel.updateLocation(it) },
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

