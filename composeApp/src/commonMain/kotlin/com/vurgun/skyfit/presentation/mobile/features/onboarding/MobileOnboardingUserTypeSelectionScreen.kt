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
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingLogoComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingStepProgressComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingTitleGroupComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingUserTypeSelectorComponent
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileOnboardingUserTypeSelectionScreen(navigator: Navigator) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent()
            Spacer(Modifier.height(47.dp))
            OnboardingLogoComponent()
            Spacer(Modifier.height(56.dp))
            OnboardingTitleGroupComponent()
            Spacer(Modifier.height(64.dp))
            OnboardingUserTypeSelectorComponent()
        }
    }
}