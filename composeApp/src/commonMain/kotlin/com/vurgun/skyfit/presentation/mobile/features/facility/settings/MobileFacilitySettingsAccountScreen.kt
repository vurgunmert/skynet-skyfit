package com.vurgun.skyfit.presentation.mobile.features.facility.settings


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
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.settings.AccountSettingsSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileUserSettingsActivityTagEditComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileUserSettingsScreenDeleteActionsComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileUserSettingsScreenSaveActionComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_lock

@Composable
fun MobileFacilitySettingsAccountScreen(navigator: Navigator) {

    val viewModel = remember { FacilityAccountSettingsViewModel() }

    val accountState by viewModel.accountState.collectAsState()
    val scrollState = rememberScrollState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

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

            MobileUserSettingsScreenPhotoEditComponent(
                urlString = null,
                label = "Arkaplanı Düzenle",
                onImagePicked = {
                    //TODO: Picked image should be stored
                }
            )

            AccountSettingsSelectToEnterMultilineInputComponent(
                title = "Biyografi *",
                hint = "Biyografi bilgilerinizi girin",
                value = accountState.biography,
                onValueChange = { viewModel.updateBiography(it) }
            )

            AccountSettingsSelectToEnterMultilineInputComponent(
                title = "Adres *",
                hint = "Adres bilgilerinizi girin",
                value = accountState.location,
                onValueChange = { viewModel.updateLocation(it) }
            )

            MobileUserSettingsActivityTagEditComponent(
                selectedTags = accountState.profileTags,
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
                onClick = { showDeleteConfirm = true }
            )

            Spacer(Modifier.height(124.dp))
        }
    }
}

