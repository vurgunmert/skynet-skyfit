package com.vurgun.skyfit.feature_auth.ui.mobile

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
import com.vurgun.skyfit.core.domain.models.AppState
import com.vurgun.skyfit.core.domain.models.UserState
import com.vurgun.skyfit.core.ui.viewmodel.AppStateViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileSplashScreen(navigator: Navigator) {
    val viewModel = koinInject<AppStateViewModel>()
    val appState by viewModel.appState.collectAsState()
    val userState by viewModel.userState.collectAsState()

    LaunchedEffect(appState, userState) {
        when {
            appState != AppState.NORMAL -> {
                navigator.jumpAndTakeover(NavigationRoute.Splash, NavigationRoute.Maintenance)
            }

            userState == UserState.NOT_LOGGED_IN -> {
                navigator.jumpAndTakeover(NavigationRoute.Splash, NavigationRoute.Login)
            }

            userState == UserState.READY -> {
                navigator.jumpAndTakeover(NavigationRoute.Splash, NavigationRoute.Dashboard)
            }
        }
    }

    Box(Modifier.fillMaxSize().background(SkyFitColor.background.default)) {
        Image(
            painterResource(Res.drawable.logo_skyfit),
            null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
