package com.vurgun.skyfit.feature.access.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

internal class OnboardingCompletedScreen(
    private val viewModel: OnboardingViewModel
) : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()

        MobileOnboardingCompletedScreen(
            viewModel = viewModel,
            goToDashboard = { appNavigator.replaceAll(SharedScreen.Main) }
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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_ready_title),
                subtitle = stringResource(Res.string.onboarding_ready_message),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(48.dp))

            Image(
                painter = painterResource(character?.icon?.res ?: Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier
                    .heightIn(max = 120.dp)
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
            )

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = stringResource(Res.string.onboarding_get_started_action),
                onClick = goToDashboard,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                size = SkyButtonSize.Large
            )
        }
    }
}