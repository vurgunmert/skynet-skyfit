package com.vurgun.skyfit.ui.core.components.event
//
//import androidx.compose.desktop.ui.tooling.preview.Preview
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.vurgun.skyfit.ui.core.components.event.EventTitleRow
//import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
//import com.vurgun.skyfit.designsystem.utils.PreviewBox
//import com.vurgun.skyfit.designsystem.utils.PreviewMobileScaffoldColumn
//
//@Preview
//@Composable
//private fun ActivityCalendarEventItems_Preview() {
//    val iconId = "ic_push_up"
//    val title = "Shoulders and Abs"
//    val date = "30/11/2024"
//    val timePeriod = "08:00 - 09:00"
//    val location = "@ironstudio"
//    val trainer = "Micheal Blake"
//    val capacity = "3/5"
//    val note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
//
//    PreviewMobileScaffoldColumn {
//        EventTitleRow("BasicActivityCalendarEventItem_disabled")
//        BasicActivityCalendarEventItem(title, iconId, timePeriod, enabled = false)
//        EventTitleRow("BasicActivityCalendarEventItem_enabled")
//        BasicActivityCalendarEventItem(title, iconId, timePeriod, enabled = true)
//        EventTitleRow("BookedActivityCalendarEventItem_disabled")
//        BookedActivityCalendarEventItem(title, iconId, date, timePeriod, location, trainer, note, enabled = false)
//        EventTitleRow("BookedActivityCalendarEventItem_enabled")
//        BookedActivityCalendarEventItem(title, iconId, date, timePeriod, location, trainer, note, enabled = true)
//        EventTitleRow("AvailableActivityCalendarEventItem")
//        AvailableActivityCalendarEventItem(title, iconId, date, timePeriod, location, trainer, capacity, note)
//        EventTitleRow("AvailableActivityCalendarEventItem_FullCapacity")
//        AvailableActivityCalendarEventItem(title, iconId, date, timePeriod, location, trainer, capacity, note, isFull = true)
//        EventTitleRow("AvailableActivityCalendarEventItem_FullCapacity_Notifying")
//        AvailableActivityCalendarEventItem(
//            title,
//            iconId,
//            date,
//            timePeriod,
//            location,
//            trainer,
//            capacity,
//            note,
//            isFull = true,
//            isNotifyMeEnabled = true
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun AppointmentEventItems_Preview() {
//    val iconId = "ic_push_up"
//    val title = "Shoulders and Abs"
//    val date = "30/11/2024"
//    val timePeriod = "08:00 - 09:00"
//    val location = "@ironstudio"
//    val trainer = "Micheal Blake"
//    val note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
//
//    PreviewMobileScaffoldColumn {
//        EventTitleRow("BasicAppointmentEventItem")
//        BasicAppointmentEventItem(title, iconId, date, timePeriod, location, trainer, note, onClick = {})
//        EventTitleRow("ActiveAppointmentEventItem")
//        ActiveAppointmentEventItem(title, iconId, date, timePeriod, location, trainer, note, onDelete = {}, onClick = {})
//        EventTitleRow("AttendanceAppointmentEventItem_missing")
//        AttendanceAppointmentEventItem(title, iconId, date, timePeriod, location, trainer, note, isCompleted = false, onClick = {})
//        EventTitleRow("AttendanceAppointmentEventItem_completed")
//        AttendanceAppointmentEventItem(title, iconId, date, timePeriod, location, trainer, note, isCompleted = true, onClick = {})
//    }
//}
//
//@Preview
//@Composable
//private fun LessonEventItems_Preview() {
//    val iconId = "ic_push_up"
//    val title = "Shoulders and Abs"
//    val date = "30/11/2024"
//    val timePeriod = "08:00 - 09:00"
//    val category = "Group Fitness"
//    val location = "@ironstudio"
//    val trainer = "Micheal Blake"
//    val capacity = "2/5"
//    val cost = "100"
//    val note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
//
//    SkyFitMobileScaffold {
//        Column(
//            Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            EventTitleRow("BasicLessonEventItem_Preview")
//            BasicLessonEventItem(
//                title = title,
//                iconId = iconId,
//                date = date,
//                timePeriod = timePeriod,
//                location = location,
//                trainer = trainer,
//                note = note
//            )
//
//            EventTitleRow("DetailedLessonEventItem_Preview")
//            DetailedLessonEventItem(
//                title = title,
//                iconId = iconId,
//                date = date,
//                timePeriod = timePeriod,
//                category = category,
//                location = location,
//                trainer = trainer,
//                capacity = capacity,
//                cost = cost
//            )
//
//            EventTitleRow("EditableLessonEventItem_Preview")
//            EditableLessonEventItem(
//                title = title,
//                iconId = iconId,
//                date = date,
//                timePeriod = timePeriod,
//                location = location,
//                trainer = trainer,
//                note = note,
//                isMenuOpen = false,
//                onMenuToggle = { },
//                menuContent = { }
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_BasicActivityCalendar_Disabled() {
//    PreviewBox {
//        BasicActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            timePeriod = "08:00 - 09:00",
//            enabled = false
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_BasicActivityCalendar_Enabled() {
//    PreviewBox {
//        BasicActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            timePeriod = "08:00 - 09:00",
//            enabled = true
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_BookedActivityCalendar_Disabled() {
//    PreviewBox {
//        BookedActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
//            enabled = false
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_BookedActivityCalendar_Enabled() {
//    PreviewBox {
//        BookedActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
//            enabled = true
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_AvailableActivityCalendar() {
//    PreviewBox {
//        AvailableActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            capacity = "3/5",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_AvailableActivityCalendar_Full() {
//    PreviewBox {
//        AvailableActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            capacity = "5/5",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
//            isFull = true
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun Preview_AvailableActivityCalendar_FullNotify() {
//    PreviewBox {
//        AvailableActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            capacity = "5/5",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
//            isFull = true,
//            isNotifyMeEnabled = true
//        )
//    }
//}
//
//
//@Preview
//@Composable
//private fun Preview_EventItem_Paid_ActivityCalendar() {
//    PreviewBox {
//        PaidActivityCalendarEventItem(
//            title = "Shoulders and Abs",
//            iconId = "ic_push_up",
//            date = "30/11/2024",
//            timePeriod = "08:00 - 09:00",
//            location = "@ironstudio",
//            trainer = "Micheal Blake",
//            capacity = "3/5",
//            cost = "120",
//            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
//        )
//    }
//}