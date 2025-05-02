package com.vurgun.skyfit.feature.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.vurgun.skyfit.feature.splash.screenmodel.SplashEffect
import com.vurgun.skyfit.feature.splash.screenmodel.SplashViewModel
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.error_generic_message
import skyfit.core.ui.generated.resources.logo_skyfit

internal class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SplashViewModel>()
        var errorMessage by remember { mutableStateOf<String?>(null) }

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                SplashEffect.NavigateToDashboard -> {
                    appNavigator.replaceAll(SharedScreen.Dashboard)
                }

                SplashEffect.NavigateToAuth -> {
                    appNavigator.replace(SharedScreen.Authorization)
                }

                SplashEffect.NavigateToMaintenance -> {
                    appNavigator.replace(SharedScreen.Maintenance)
                }

                is SplashEffect.ShowError -> {
                    errorMessage = effect.message ?: getString(Res.string.error_generic_message)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        if (errorMessage.isNullOrEmpty()) {
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
        } else {
            ErrorScreen(errorMessage) { appNavigator.pop() }
        }
    }
}
