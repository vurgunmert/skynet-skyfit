package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.presentation.shared.features.auth.SplashViewModel
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileSplashScreen(navigator: Navigator) {
    val viewModel = koinInject<SplashViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    when (uiState) {
        SplashViewModel.UIState.Idle -> Unit
        SplashViewModel.UIState.Login -> {
            navigator.jumpAndTakeover(NavigationRoute.Splash, NavigationRoute.Login)
        }

        SplashViewModel.UIState.Ready -> {
            navigator.jumpAndTakeover(NavigationRoute.Splash, NavigationRoute.Dashboard)
        }
    }

    // Composition
    Box(Modifier.fillMaxSize().background(SkyFitColor.background.default)) {
        Image(
            painterResource(Res.drawable.logo_skyfit),
            null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
