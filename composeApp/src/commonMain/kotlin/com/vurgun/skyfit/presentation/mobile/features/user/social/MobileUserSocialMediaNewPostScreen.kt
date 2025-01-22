package com.vurgun.skyfit.presentation.mobile.features.user.social

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
fun MobileUserSocialMediaNewPostScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSocialMediaNewPostScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSocialMediaNewPostScreenInputComponent()
        }
    }
}

@Composable
private fun MobileUserSocialMediaNewPostScreenToolbarComponent() {
    TodoBox("MobileUserSocialMediaNewPostScreenUserProfilesComponent", Modifier.size(430.dp, 52.dp))
}

@Composable
private fun MobileUserSocialMediaNewPostScreenInputComponent() {
    TodoBox("MobileUserSocialMediaNewPostScreenInputComponent", Modifier.size(398.dp, 480.dp))
}
