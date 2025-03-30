package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.WeightUnitType
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_weight_message
import skyfit.composeapp.generated.resources.onboarding_weight_title

@Composable
fun MobileOnboardingWeightSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
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

            Box {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    WeightPicker(
                        selectedWeight = selectedWeight,
                        onWeightSelected = viewModel::updateWeight
                    )
                    Spacer(Modifier.width(16.dp))
                    WeightUnitPicker(
                        selectedWeightUnit = selectedWeightUnit,
                        onWeightUnitSelected = viewModel::updateWeightUnit
                    )
                }

                Box(
                    Modifier
                        .align(Alignment.Center)
                        .width(213.dp)
                        .height(43.dp)
                        .background(color = SkyFitColor.background.fillTransparentSecondary, shape = RoundedCornerShape(size = 16.dp))
                )
            }


            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent { navigator.jumpAndStay(NavigationRoute.OnboardingHeightSelection) }

            Spacer(Modifier.height(20.dp))
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
        modifier = Modifier.width(36.dp)
    )
}

@Composable
fun WeightUnitPicker(
    selectedWeightUnit: WeightUnitType,
    onWeightUnitSelected: (WeightUnitType) -> Unit
) {
    val weightUnits = WeightUnitType.getAllUnits()
    SkyFitWheelPickerComponent(
        items = weightUnits,
        selectedItem = selectedWeightUnit,
        onItemSelected = onWeightUnitSelected,
        itemText = { it.shortLabel },
        visibleItemCount = 3,
        modifier = Modifier.width(36.dp)
    )
}