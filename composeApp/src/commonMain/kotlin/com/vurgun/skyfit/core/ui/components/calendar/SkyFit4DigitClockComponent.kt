package com.vurgun.skyfit.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun SkyFitFourDigitClockComponent(
    hour: Int,
    minute: Int,
    clickable: Boolean,
    onTimeChange: (Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 88.dp)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                // Hour Section
                TimePickerSection(
                    value = hour,
                    clickable = clickable,
                    onValueChange = { newHour -> onTimeChange(newHour.coerceIn(0, 23), minute) }
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "saat",
                    style = SkyFitTypography.bodySmall,
                    color = SkyFitColor.text.secondary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.width(2.dp))
            Text(
                text = ":",
                style = SkyFitTypography.heading4
            )
            Spacer(Modifier.width(2.dp))

            Column(verticalArrangement = Arrangement.Center) {
                // Minute Section
                TimePickerSection(
                    value = minute,
                    clickable = clickable,
                    onValueChange = { newMinute -> onTimeChange(hour, newMinute.coerceIn(0, 59)) }
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "dakika",
                    style = SkyFitTypography.bodySmall,
                    color = SkyFitColor.text.secondary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }


//        // Time Blocks (Hours and Minutes)
//        Row(
//            modifier = Modifier
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Hour Section
//            TimePickerSection(
//                value = hour,
//                onValueChange = { newHour -> onTimeChange(newHour.coerceIn(0, 23), minute) }
//            )
//
//            Spacer(Modifier.width(4.dp))
//
//            // Separator
//            Text(
//                text = ":",
//                style = SkyFitTypography.heading4
//            )
//
//            Spacer(Modifier.width(4.dp))
//
//            // Minute Section
//            TimePickerSection(
//                value = minute,
//                onValueChange = { newMinute -> onTimeChange(hour, newMinute.coerceIn(0, 59)) }
//            )
//        }
//
//        // Labels (saat and dakika)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "saat",
//                style = SkyFitTypography.bodySmall,
//                color = SkyFitColor.text.secondary,
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.End
//            )
//            Text(
//                text = "dakika",
//                style = SkyFitTypography.bodySmall,
//                color = SkyFitColor.text.secondary,
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Start
//            )
//        }
    }
}

@Composable
fun TimePickerSection(
    value: Int,
    clickable: Boolean = false,
    onValueChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeBlock(
            value = value / 10, // First digit
            clickable = clickable,
            onClick = { newDigit ->
                val updatedValue = ((newDigit % 10) * 10) + (value % 10)
                onValueChange(updatedValue)
            }
        )
        TimeBlock(
            value = value % 10, // Second digit
            clickable = clickable,
            onClick = { newDigit ->
                val updatedValue = ((value / 10) * 10) + (newDigit % 10)
                onValueChange(updatedValue)
            }
        )
    }
}


@Composable
private fun TimeBlock(value: Int, clickable: Boolean, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(20.dp, 32.dp)
            .background(SkyFitColor.specialty.buttonBgHover, RoundedCornerShape(4.dp))
            .clickable(enabled = clickable) { onClick((value + 1) % 10) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString().padStart(1, '0'),
            style = SkyFitTypography.heading4,
            color = SkyFitColor.text.default,
        )
    }
}