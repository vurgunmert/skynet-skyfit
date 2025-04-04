package com.vurgun.skyfit.feature_splash.presentation.view

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
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_splash.presentation.viewmodel.SplashUiState
import com.vurgun.skyfit.feature_splash.presentation.viewmodel.SplashViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun SplashScreen(navigator: Navigator) {
    val viewModel = koinInject<SplashViewModel>()

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            SplashUiState.Loading -> Unit
            SplashUiState.Maintenance -> {
                navigator.jumpAndTakeover(MobileNavRoute.Maintenance)
            }

            SplashUiState.NavigateToDashboard -> {
                navigator.jumpAndTakeover(MobileNavRoute.Dashboard)
            }

            SplashUiState.NavigateToLogin -> {
                navigator.jumpAndTakeover(MobileNavRoute.Login)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null
        )
    }
}
