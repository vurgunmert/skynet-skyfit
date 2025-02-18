package com.vurgun.skyfit.presentation.mobile.features.facility.settings


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsActivityTagEditComponent
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilitySettingsAccountScreen(navigator: Navigator) {

    var saveEnabled: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Hesap Ayarlari", onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            if (saveEnabled) {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    MobileFacilitySettingsAccountScreenSaveActionComponent(
                        onClick = { saveEnabled = false }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilitySettingsAccountScreenInputComponent()

            MobileSettingsMenuItemComponent("Şifremi Değiştir")
            Spacer(Modifier.height(32.dp))

            MobileSettingsMenuItemComponent("Hesabı Sil")
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MobileFacilitySettingsAccountScreenInputComponent() {

    Spacer(Modifier.height(24.dp))
    MobileUserSettingsScreenPhotoEditComponent("backgroundImageUrl", label = "Arkaplanı Düzenle") {

    }
    Spacer(Modifier.height(24.dp))
//    MobileUserSettingsActivityTagEditComponent()
}

@Composable
private fun MobileFacilitySettingsAccountScreenSaveActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Değişiklikleri Kaydet",
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}