package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun MobileUserTrophiesScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserTrophiesToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileUserTrophiesUserInfoComponent()

            Spacer(Modifier.height(16.dp))

            MobileUserTrophiesComponent()
        }
    }
}


@Composable
private fun MobileUserTrophiesToolbarComponent() {
    TodoBox("MobileUserTrophiesToolbarComponent", Modifier.size(320.dp, 36.dp))
}

@Composable
private fun MobileUserTrophiesUserInfoComponent() {
    TodoBox("MobileUserTrophiesHeaderComponent", Modifier.size(98.dp, 112.dp))
}

@Composable
private fun MobileUserTrophiesComponent() {
    TodoBox("MobileUserTrophiesComponent", Modifier.size(320.dp, 786.dp))
}
