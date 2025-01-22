package com.vurgun.skyfit.presentation.mobile.features.explore

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
fun MobileExploreFacilitiesScreen(rootNavigator: Navigator) {

    val showSearch: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileExploreFacilitiesScreenToolbarComponent()
                if (showSearch) {
                    MobileExploreFacilitiesScreenSearchComponent()
                }
                MobileExploreFacilitiesScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreFacilitiesGridComponent()
        }
    }
}

@Composable
private fun MobileExploreFacilitiesScreenToolbarComponent() {
    TodoBox("MobileExploreFacilitiesScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreFacilitiesScreenSearchComponent() {
    TodoBox("MobileExploreFacilitiesScreenSearchComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreFacilitiesScreenTabsComponent() {
    TodoBox("MobileExploreFacilitiesScreenTabsComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreFacilitiesGridComponent() {
    TodoBox("MobileExploreFacilitiesComponent", Modifier.size(398.dp, 572.dp))
}