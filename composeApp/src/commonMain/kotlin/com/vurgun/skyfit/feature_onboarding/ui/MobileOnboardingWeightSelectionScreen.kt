package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitWheelPickerComponent

@Composable
fun MobileOnboardingWeightSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    var selectedWeight by remember { mutableStateOf(65) }
    var selectedWeightUnit by remember { mutableStateOf("kg") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 4)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = "Kilonuz",
                subtitle = "Seçiminiz profilinizde görüntülenecek"
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
                onClickContinue = onNext,
                onClickSkip = onSkip
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
fun WeightPicker(
    selectedWeight: Int,
    onWeightSelected: (Int) -> Unit,
    minWeight: Int = 40,
    maxWeight: Int = 200
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