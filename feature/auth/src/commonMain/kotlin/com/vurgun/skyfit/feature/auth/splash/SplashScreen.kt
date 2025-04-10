package com.vurgun.skyfit.feature.auth.splash

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
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun SplashScreen(
    goToMaintenance: () -> Unit,
    goToLogin: () -> Unit,
    goToDashboard: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            SplashUiState.Loading -> Unit
            SplashUiState.Maintenance -> {
                goToMaintenance()
            }

            SplashUiState.NavigateToDashboard -> {
                goToDashboard()
            }

            SplashUiState.NavigateToLogin -> {
                goToLogin()
            }
        }
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
