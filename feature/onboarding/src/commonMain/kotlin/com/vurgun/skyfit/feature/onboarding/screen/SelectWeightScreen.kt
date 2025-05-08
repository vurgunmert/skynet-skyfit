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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.core.ui.components.picker.WeightAndUnitPicker
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.onboarding_weight_message
import skyfit.core.ui.generated.resources.onboarding_weight_title

internal class SelectWeightScreen(private val viewModel: OnboardingViewModel): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingWeightSelectionScreen(
            viewModel = viewModel,
            goToHeightSelection = { navigator.push(SelectHeightScreen(viewModel)) }
        )
    }

}

@Composable
internal fun MobileOnboardingWeightSelectionScreen(
    viewModel: OnboardingViewModel,
    goToHeightSelection: () -> Unit
) {
    val selectedWeight = viewModel.uiState.collectAsState().value.weight ?: 70
    val selectedWeightUnit = viewModel.uiState.collectAsState().value.weightUnit

    SkyFitMobileScaffold {
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

            OnboardingActionGroupComponent(onClickContinue = {
                viewModel.updateWeight(selectedWeight)
                viewModel.updateWeightUnit(selectedWeightUnit)
                goToHeightSelection()
            })

            Spacer(Modifier.height(20.dp))
        }
    }
}