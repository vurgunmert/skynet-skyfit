package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserSettingsAccountScreen(navigator: Navigator) {

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
            } else if(showSave) {
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSettingsScreenInputComponent()

            MobileSettingsMenuItemComponent("Şifremi Değiştir")
            Spacer(Modifier.height(32.dp))

            MobileSettingsMenuItemComponent("Hesabı Sil")
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MobileUserSettingsScreenInputComponent() {
    TodoBox("MobileUserSettingsScreenInputComponent", Modifier.size(382.dp, 184.dp))
}

@Composable
private fun MobileUserSettingsScreenSaveActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        Modifier.fillMaxWidth(), text = "Değişiklikleri Kaydet",
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        initialState = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

}

@Composable
private fun MobileUserSettingsScreenDeleteActionsComponent(
    onDeleteClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(24.dp)
    ) {
        Text(
            text = "Emin misiniz?",
            style = SkyFitTypography.heading4,
            color = SkyFitColor.text.default,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Hesabınızı sildiğinizde bu işlemi geri alamayacaksınız. Profiliniz, fotoğraflarınız, notlarınız, tepkileriniz ve takipçileriniz dahil tüm verileriniz kaybolacak.",
            style = SkyFitTypography.bodyLargeMedium,
            color = SkyFitColor.text.secondary,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(24.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = "Hesabı Sil",
            onClick = onDeleteClicked,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
        Spacer(Modifier.height(24.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = "İptal",
            onClick = onCancelClicked,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
    }
}