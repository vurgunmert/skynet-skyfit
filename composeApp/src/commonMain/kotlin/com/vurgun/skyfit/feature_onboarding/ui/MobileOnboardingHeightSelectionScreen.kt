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
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_select_height_message
import skyfit.composeapp.generated.resources.onboarding_select_height_title

@Composable
fun MobileOnboardingHeightSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val cachedHeight by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.height
                is TrainerOnboardingViewModel -> viewModel.state.value.height
                else -> 170
            }
        }
    }

    val cachedHeightUnit by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.heightUnit
                is TrainerOnboardingViewModel -> viewModel.state.value.heightUnit
                else -> "cm"
            }
        }
    }

    var selectedHeight by remember { mutableStateOf(cachedHeight ?: 170) }
    var selectedHeightUnit by remember { mutableStateOf(cachedHeightUnit ?: "cm") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 5)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_height_title),
                subtitle = stringResource(Res.string.onboarding_select_height_message)
            )
            Spacer(Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HeightPicker(
                    selectedHeight = selectedHeight,
                    onHeightSelected = { selectedHeight = it })

                HeightUnitPicker(
                    selectedHeightUnit = selectedHeightUnit,
                    onHeightUnitSelected = { selectedHeightUnit = it }
                )
            }
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> {
                            viewModel.updateHeight(selectedHeight)
                            viewModel.updateHeightUnit(selectedHeightUnit)
                        }

                        is TrainerOnboardingViewModel -> {
                            viewModel.updateHeight(selectedHeight)
                            viewModel.updateHeightUnit(selectedHeightUnit)
                        }
                    }
                    navigator.jumpAndStay(NavigationRoute.OnboardingBodyTypeSelection)
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingHeightSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingHeightSelection,
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
private fun HeightPicker(
    selectedHeight: Int,
    onHeightSelected: (Int) -> Unit,
    minHeight: Int = 120,
    maxHeight: Int = 230
) {
    val weights = (minHeight..maxHeight).toList()

    SkyFitWheelPickerComponent(
        items = weights,
        selectedItem = selectedHeight,
        onItemSelected = onHeightSelected,
        itemText = { "$it" },
        visibleItemCount = 3,
        modifier = Modifier.width(112.dp)
    )
}

@Composable
private fun HeightUnitPicker(
    selectedHeightUnit: String = "cm",
    onHeightUnitSelected: (String) -> Unit
) {
    val weightUnits = listOf("ft", "cm")

    SkyFitWheelPickerComponent(
        items = weightUnits,
        selectedItem = selectedHeightUnit,
        onItemSelected = onHeightUnitSelected,
        itemText = { it },
        visibleItemCount = 3,
        modifier = Modifier.width(64.dp)
    )
}