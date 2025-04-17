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
import com.vurgun.skyfit.ui.core.components.picker.HeightAndUnitPicker
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.onboarding_height_message
import skyfit.ui.core.generated.resources.onboarding_height_title

@Composable
internal fun MobileOnboardingHeightSelectionScreen(
    viewModel: OnboardingViewModel,
    goToBodyTypeSelection: () -> Unit
) {
    val selectedHeight = viewModel.uiState.collectAsState().value.height ?: 170
    val selectedHeightUnit = viewModel.uiState.collectAsState().value.heightUnit

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 5)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_height_title),
                subtitle = stringResource(Res.string.onboarding_height_message)
            )
            Spacer(Modifier.height(16.dp))

            HeightAndUnitPicker(
                selectedHeight = selectedHeight,
                selectedHeightUnit = selectedHeightUnit,
                onHeightSelected = viewModel::updateHeight,
                onHeightUnitSelected = viewModel::updateHeightUnit
            )

            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(onClickContinue = goToBodyTypeSelection)

            Spacer(Modifier.height(20.dp))
        }
    }
}

