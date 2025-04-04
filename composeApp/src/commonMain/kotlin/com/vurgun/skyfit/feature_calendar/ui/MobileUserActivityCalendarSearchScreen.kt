package com.vurgun.skyfit.feature_calendar.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature_appointments.ui.AppointmentCardViewData
import com.vurgun.skyfit.feature_appointments.ui.AppointmentCardItemComponent
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserActivityCalendarSearchScreen(navigator: Navigator) {
    val appointmentCardItem = AppointmentCardViewData(
        iconId = "TODO()",
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

    val activityItems = listOf(appointmentCardItem, appointmentCardItem, appointmentCardItem, appointmentCardItem, appointmentCardItem)

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileUserActivityCalendarSearchScreenToolbarComponent(
                    onClickBack = { navigator.popBackStack() },
                    onClickNew = { navigator.jumpAndStay(MobileNavRoute.UserActivityCalendarAdd) }
                )
                MobileUserActivityCalendarSearchScreenSearchComponent()
                MobileUserActivityCalendarSearchScreenFilterItemComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (activityItems.isEmpty()) {
                MobileUserActivityCalendarSearchFeaturedExercisesComponent()
                MobileUserActivityCalendarSearchPopularExercisesComponent()
            } else {
                MobileUserActivityCalendarSearchResultsComponent(activityItems)
            }
        }
    }
}


@Composable
private fun MobileUserActivityCalendarSearchScreenToolbarComponent(
    onClickBack: () -> Unit,
    onClickNew: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Back",
                tint = SkyFitColor.text.default
            )
        }

        Spacer(Modifier.weight(1f))

        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Yeni",
            onClick = onClickNew,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenSearchComponent() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitSearchTextInputComponent(hint = "Antrenman, antrenör ya da spor salonu ara")
    }
}

data class FilterItem(
    val title: String,
    val selected: Boolean = false
)

@Composable
private fun MobileUserActivityCalendarSearchScreenFilterItemComponent() {
    var activityFilters by remember {
        mutableStateOf(
            listOf(
                FilterItem(title = "Tumu", selected = true),
                FilterItem(title = "Kosma", selected = true),
                FilterItem(title = "Ip atlama", selected = false),
                FilterItem(title = "Pilates", selected = false),
                FilterItem(title = "Yoga", selected = false)
            )
        )
    }

    LazyRow {
        items(activityFilters) { activityFilter ->
            MobileUserActivityCalendarSearchScreenFilterComponent(
                activityFilter,
                onClick = {
                    activityFilters = activityFilters.map { filter ->
                        filter.copy(selected = filter.title == activityFilter.title)
                    }
                }
            )
        }
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenFilterComponent(item: FilterItem, onClick: () -> Unit) {
    if (item.selected) {
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = item.title,
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    } else {
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = item.title,
            onClick = onClick,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileUserActivityCalendarSearchFeaturedExercisesComponent() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "En yeni",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(16.dp))
        MobileSettingsMenuItemComponent("Yuruyus")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Bisiklet")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Yoga")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MobileUserActivityCalendarSearchPopularExercisesComponent() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "En popular",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(16.dp))
        MobileSettingsMenuItemComponent("Pilates")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Kosma")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Yuzme")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        Spacer(Modifier.height(16.dp))
        MobileSettingsMenuItemComponent("Tirmanma")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        Spacer(Modifier.height(16.dp))
    }
}


@Composable
private fun MobileUserActivityCalendarSearchResultsComponent(activityItems: List<AppointmentCardViewData>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(activityItems) {
            AppointmentCardItemComponent(it, Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun MobileUserCalendarActivityMenuItemComponent(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = SkyFitTypography.bodyMediumMedium
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
    }
}
