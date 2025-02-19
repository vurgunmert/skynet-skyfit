package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import cz.kudladev.DatePicker
import kotlinx.datetime.LocalDate

@Composable
fun DatePickerDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = SkyFitColor.border.default,
            shape = RoundedCornerShape(16.dp),
            text = {
                DatePicker(
                    onSelectDate = { selectedDate ->
                        onDateSelected(selectedDate)
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Kapat", color = SkyFitColor.text.default)
                }
            }
        )
    }
}


// THIS IS HOW DIALOG COLORED
//selectedDateColor = MaterialTheme.colorScheme.primary,
//disabledDateColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
//todayDateBorderColor = MaterialTheme.colorScheme.primary,
//rangeDateDateColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
//textDisabledDateColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
//textSelectedDateColor = MaterialTheme.colorScheme.onPrimary,
//textTodayDateColor = MaterialTheme.colorScheme.primary,
//textCurrentMonthDateColor = MaterialTheme.colorScheme.onSurface,
//textOtherColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),