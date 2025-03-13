package com.vurgun.skyfit.feature_settings.ui.trainer

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
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.feature_settings.ui.FitnessTagPickerComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenDeleteActionsComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenSaveActionComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_lock
import skyfit.composeapp.generated.resources.ic_pencil

@Composable
fun MobileTrainerSettingsAccountScreen(navigator: Navigator) {

    val viewModel: TrainerAccountSettingsViewModel = koinInject()

    val trainerAccountState by viewModel.accountState.collectAsState()

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
            } else if (trainerAccountState.isUpdated) {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    MobileUserSettingsScreenSaveActionComponent(onClick = viewModel::saveChanges)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            MobileUserSettingsScreenPhotoEditComponent(
                urlString = null,
                label = "Fotoğrafı Düzenle",
                onImagePicked = {
                    //TODO: Picked image should be stored
                }
            )

            MobileUserSettingsScreenPhotoEditComponent(
                urlString = null,
                label = "Arkaplanı Düzenle",
                onImagePicked = {
                    //TODO: Picked image should be stored
                }
            )

            SkyFitSelectToEnterInputComponent(
                title = "Kullanıcı Adı *",
                hint = "Kullanıcı Adı",
                value = trainerAccountState.userName,
                onValueChange = { viewModel.updateUserName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterInputComponent(
                title = "Ad Soyad *",
                hint = "Ad Soyad",
                value = trainerAccountState.fullName,
                onValueChange = { viewModel.updateFullName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterInputComponent(
                title = "Email *",
                hint = "Email",
                value = trainerAccountState.email,
                onValueChange = { viewModel.updateEmail(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Biyografi *",
                hint = "Biyografi bilgilerinizi girin",
                value = trainerAccountState.biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil
            )


            FitnessTagPickerComponent(
                selectedTags = trainerAccountState.profileTags,
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

