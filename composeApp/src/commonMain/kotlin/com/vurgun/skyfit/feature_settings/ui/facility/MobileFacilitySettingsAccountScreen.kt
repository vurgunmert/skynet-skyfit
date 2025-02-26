package com.vurgun.skyfit.feature_settings.ui.facility


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
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsActivityTagEditComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenDeleteActionsComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenSaveActionComponent
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_lock
import skyfit.composeapp.generated.resources.ic_pencil

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

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Isyeri Adi *",
                hint = "Profilinizde gozukecek isyeri adi",
                value = accountState.name,
                onValueChange = { viewModel.updateName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Biyografi *",
                hint = "Biyografi bilgilerinizi girin",
                value = accountState.biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Adres *",
                hint = "Adres bilgilerinizi girin",
                value = accountState.location,
                onValueChange = { viewModel.updateLocation(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            MobileUserSettingsActivityTagEditComponent(
                selectedTags = accountState.profileTags,
                onTagsSelected = viewModel::updateTags
            )

            MobileSettingsMenuItemComponent(
                text = "Şifremi Değiştir",
                iconRes = Res.drawable.ic_lock,
                onClick = { navigator.jumpAndStay(NavigationRoute.UserSettingsChangePassword) }
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

