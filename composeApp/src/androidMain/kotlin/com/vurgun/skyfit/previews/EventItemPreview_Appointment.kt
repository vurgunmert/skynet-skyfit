package com.vurgun.skyfit.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vurgun.skyfit.designsystem.utils.PreviewBox
import com.vurgun.skyfit.designsystem.widget.event.ActiveAppointmentEventItem
import com.vurgun.skyfit.designsystem.widget.event.AttendanceAppointmentEventItem
import com.vurgun.skyfit.designsystem.widget.event.BasicAppointmentEventItem

@Preview(name = "Appointment - Basic")
@Composable
private fun Preview_BasicAppointmentEventItem() {
    PreviewBox {
        BasicAppointmentEventItem(
            title = "Shoulders and Abs",
            iconId = "ic_push_up",
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            onClick = {}
        )
    }
}

@Preview(name = "Appointment - Active")
@Composable
private fun Preview_ActiveAppointmentEventItem() {
    PreviewBox {
        ActiveAppointmentEventItem(
            title = "Shoulders and Abs",
            iconId = "ic_push_up",
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            onDelete = {},
            onClick = {}
        )
    }
}

@Preview(name = "Appointment - Attendance (Missing)")
@Composable
private fun Preview_AttendanceAppointmentEventItem_Missing() {
    PreviewBox {
        AttendanceAppointmentEventItem(
            title = "Shoulders and Abs",
            iconId = "ic_push_up",
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            isCompleted = false,
            onClick = {}
        )
    }
}

@Preview(name = "Appointment - Attendance (Completed)")
@Composable
private fun Preview_AttendanceAppointmentEventItem_Completed() {
    PreviewBox {
        AttendanceAppointmentEventItem(
            title = "Shoulders and Abs",
            iconId = "ic_push_up",
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            isCompleted = true,
            onClick = {}
        )
    }
}
