package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserActivityCalendarSearchScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserActivityCalendarSearchScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserActivityCalendarSearchScreenSearchComponent()
            MobileUserActivityCalendarSearchScreenTabsComponent()
            MobileUserActivityCalendarSearchFeaturedExercisesComponent()
            MobileUserActivityCalendarSearchPopularExercisesComponent()
            MobileUserActivityCalendarSearchResultsComponent()
        }
    }
}


@Composable
private fun MobileUserActivityCalendarSearchScreenToolbarComponent() {
    TodoBox("MobileUserActivityCalendarSearchScreenToolbarComponent", Modifier.size(430.dp, 48.dp))
}

@Composable
private fun MobileUserActivityCalendarSearchScreenSearchComponent() {
    TodoBox("MobileUserActivityCalendarSearchScreenSearchComponent", Modifier.size(430.dp, 76.dp))
}

@Composable
private fun MobileUserActivityCalendarSearchScreenTabsComponent() {
    TodoBox("MobileUserActivityCalendarSearchScreenTabsComponent", Modifier.size(430.dp, 64.dp))
}

@Composable
private fun MobileUserActivityCalendarSearchFeaturedExercisesComponent() {
    TodoBox("MobileUserActivityCalendarSearchFeaturedExercisesComponent", Modifier.size(430.dp, 203.dp))
}

@Composable
private fun MobileUserActivityCalendarSearchPopularExercisesComponent() {
    TodoBox("MobileUserActivityCalendarSearchPopularExercisesComponent", Modifier.size(430.dp, 258.dp))
}


@Composable
private fun MobileUserActivityCalendarSearchResultsComponent() {
    TodoBox("MobileUserActivityCalendarSearchResultsComponent", Modifier.size(430.dp, 660.dp))
}
