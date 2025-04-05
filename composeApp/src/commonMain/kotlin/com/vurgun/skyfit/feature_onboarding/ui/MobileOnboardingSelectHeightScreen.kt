package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.designsystem.components.picker.HeightAndUnitPicker
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_height_message
import skyfit.composeapp.generated.resources.onboarding_height_title

@Composable
fun MobileOnboardingHeightSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val selectedHeight = viewModel.uiState.collectAsState().value.height ?: 170
    val selectedHeightUnit = viewModel.uiState.collectAsState().value.heightUnit

    SkyFitScaffold {
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
            OnboardingActionGroupComponent { navigator.jumpAndStay(MobileNavRoute.OnboardingBodyTypeSelection) }

            Spacer(Modifier.height(20.dp))
        }
    }
}

