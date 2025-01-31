package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItemComponent
import com.vurgun.skyfit.presentation.shared.features.profile.FacilityClassesViewModel
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityClassesScreen(navigator: Navigator) {
    val viewModel = FacilityClassesViewModel()
    val privateClasses = viewModel.privateClasses

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Dersler", onClickBack = { navigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            MobileFacilityClassesScreenActiveClassesComponent(privateClasses)
            MobileFacilityClassesScreenPassiveClassesComponent(privateClasses)
        }
    }
}

@Composable
fun MobileFacilityClassesScreenActiveClassesComponent(privateClasses: List<SkyFitClassCalendarCardItem>) {

    Box(Modifier.fillMaxWidth().padding(16.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Aktif",
                    style = SkyFitTypography.heading5
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "+ Yeni",
                    style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }

            privateClasses.forEach {
                Spacer(Modifier.height(16.dp))
                SkyFitClassCalendarCardItemComponent(
                    item = it,
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun MobileFacilityClassesScreenPassiveClassesComponent(privateClasses: List<SkyFitClassCalendarCardItem>) {

    Box(Modifier.fillMaxWidth().padding(16.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Kullanım dışı",
                style = SkyFitTypography.heading5
            )

            privateClasses.forEach {
                Spacer(Modifier.height(16.dp))
                SkyFitClassCalendarCardItemComponent(
                    item = it,
                    onClick = { }
                )
            }
        }
    }
}
