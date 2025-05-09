package com.vurgun.skyfit.feature.access.onboarding


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
import com.vurgun.skyfit.core.ui.components.picker.HeightAndUnitPicker
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.onboarding_height_message
import skyfit.core.ui.generated.resources.onboarding_height_title

internal class SelectHeightScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingHeightSelectionScreen(
            viewModel = viewModel,
            goToBodyTypeSelection = { navigator.push(SelectBodyTypeScreen(viewModel)) }
        )
    }
}


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
            OnboardingActionGroupComponent(onClickContinue = {
                viewModel.updateHeight(selectedHeight)
                viewModel.updateHeightUnit(selectedHeightUnit)
                goToBodyTypeSelection()
            })

            Spacer(Modifier.height(20.dp))
        }
    }
}

