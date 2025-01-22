package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.auth.AuthActionInfoGroupComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthLogoComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthTitleGroupComponent
import com.vurgun.skyfit.presentation.shared.features.auth.AuthValidateCodeInputGroupComponent
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileForgotPasswordCodeScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            AuthLogoComponent()
            Spacer(Modifier.height(48.dp))
            AuthTitleGroupComponent()
            Spacer(Modifier.height(48.dp))
            AuthValidateCodeInputGroupComponent()
            Spacer(Modifier.weight(1f))
            AuthActionInfoGroupComponent()
            Spacer(Modifier.height(48.dp))
        }
    }
}
