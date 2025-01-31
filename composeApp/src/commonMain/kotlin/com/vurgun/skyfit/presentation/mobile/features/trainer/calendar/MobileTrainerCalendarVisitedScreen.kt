package com.vurgun.skyfit.presentation.mobile.features.trainer.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.facility.calendar.MobileFacilityCalendarVisitedScreenCalendarGridComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.calendar.MobileFacilityCalendarVisitedScreenCreateActionComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.calendar.MobileFacilityCalendarVisitedScreenPrivateClassesComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitAvatarCircle
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.UserCircleAvatarItem
import com.vurgun.skyfit.presentation.shared.features.calendar.FacilityCalendarVisitedViewModel
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerCalendarVisitedScreen(navigator: Navigator) {

    val showCreateAction: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Randevu Al", onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showCreateAction) {
                MobileTrainerCalendarVisitedScreenCreateActionComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileTrainerCalendarVisitedScreenInfoComponent()
            MobileTrainerCalendarVisitedScreenCalendarGridComponent()
            MobileTrainerCalendarVisitedScreenPrivateClassesComponent()
        }
    }
}

@Composable
private fun MobileTrainerCalendarVisitedScreenInfoComponent() {
    Column(
        Modifier.padding(16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Info",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Antrenör Bilgileri",
                style = SkyFitTypography.bodyLargeSemibold
            )
        }

        Spacer(Modifier.height(16.dp))
        Row {
            SkyFitAvatarCircle(
                modifier = Modifier.size(60.dp),
                item = UserCircleAvatarItem("")
            )

            Spacer(Modifier.width(16.dp))
            Column {
                Row {
                    Text(
                        text = "Jordan Blake",
                        style = SkyFitTypography.bodyLargeMedium,
                        color = SkyFitColor.text.default
                    )
                    Text(
                        text = "@mblake",
                        style = SkyFitTypography.bodyMediumRegular,
                        color = SkyFitColor.text.secondary
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = "Location",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "@ironstudio",
                        style = SkyFitTypography.bodySmall,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun MobileTrainerCalendarVisitedScreenCalendarGridComponent() {
    MobileFacilityCalendarVisitedScreenCalendarGridComponent()
}

@Composable
private fun MobileTrainerCalendarVisitedScreenPrivateClassesComponent() {
    val viewModel = FacilityCalendarVisitedViewModel()
    MobileFacilityCalendarVisitedScreenPrivateClassesComponent(viewModel.items)
}

@Composable
private fun MobileTrainerCalendarVisitedScreenCreateActionComponent() {
    MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick = {})
}
