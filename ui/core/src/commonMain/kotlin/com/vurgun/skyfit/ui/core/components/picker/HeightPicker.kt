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
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
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
fun HeightAndUnitPickerDialog(
    height: Int,
    heightUnit: HeightUnitType,
    onConfirm: (Int, HeightUnitType) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedHeight by remember { mutableStateOf(height) }
    var selectedHeightUnit by remember { mutableStateOf(heightUnit) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyMediumSemiboldText("Boy Seçimi")
                Spacer(Modifier.height(12.dp))
                BodyMediumRegularText("Lütfen boyunuzu ve ölçü birimini seçin.")
                Spacer(Modifier.height(12.dp))

                HeightAndUnitPicker(
                    selectedHeight = selectedHeight,
                    selectedHeightUnit = selectedHeightUnit,
                    onHeightSelected = { selectedHeight = it },
                    onHeightUnitSelected = { selectedHeightUnit = it }
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
                            onConfirm(selectedHeight, selectedHeightUnit)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HeightAndUnitPicker(
    selectedHeight: Int,
    selectedHeightUnit: HeightUnitType,
    onHeightSelected: (Int) -> Unit,
    onHeightUnitSelected: (HeightUnitType) -> Unit
) {
    Box {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HeightPicker(
                selectedHeight = selectedHeight,
                onHeightSelected = onHeightSelected
            )
            Spacer(Modifier.width(16.dp))
            HeightUnitPicker(
                selectedHeightUnit = selectedHeightUnit,
                onHeightUnitSelected = onHeightUnitSelected
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
fun HeightPicker(
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
fun HeightUnitPicker(
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