package com.vurgun.skyfit.feature.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.picker.WeightAndUnitPicker
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.onboarding_weight_message
import skyfit.ui.core.generated.resources.onboarding_weight_title

@Composable
internal fun MobileOnboardingWeightSelectionScreen(
    viewModel: OnboardingViewModel,
    goToHeightSelection: () -> Unit
) {
    val selectedWeight = viewModel.uiState.collectAsState().value.weight ?: 70
    val selectedWeightUnit = viewModel.uiState.collectAsState().value.weightUnit

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 4)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_weight_title),
                subtitle = stringResource(Res.string.onboarding_weight_message)
            )
            Spacer(Modifier.height(16.dp))

            WeightAndUnitPicker(
                selectedWeight = selectedWeight,
                selectedWeightUnit = selectedWeightUnit,
                onWeightSelected = viewModel::updateWeight,
                onWeightUnitSelected = viewModel::updateWeightUnit
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(onClickContinue = goToHeightSelection)

            Spacer(Modifier.height(20.dp))
        }
    }
}