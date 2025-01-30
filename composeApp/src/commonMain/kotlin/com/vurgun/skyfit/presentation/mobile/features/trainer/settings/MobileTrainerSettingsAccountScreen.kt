package com.vurgun.skyfit.presentation.mobile.features.trainer.settings

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsActivityTagEditComponent
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreenDeleteActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreenSaveActionComponent
import com.vurgun.skyfit.presentation.shared.components.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitTrainerAccountSettingsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerSettingsAccountScreen(navigator: Navigator) {

    val viewModel: SkyFitTrainerAccountSettingsViewModel = koinInject()

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showSave by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Hesap Ayarlari", onBackClick = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileUserSettingsScreenDeleteActionsComponent(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = {}
                )
            } else if (showSave) {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    MobileUserSettingsScreenSaveActionComponent(
                        onClick = { showSave = false }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileTrainerSettingsAccountScreenInputComponent(viewModel)

            MobileSettingsMenuItemComponent("Şifremi Değiştir")
            Spacer(Modifier.height(32.dp))

            MobileSettingsMenuItemComponent("Hesabı Sil")
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MobileTrainerSettingsAccountScreenInputComponent(viewModel: SkyFitTrainerAccountSettingsViewModel) {
    // Collect state from ViewModel
    val userName by viewModel.userName.collectAsState()
    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val height by viewModel.height.collectAsState()
    val heightUnit by viewModel.heightUnit.collectAsState()
    val weightUnit by viewModel.weightUnit.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val bodyType by viewModel.bodyType.collectAsState()
    val profileImageUrl by viewModel.profileImageUrl.collectAsState()
    val backgroundImageUrl by viewModel.backgroundImageUrl.collectAsState()
    val isAnyUpdated by viewModel.isAnyUpdated.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MobileUserSettingsScreenPhotoEditComponent("profileImageUrl", label = "Fotoğrafı Düzenle") {

        }
        Spacer(Modifier.height(24.dp))
        MobileUserSettingsScreenPhotoEditComponent("backgroundImageUrl", label = "Arkaplanı Düzenle") {

        }

        Spacer(Modifier.height(24.dp))
        Text("Kullanici Adi", style = SkyFitTypography.bodySmallSemibold)
        Spacer(Modifier.height(4.dp))
        SkyFitTextInputComponent(
            hint = "Ad Soyad *",
            value = userName,
            onValueChange = { },
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )

        Spacer(Modifier.height(24.dp))
        Text("Ad Soyad *", style = SkyFitTypography.bodySmallSemibold)
        Spacer(Modifier.height(4.dp))
        SkyFitTextInputComponent(
            hint = "Ad Soyad *",
            value = userName,
            onValueChange = { },
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )

        Spacer(Modifier.height(24.dp))
        Text("Email *", style = SkyFitTypography.bodySmallSemibold)
        Spacer(Modifier.height(4.dp))
        SkyFitTextInputComponent(
            hint = "Ad Soyad *",
            value = userName,
            onValueChange = { },
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )

        Spacer(Modifier.height(24.dp))
        Text("Biyografi *", style = SkyFitTypography.bodySmallSemibold)
        Spacer(Modifier.height(4.dp))
        SkyFitTextInputComponent(
            hint = "Ad Soyad *",
            value = userName,
            onValueChange = { },
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )

        MobileUserSettingsActivityTagEditComponent()
    }
}