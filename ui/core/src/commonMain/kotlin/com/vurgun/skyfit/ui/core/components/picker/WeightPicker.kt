package com.vurgun.skyfit.ui.core.components.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import com.vurgun.skyfit.ui.core.components.button.PrimaryDialogButton
import com.vurgun.skyfit.ui.core.components.button.SecondaryDialogButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitWheelPickerComponent
import com.vurgun.skyfit.ui.core.components.text.BodyMediumRegularText
import com.vurgun.skyfit.ui.core.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.cancel_action
import skyfit.ui.core.generated.resources.confirm_action

@Composable
fun WeightAndUnitPickerDialog(
    weight: Int,
    weightUnit: WeightUnitType,
    onConfirm: (Int, WeightUnitType) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedWeight by remember { mutableStateOf(weight) }
    var selectedWeightUnit by remember { mutableStateOf(weightUnit) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyMediumSemiboldText("Kilo Seçimi")
                Spacer(Modifier.height(12.dp))
                BodyMediumRegularText("Lütfen Kilonuzu ve ölçü birimini seçin.")
                Spacer(Modifier.height(12.dp))

                WeightAndUnitPicker(
                    selectedWeight = selectedWeight,
                    selectedWeightUnit = selectedWeightUnit,
                    onWeightSelected = { selectedWeight = it },
                    onWeightUnitSelected = { selectedWeightUnit = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SecondaryDialogButton(
                        text = stringResource(Res.string.cancel_action),
                        onClick = onDismiss
                    )

                    Spacer(Modifier.width(16.dp))

                    PrimaryDialogButton(
                        text = stringResource(Res.string.confirm_action),
                        onClick = {
                            onConfirm(selectedWeight, selectedWeightUnit)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WeightAndUnitPicker(
    selectedWeight: Int,
    selectedWeightUnit: WeightUnitType,
    onWeightSelected: (Int) -> Unit,
    onWeightUnitSelected: (WeightUnitType) -> Unit
) {
    Box {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            WeightPicker(
                selectedWeight = selectedWeight,
                onWeightSelected = onWeightSelected
            )
            Spacer(Modifier.width(16.dp))
            WeightUnitPicker(
                selectedWeightUnit = selectedWeightUnit,
                onWeightUnitSelected = onWeightUnitSelected
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