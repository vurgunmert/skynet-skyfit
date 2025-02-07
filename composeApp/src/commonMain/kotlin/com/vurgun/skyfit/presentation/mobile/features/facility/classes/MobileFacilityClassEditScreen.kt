package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitCheckBoxComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityClassEditScreen(navigator: Navigator) {

    val saveEnabled: Boolean = true
    var showCancelDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Dersi Duzenle", onClickBack = {
                if (saveEnabled) {
                    showCancelDialog = true
                } else {
                    navigator.popBackStack()
                }
            })
        },
        bottomBar = {
            MobileFacilityClassEditScreenActionComponent(enabled = saveEnabled, onClick = {})
        }
    ) {
        MobileFacilityClassEditScreenInputComponent()

        MobileFacilityClassEditScreenCancelDialog(
            showDialog = showCancelDialog,
            onClickDismiss = { showCancelDialog = false },
            onClickExit = { navigator.popBackStack() }
        )
    }
}

@Composable
private fun MobileFacilityClassEditScreenInputComponent() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MobileFacilityClassEditInfoInputGroup()

        Spacer(Modifier.height(8.dp))
        MobileFacilityClassEditTrainerInputGroup()

        Spacer(Modifier.height(8.dp))
        MobileFacilityClassEditTimingInputGroup()

        Spacer(Modifier.height(8.dp))
        MobileFacilityClassEditCapacityInputGroup()

        Spacer(Modifier.height(8.dp))
        MobileFacilityClassEditAppointmentInputGroup()

        Spacer(Modifier.height(16.dp))
        MobileFacilityClassEditPaymentInputGroup()

        Spacer(Modifier.height(16.dp))
        MobileFacilityClassEditSelectUserGroupComponent()
    }

}

@Composable
private fun MobileFacilityClassEditInfoInputGroup() {
    Row {
        Column {
            Text("Ikon", style = SkyFitTypography.bodyMediumSemibold)
            Spacer(Modifier.height(8.dp))
            SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit))
        }
        Column {
            Text("Antreman basligi", style = SkyFitTypography.bodyMediumSemibold)
            Spacer(Modifier.height(8.dp))
            SkyFitTextInputComponent("Antreman basligi")
        }
    }
}

@Composable
private fun MobileFacilityClassEditTrainerInputGroup() {
    Column {
        Text("Eğitmenin Notu", style = SkyFitTypography.bodyMediumSemibold)
        SkyFitTextInputComponent("Egitmenin nottu", modifier = Modifier.heightIn(min = 80.dp))
    }

    Spacer(Modifier.height(8.dp))
    Column {
        Text("Eğitmeni", style = SkyFitTypography.bodyMediumSemibold)
        SkyFitTextInputComponent("Egitmen", modifier = Modifier.heightIn(min = 80.dp))
    }
}

@Composable
private fun ColumnScope.MobileFacilityClassEditTimingInputGroup() {
    Text("Baslangic Tarihi", style = SkyFitTypography.bodyMediumSemibold)
    Spacer(Modifier.height(8.dp))
    SkyFitTextInputComponent("21 /12 / 2024", modifier = Modifier)

    Row(Modifier.fillMaxWidth()) {
        Column {
            Text("Baslangic Saati", style = SkyFitTypography.bodyMediumSemibold)
            Spacer(Modifier.height(8.dp))
            SkyFitTextInputComponent("08:30")
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text("Bitis Saati", style = SkyFitTypography.bodyMediumSemibold)
            Spacer(Modifier.height(8.dp))
            SkyFitTextInputComponent("08:40")
        }
    }

    Spacer(Modifier.height(8.dp))
    Column {
        Text("Ders Tekrari", style = SkyFitTypography.bodyMediumSemibold)
        SkyFitTextInputComponent("Hergun")
    }
}

@Composable
private fun MobileFacilityClassEditCapacityInputGroup() {
    Column {
        Text("Kontenjan", style = SkyFitTypography.bodyMediumSemibold)
        SkyFitTextInputComponent("5")
    }
}

@Composable
private fun MobileFacilityClassEditAppointmentInputGroup() {
    Column {
        var mandatoryAppointment by remember { mutableStateOf(false) }

        Text("Zorunlu Randevu Alimi", style = SkyFitTypography.bodyMediumSemibold)
        Row {
            Row {
                RadioButton(selected = mandatoryAppointment, onClick = { mandatoryAppointment = !mandatoryAppointment })
                Text("Evet", style = SkyFitTypography.bodyMediumRegular)
            }
            Row {
                RadioButton(selected = !mandatoryAppointment, onClick = { mandatoryAppointment = !mandatoryAppointment })
                Text("Hayir", style = SkyFitTypography.bodyMediumRegular)
            }
        }
    }
}

@Composable
private fun MobileFacilityClassEditPaymentInputGroup() {
    Column {
        var mandatoryPayment by remember { mutableStateOf(false) }

        Text("Ucret", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.height(16.dp))
        Row {
            SkyFitCheckBoxComponent(label = "Uygula", checked = mandatoryPayment, onCheckedChange = { mandatoryPayment = it })
            Spacer(Modifier.width(24.dp))
            SkyFitTextInputComponent("0.00")
        }
    }
}

@Composable
private fun MobileFacilityClassEditSelectUserGroupComponent() {
    var selectedOption by remember { mutableStateOf("Herkes") } // Default selection

    Column {
        Text("Kimler Katılabilir?", style = SkyFitTypography.bodyMediumSemibold)

        Row {
            listOf("Herkes", "Üyeler", "Takipçiler").forEach { option ->
                Row(
                    modifier = Modifier
                        .clickable { selectedOption = option }
                        .padding(end = 16.dp), // Spacing between options
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(option, style = SkyFitTypography.bodyMediumRegular)
                }
            }
        }
    }
}


@Composable
private fun MobileFacilityClassEditScreenActionComponent(enabled: Boolean, onClick: () -> Unit) {
    Box(Modifier.padding(32.dp).background(SkyFitColor.background.default)) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(),
            text = "Kaydet",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
            isEnabled = enabled
        )
    }
}

@Composable
fun MobileFacilityClassEditScreenCancelDialog(
    showDialog: Boolean,
    onClickExit: () -> Unit,
    onClickDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClickDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logo_skyfit),
                            contentDescription = "Close",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.clickable(onClick = onClickDismiss)
                        )
                    }

                    // Alert Message
                    Text(
                        text = "Geri dönerseniz değişikler kaydedilmeyecek",
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SkyFitButtonComponent(
                            text = "Tamam",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickExit,
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )

                        SkyFitButtonComponent(
                            text = "Hayır, şimdi değil",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickDismiss,
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )
                    }
                }
            }
        }
    }
}
