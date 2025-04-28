package com.vurgun.skyfit.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vurgun.skyfit.core.ui.components.event.BasicLessonEventItem
import com.vurgun.skyfit.core.ui.components.event.DetailedLessonEventItem
import com.vurgun.skyfit.core.ui.components.event.EditableLessonEventItem


@Preview(name = "Lesson - Basic")
@Composable
private fun Preview_BasicLessonEventItem() {
    PreviewBox {
        BasicLessonEventItem(
            title = "Shoulders and Abs",
            iconId = 2,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
        )
    }
}

@Preview(name = "Lesson - Detailed")
@Composable
private fun Preview_DetailedLessonEventItem() {
    PreviewBox {
        DetailedLessonEventItem(
            title = "Shoulders and Abs",
            iconId = 2,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            category = "Group Fitness",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            capacity = "2/5",
            cost = "100"
        )
    }
}

@Preview(name = "Lesson - Editable")
@Composable
private fun Preview_EditableLessonEventItem() {
    PreviewBox {
        EditableLessonEventItem(
            title = "Shoulders and Abs",
            iconId = 2,
            date = "30/11/2024",
            timePeriod = "08:00 - 09:00",
            location = "@ironstudio",
            trainer = "Micheal Blake",
            note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
            isMenuOpen = false,
            onMenuToggle = {},
            menuContent = {}
        )
    }
}
