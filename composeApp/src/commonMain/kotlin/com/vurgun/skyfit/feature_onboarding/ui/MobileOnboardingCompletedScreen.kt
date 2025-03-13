package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.loader.AutoLoopingCircularProgressIndicator
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit
import skyfit.composeapp.generated.resources.onboarding_lets_get_started
import skyfit.composeapp.generated.resources.onboarding_ready_message
import skyfit.composeapp.generated.resources.onboarding_ready_title

@Composable
fun MobileOnboardingCompletedScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {

    val isLoading by viewModel.isLoading.collectAsState()
    val character = viewModel.state.collectAsState().value.character

    LaunchedEffect(viewModel) {
        viewModel.submitFields()
    }

    SkyFitMobileScaffold {
        if (isLoading) {
            Box(Modifier.fillMaxSize()) {
                AutoLoopingCircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                OnboardingTitleGroupComponent(
                    title = stringResource(Res.string.onboarding_ready_title),
                    subtitle = stringResource(Res.string.onboarding_ready_message),
                    modifier = Modifier.align(Alignment.TopStart).padding(top = 110.dp)
                )

                Image(
                    painter = painterResource(character?.icon?.res ?: Res.drawable.logo_skyfit),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                )


                SkyFitButtonComponent(
                    text = stringResource(Res.string.onboarding_lets_get_started),
                    modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
                    onClick = { navigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard) },
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large,
                    state = ButtonState.Rest
                )
            }
        }
    }
}