package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.auth.AuthActionGroupComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthLoginInputGroupComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthLogoComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthTitleGroupComponent
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileLoginScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            AuthLogoComponent()
            Spacer(Modifier.height(48.dp))
            AuthTitleGroupComponent()
            Spacer(Modifier.height(48.dp))
            AuthLoginInputGroupComponent()
            Spacer(Modifier.height(48.dp))
            AuthActionGroupComponent()
            Spacer(Modifier.height(48.dp))
        }
    }
}
