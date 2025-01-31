package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.SkyFitChipComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitUserAccountSettingsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserSettingsAccountScreen(navigator: Navigator) {

    val viewModel: SkyFitUserAccountSettingsViewModel = koinInject()

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showSave by remember { mutableStateOf(true) }

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
            MobileUserSettingsScreenInputComponent(viewModel)

            MobileSettingsMenuItemComponent("Şifremi Değiştir")
            Spacer(Modifier.height(32.dp))

            MobileSettingsMenuItemComponent("Hesabı Sil")
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MobileUserSettingsScreenInputComponent(viewModel: SkyFitUserAccountSettingsViewModel) {
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
        Text("Ad Soyad *", style = SkyFitTypography.bodySmallSemibold)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MobileUserSettingsActivityTagEditComponent(onClick: () -> Unit = {}) {
    Column(Modifier.fillMaxWidth()) {

        Text("Profil Etiketleri", style = SkyFitTypography.bodySmallSemibold)
        Row {
            SkyFitTextInputComponent(
                hint = "Pilates",
                value = "Pilates",
                onValueChange = { }
            )
            Spacer(Modifier.width(16.dp))
            SkyFitButtonComponent(
                Modifier.wrapContentWidth(),
                text = "Ekle",
                onClick = onClick,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Micro,
                initialState = ButtonState.Rest
            )
        }
        Spacer(Modifier.height(16.dp))
        SkyFitActivityTagLimitInfoComponent()

        var tagz = listOf(
            "Zorlu Antrenman",
            "Yağ Yakımı",
            "Kondisyon",
            "Beslenme",
            "Pilates"
        )

        FlowColumn(
            Modifier.padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachColumn = 3,
        ) {
            tagz.forEach {
                SkyFitChipComponent(text = it, onClick = {})
            }
        }
    }

}

@Composable
fun MobileUserSettingsActivityTagInputComponent(onClick: () -> Unit = {}) {
    SkyFitButtonComponent(
        Modifier.wrapContentWidth(),
        text = "Etiket başlığı örn: Pilates",
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Micro,
        initialState = ButtonState.Rest,
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}

@Composable
fun MobileUserSettingsScreenPhotoEditComponent(
    urlString: String?,
    label: String,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = urlString,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(onClick = onClick),
        )

        Spacer(Modifier.width(16.dp))

        SkyFitButtonComponent(
            Modifier.width(190.dp),
            text = label,
            onClick = onClick,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Medium,
            initialState = ButtonState.Rest,
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

@Composable
fun MobileUserSettingsScreenSaveActionComponent(onClick: () -> Unit) {
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
fun MobileUserSettingsScreenDeleteActionsComponent(
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

@Composable
private fun SkyFitActivityTagLimitInfoComponent() {

    Box(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceCautionActive, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = "Notification Icon",
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.caution
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Etiket Sayısı",
                    color = SkyFitColor.text.default,
                    style = SkyFitTypography.bodySmallSemibold,
                    modifier = Modifier.weight(1f).padding(end = 16.dp),
                    maxLines = 2
                )
            }
            Text(
                text = "Profilinizde en fazla 5 etiket bulundurabilirsiniz.",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = SkyFitColor.text.default,
                style = SkyFitTypography.bodySmall
            )
        }
    }
}