package com.vurgun.skyfit.presentation.mobile.features.facility.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.calendar.FacilityCalendarVisitedViewModel
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitCalendarGridComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItemComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.now
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityCalendarVisitedScreen(navigator: Navigator) {

    val viewModel = FacilityCalendarVisitedViewModel()

    val showCreateAction: Boolean = true
    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Randevu Al", onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showCreateAction) {
                MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.UserAppointmentDetail)
                })
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilityCalendarVisitedScreenCalendarGridComponent()
            MobileFacilityCalendarVisitedScreenPrivateClassesComponent(viewModel.items)
        }
    }
}

@Composable
fun MobileFacilityCalendarVisitedScreenCalendarGridComponent() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    SkyFitCalendarGridComponent(
        initialSelectedDate = selectedDate,
        isSingleSelect = true,
        onDateSelected = { selectedDate = it }
    )
}

@Composable
fun MobileFacilityCalendarVisitedScreenPrivateClassesComponent(items: List<SkyFitClassCalendarCardItem>) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = "Ozel Ders Sec",
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(8.dp).height(24.dp),
                    textAlign = TextAlign.Center
                )
            }

            items.forEach { item ->
                Spacer(Modifier.height(16.dp))
                SkyFitClassCalendarCardItemComponent(
                    item = item,
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(32.dp)) {
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Randevu Olustur",
            onClick = onClick,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}