package com.vurgun.skyfit.feature.access.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit
import skyfit.core.ui.generated.resources.onboarding_get_started_action
import skyfit.core.ui.generated.resources.onboarding_ready_message
import skyfit.core.ui.generated.resources.onboarding_ready_title

internal class OnboardingCompletedScreen(
    private val viewModel: OnboardingViewModel
) : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()

        MobileOnboardingCompletedScreen(
            viewModel = viewModel,
            goToDashboard = { appNavigator.replaceAll(SharedScreen.Dashboard) }
        )
    }
}

@Composable
private fun MobileOnboardingCompletedScreen(
    viewModel: OnboardingViewModel,
    goToDashboard: () -> Unit
) {
    val character = viewModel.uiState.collectAsState().value.character

    SkyFitMobileScaffold {
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

            PrimaryLargeButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = stringResource(Res.string.onboarding_get_started_action),
                onClick = goToDashboard,
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}