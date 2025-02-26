package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.YearPicker

@Composable
fun MobileOnboardingBirthYearSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {

    var selectedYear by remember { mutableStateOf(2000) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 3)
            Spacer(Modifier.weight(1f))
            OnboardingTitleGroupComponent(
                title = "Doğum Tarihiniz",
                subtitle = "Seçiminiz profilinizde görüntülenecek"
            )
            Spacer(Modifier.height(16.dp))
            YearPicker(
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it },
                startYear = 1930,
                endYear = 2025
            )
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = onNext,
                onClickSkip = onSkip
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}