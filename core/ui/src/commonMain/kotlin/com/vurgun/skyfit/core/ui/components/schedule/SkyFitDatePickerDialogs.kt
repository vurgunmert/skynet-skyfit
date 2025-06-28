package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.ui.components.schedule.monthly.CalendarSingleDateSelector
import com.vurgun.skyfit.core.ui.components.button.PrimaryDialogButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryDialogButton
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.calendar_select_date
import fiwe.core.ui.generated.resources.cancel_action
import fiwe.core.ui.generated.resources.confirm_action

@Composable
fun DatePickerDialog(
    selectedDate: LocalDate = LocalDate.now(),
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    var mutableSelectedDate by remember { mutableStateOf(selectedDate) }

    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(SkyFitColor.background.default, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
                ) {
                    BodyMediumRegularText(stringResource(Res.string.calendar_select_date))

                    CalendarSingleDateSelector { mutableSelectedDate = it }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                    ) {
                        SecondaryDialogButton(
                            text = stringResource(Res.string.cancel_action),
                            onClick = onDismiss
                        )
                        PrimaryDialogButton(
                            text = stringResource(Res.string.confirm_action),
                            onClick = { onConfirm(mutableSelectedDate) }
                        )
                    }
                }
            }
        }
    }
}