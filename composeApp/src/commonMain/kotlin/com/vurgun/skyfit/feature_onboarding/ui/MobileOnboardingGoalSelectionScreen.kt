package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableTextButton

@Composable
fun MobileOnboardingGoalSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 6)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = "Ne için buradasınız",
                subtitle = "Seçiminize göre özel antrenmanlara ulaş"
            )
            Spacer(Modifier.height(16.dp))
            MobileOnboardingGoalSelectionComponent()
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = onNext,
                onClickSkip = onSkip
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileOnboardingGoalSelectionComponent() {
    var selectedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SkyFitSelectableTextButton(
            text = "Kilo Vermek",
            selected = selectedIndex == 0,
            onSelect = { selectedIndex = 0 }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitSelectableTextButton(
            text = "Fit Olmak",
            selected = selectedIndex == 1,
            onSelect = { selectedIndex = 1 }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitSelectableTextButton(
            text = "Kas Yapmak",
            selected = selectedIndex == 2,
            onSelect = { selectedIndex = 2 }
        )
    }
}