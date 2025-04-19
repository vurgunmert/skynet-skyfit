package com.vurgun.skyfit.feature.profile.trainer

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.feature.calendar.components.component.calendar.monthly.CalendarRangeDateSelectorCard
import com.vurgun.skyfit.feature.profile.facility.FacilityCalendarVisitedViewModel
import com.vurgun.skyfit.feature.profile.facility.MobileFacilityCalendarVisitedScreenCreateActionComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun MobileTrainerCalendarVisitedScreen(
    goToBack: () -> Unit
) {

    val showCreateAction: Boolean = true

    val viewModel = FacilityCalendarVisitedViewModel()
    val lessonsColumnViewData by viewModel.lessonsColumnViewData.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Randevu Al", onClickBack = goToBack)
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

            CalendarRangeDateSelectorCard(
                onSelectionChanged = { start, end ->
                    // TODO: Set date
                }
            )

            MobileTrainerCalendarVisitedScreenPrivateClassesComponent(lessonsColumnViewData)
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
            SkyFitCircularImageComponent(
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
private fun MobileTrainerCalendarVisitedScreenPrivateClassesComponent(lessonsColumnViewData: LessonSessionColumnViewData?) {
//    MobileFacilityCalendarVisitedScreenPrivateClassesComponent(calendarClasses, {})
}

@Composable
private fun MobileTrainerCalendarVisitedScreenCreateActionComponent() {
    MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick = {})
}
