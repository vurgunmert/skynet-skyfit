package com.vurgun.skyfit.feature.onboarding.screen

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
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit
import skyfit.ui.core.generated.resources.onboarding_get_started_action
import skyfit.ui.core.generated.resources.onboarding_ready_message
import skyfit.ui.core.generated.resources.onboarding_ready_title

@Composable
internal fun MobileOnboardingCompletedScreen(
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