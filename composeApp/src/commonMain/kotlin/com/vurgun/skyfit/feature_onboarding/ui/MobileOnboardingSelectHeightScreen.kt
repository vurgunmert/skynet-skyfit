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
import com.vurgun.skyfit.core.domain.models.HeightUnitType
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
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

            Box {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HeightPicker(
                        selectedHeight = selectedHeight,
                        onHeightSelected = viewModel::updateHeight
                    )
                    Spacer(Modifier.width(16.dp))
                    HeightUnitPicker(
                        selectedHeightUnit = selectedHeightUnit,
                        onHeightUnitSelected = viewModel::updateHeightUnit
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
            OnboardingActionGroupComponent { navigator.jumpAndStay(MobileNavRoute.OnboardingBodyTypeSelection) }

            Spacer(Modifier.height(20.dp))
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
        modifier = Modifier.width(36.dp)
    )
}

@Composable
private fun HeightUnitPicker(
    selectedHeightUnit: HeightUnitType,
    onHeightUnitSelected: (HeightUnitType) -> Unit
) {
    val units = HeightUnitType.getAllUnits()

    SkyFitWheelPickerComponent(
        items = units,
        selectedItem = selectedHeightUnit,
        onItemSelected = onHeightUnitSelected,
        itemText = { it.label },
        visibleItemCount = 3,
        modifier = Modifier.width(36.dp)
    )
}