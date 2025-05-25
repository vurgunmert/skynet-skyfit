package com.vurgun.skyfit.core.ui.components.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.utility.isAfter
import com.vurgun.skyfit.core.data.utility.isBefore
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.ui.components.schedule.dialog.SingleDatePickerDialog
import com.vurgun.skyfit.core.ui.components.text.TitledMediumRegularText
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun SelectStartEndDateRow(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    endDate: LocalDate,
    onStartDateSelected: (LocalDate) -> Unit = {},
    onEndDateSelected: (LocalDate) -> Unit = {}
) {
    var isStartDatePickerOpen by remember { mutableStateOf(false) }
    var isEndDatePickerOpen by remember { mutableStateOf(false) }
    var mutableStartDate by remember { mutableStateOf(startDate) }
    var mutableEndDate by remember { mutableStateOf(endDate) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isStartDatePickerOpen = true },
            title = stringResource(Res.string.lesson_start_date_label),
            hint = stringResource(Res.string.lesson_date_hint),
            value = mutableStartDate.datePickerLabelFormat(),
            rightIconRes = Res.drawable.ic_calendar_dots
        )

        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isEndDatePickerOpen = true },
            title = stringResource(Res.string.lesson_end_date_label),
            hint = stringResource(Res.string.lesson_date_hint),
            value = mutableEndDate.datePickerLabelFormat(),
            rightIconRes = Res.drawable.ic_calendar_dots
        )
    }

    if (isStartDatePickerOpen) {
        SingleDatePickerDialog(
            isOpen = isStartDatePickerOpen,
            onConfirm = { newStartDate ->
                val selectableStartDate: LocalDate = when {
                    newStartDate.isBefore(LocalDate.now()) -> LocalDate.now()
                    else -> newStartDate
                }
                mutableStartDate = selectableStartDate
                onStartDateSelected(selectableStartDate)

                if (selectableStartDate.isAfter(endDate)) {
                    mutableEndDate = selectableStartDate
                    onEndDateSelected(selectableStartDate)
                }

                isStartDatePickerOpen = false
            },
            onDismiss = { isStartDatePickerOpen = false }
        )
    }

    if (isEndDatePickerOpen) {
        SingleDatePickerDialog(
            isOpen = isEndDatePickerOpen,
            onConfirm = { newEndDate ->
                val selectableEndDate: LocalDate = when {
                    newEndDate.isBefore(startDate) -> startDate
                    else -> newEndDate
                }
                mutableEndDate = selectableEndDate
                onEndDateSelected(selectableEndDate)

                isEndDatePickerOpen = false
            },
            onDismiss = { isEndDatePickerOpen = false }
        )
    }
}

private fun LocalDate.datePickerLabelFormat(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0') // Ensures 01, 02, ...
    val month = this.monthNumber.toString().padStart(2, '0') // Ensures 01, 02, ...
    val year = this.year.toString()
    return "$day / $month / $year"
}