package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.SkyFitCheckBoxComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.apply_action
import fiwe.core.ui.generated.resources.lesson_cost_label

@Composable
fun SkyFormInputCostText(
    cost: Int?,
    onChanged: (Int) -> Unit
) {
    var isPaymentMandatory by remember { mutableStateOf((cost ?: 0) > 0) }
    var inputValue by remember { mutableStateOf(cost?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
    ) {
        Text(
            text = stringResource(Res.string.lesson_cost_label),
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkyFitCheckBoxComponent(
                label = stringResource(Res.string.apply_action),
                checked = isPaymentMandatory,
                onCheckedChange = {
                    isPaymentMandatory = it
                    if (!it) inputValue = ""
                }
            )

            BasicTextField(
                value = inputValue,
                onValueChange = { newValue ->
                    val cleaned = newValue.replace(',', '.').filter { it.isDigit() || it == '.' }
                    inputValue = cleaned
                    cleaned.toIntOrNull()?.let { onChanged(it) }
                },
                enabled = isPaymentMandatory,
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = if (isPaymentMandatory) SkyFitColor.text.default else SkyFitColor.text.secondary,
                    textAlign = TextAlign.Left
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                cursorBrush = SolidColor(SkyFitColor.icon.default),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (inputValue.isEmpty()) {
                            Text(
                                "0.00",
                                color = SkyFitColor.text.secondary,
                                style = SkyFitTypography.bodyMediumRegular
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            innerTextField()
                            Text("₺", color = SkyFitColor.text.secondary)
                        }
                    }
                }
            )
        }
    }
}