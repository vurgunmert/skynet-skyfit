package com.vurgun.skyfit.feature.access.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

internal class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SplashViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                SplashEffect.NavigateToDashboard -> {
                    appNavigator.replaceAll(SharedScreen.Main)
                }

                SplashEffect.NavigateToAuth -> {
                    appNavigator.replace(SharedScreen.Authorization)
                }

                SplashEffect.NavigateToMaintenance -> {
                    appNavigator.replace(SharedScreen.Maintenance)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            is SplashUiState.Error -> {
                val message = (uiState as SplashUiState.Error).message
                ErrorScreen(message = message) { appNavigator.pop() }
            }

            SplashUiState.Loading -> {
                SplashComponent.Content()
            }
        }
    }
}

private object SplashComponent {

    @Composable
    fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SkyFitColor.background.default),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_app_logo),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MobileSplashScreenPreview() {
    SplashComponent.Content()
}