package com.vurgun.skyfit.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.BasicActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.BookedActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.PaidActivityCalendarEventItem

@Preview(name = "Basic - Disabled")
@Composable
private fun Preview_BasicActivityCalendar_Disabled() {
    PreviewBox {
        BasicActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 1,
            timePeriod = "08:00 - 09:00",
            enabled = false
        )
    }
}

@Preview(name = "Basic - Enabled")
@Composable
private fun Preview_BasicActivityCalendar_Enabled() {
    PreviewBox {
        BasicActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 2,
            timePeriod = "08:00 - 09:00",
            enabled = true
        )
    }
}

@Preview(name = "Booked - Disabled")
@Composable
private fun Preview_BookedActivityCalendar_Disabled() {
    PreviewBox {
        BookedActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId =3,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            enabled = false
        )
    }
}

@Preview(name = "Booked - Enabled")
@Composable
private fun Preview_BookedActivityCalendar_Enabled() {
    PreviewBox {
        BookedActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 4,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            enabled = true
        )
    }
}

@Preview(name = "Available")
@Composable
private fun Preview_AvailableActivityCalendar() {
    PreviewBox {
        AvailableActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 3,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            capacity = "3/5",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
        )
    }
}

@Preview(name = "Available - Full")
@Composable
private fun Preview_AvailableActivityCalendar_Full() {
    PreviewBox {
        AvailableActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 3,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            capacity = "5/5",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            isFull = true
        )
    }
}

@Preview(name = "Available - Full & Notify Me")
@Composable
private fun Preview_AvailableActivityCalendar_FullNotify() {
    PreviewBox {
        AvailableActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 3,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            capacity = "5/5",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            isFull = true,
            isNotifyMeEnabled = true
        )
    }
}


@Preview(name = "Paid")
@Composable
private fun Preview_PaidActivityCalendar() {
    PreviewBox {
        PaidActivityCalendarEventItem(
            title = "Shoulders and Abs",
            iconId = 3,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            capacity = "3/5",
            cost = "120",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
        )
    }
}
