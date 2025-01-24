package com.vurgun.skyfit.presentation.mobile.features.onboarding

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
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingActionGroupComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingStepProgressComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingTitleGroupComponent

@Composable
fun MobileOnboardingHeightSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    var selectedHeight by remember { mutableStateOf(160) }
    var selectedHeightUnit by remember { mutableStateOf("cm") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 5)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = "Boyunuz",
                subtitle = "Seçiminiz profilinizde görüntülenecek"
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
                onClickContinue = onNext,
                onClickSkip = onSkip
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
    maxHeight: Int = 220
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