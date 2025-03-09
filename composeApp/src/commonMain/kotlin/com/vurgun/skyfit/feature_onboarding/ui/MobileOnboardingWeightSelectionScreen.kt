package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_select_weight_message
import skyfit.composeapp.generated.resources.onboarding_select_weight_title

@Composable
fun MobileOnboardingWeightSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val cachedWeight by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.weight
                is TrainerOnboardingViewModel -> viewModel.state.value.weight
                else -> 70
            }
        }
    }

    val cachedWeightUnit by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.weightUnit
                is TrainerOnboardingViewModel -> viewModel.state.value.weightUnit
                else -> "kg"
            }
        }
    }

    var selectedWeight by remember { mutableStateOf(cachedWeight ?: 70) }
    var selectedWeightUnit by remember { mutableStateOf(cachedWeightUnit ?: "kg") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 4)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_weight_title),
                subtitle = stringResource(Res.string.onboarding_select_weight_message)
            )
            Spacer(Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                WeightPicker(
                    selectedWeight = selectedWeight,
                    onWeightSelected = { selectedWeight = it })

                WeightUnitPicker(
                    selectedWeightUnit = selectedWeightUnit,
                    onWeightUnitSelected = { selectedWeightUnit = it }
                )
            }
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> {
                            viewModel.updateWeight(selectedWeight)
                            viewModel.updateWeightUnit(selectedWeightUnit)
                        }
                        is TrainerOnboardingViewModel -> {
                            viewModel.updateWeight(selectedWeight)
                            viewModel.updateWeightUnit(selectedWeightUnit)
                        }
                    }
                    navigator.jumpAndStay(NavigationRoute.OnboardingHeightSelection)
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingWeightSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingWeightSelection,
                            NavigationRoute.OnboardingTrainerDetails
                        )
                    }
                }
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
fun WeightPicker(
    selectedWeight: Int,
    onWeightSelected: (Int) -> Unit,
    minWeight: Int = 30,
    maxWeight: Int = 250
) {
    val weights = (minWeight..maxWeight).toList()

    SkyFitWheelPickerComponent(
        items = weights,
        selectedItem = selectedWeight,
        onItemSelected = onWeightSelected,
        itemText = { "$it" },
        visibleItemCount = 3,
        modifier = Modifier.width(112.dp)
    )
}

@Composable
fun WeightUnitPicker(
    selectedWeightUnit: String = "kg",
    onWeightUnitSelected: (String) -> Unit
) {
    val weightUnits = listOf("lb", "kg")

    SkyFitWheelPickerComponent(
        items = weightUnits,
        selectedItem = selectedWeightUnit,
        onItemSelected = onWeightUnitSelected,
        itemText = { it },
        visibleItemCount = 3,
        modifier = Modifier.width(64.dp)
    )
}