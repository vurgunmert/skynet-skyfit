package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardItem
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardItemComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityClassEditCompletedScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(60.dp))
            MobileFacilityClassEditCompletedComponent(
                onClickProfile = {
                    navigator.jumpAndTakeover(
                        SkyFitNavigationRoute.FacilityClasses,
                        SkyFitNavigationRoute.DashboardProfile
                    )
                },
                onClickDashboard = {}
            )
        }
    }
}

@Composable
private fun MobileFacilityClassEditCompletedComponent(
    onClickProfile: () -> Unit,
    onClickDashboard: () -> Unit
) {
    val appointmentCardItem = AppointmentCardItem(
        iconUrl = "TODO()",
        title = "Shoulders and Abs",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "Group Fitness",
        location = "@ironstudio",
        trainer = "Micheal Blake",
        capacity = "2/5",
        cost = "100",
        note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
        isFull = null
    )

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(104.dp)
        )
        Spacer(Modifier.height(24.dp))

        Text(
            "Ders Oluşturuldu",
            style = SkyFitTypography.heading4,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        AppointmentCardItemComponent(appointmentCardItem, Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Profil'e Don",
            onClick = onClickProfile,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Ana Sayfa",
            onClick = onClickDashboard,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
    }
}