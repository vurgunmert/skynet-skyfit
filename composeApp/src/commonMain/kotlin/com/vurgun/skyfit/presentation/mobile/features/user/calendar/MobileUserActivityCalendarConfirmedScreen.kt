package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserActivityCalendarConfirmedScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(60.dp))
            MobileUserActivityCalendarAddConfirmedComponent()
        }
    }
}

@Composable
private fun MobileUserActivityCalendarAddConfirmedComponent() {
    TodoBox("MobileUserActivityCalendarAddConfirmedComponent", Modifier.size(382.dp, 525.dp))
}