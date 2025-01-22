package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingActionGroupComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingCharacterSelectorComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingStepProgressComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingTitleGroupComponent
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileOnboardingCharacterSelectionScreen(navigator: Navigator) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent()
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent()
            Spacer(Modifier.height(16.dp))
            OnboardingCharacterSelectorComponent()
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent()
            Spacer(Modifier.height(48.dp))
        }
    }
}