package com.vurgun.skyfit.presentation.shared.features.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FacilityCalendarVisitedViewModel : ViewModel() {
    private val _calendarClasses = MutableStateFlow(fakeCalendarClasses)
    val calendarClasses: StateFlow<List<SkyFitClassCalendarCardItem>> get() = _calendarClasses

    fun toggleSelection(selectedItem: SkyFitClassCalendarCardItem) {
        _calendarClasses.update { currentList ->
            currentList.map { item ->
                when {
                    item == selectedItem -> item.copy(selected = !item.selected) // Toggle selection
                    else -> item.copy(selected = false) // Deselect all others
                }
            }
        }
    }
}



val fakeCalendarClasses = listOf(
    SkyFitClassCalendarCardItem(
        title = "Morning Yoga",
        date = "2025-02-01",
        hours = "08:00 - 09:00",
        category = "Yoga",
        location = "Studio A",
        trainer = "Alice Johnson",
        capacity = "15",
        cost = "$10",
        note = "Bring your own mat",
        enabled = true,
        selected = false,
        booked = false,
        iconId = "ic_push_up"
    ),
    SkyFitClassCalendarCardItem(
        title = "HIIT Workout",
        date = "2025-02-01",
        hours = "10:00 - 10:45",
        category = "Fitness",
        location = "Gym B",
        trainer = "John Doe",
        capacity = "20",
        cost = "$15",
        note = "High-intensity training",
        enabled = true,
        selected = false,
        booked = false,
        iconId = "ic_sit_up"
    ),
    SkyFitClassCalendarCardItem(
        title = "Pilates Core Strength",
        date = "2025-02-02",
        hours = "12:00 - 13:00",
        category = "Pilates",
        location = "Studio C",
        trainer = "Emma Stone",
        capacity = "12",
        cost = "$12",
        note = "Focus on core strength",
        enabled = true,
        selected = false,
        booked = true,
        iconId = "ic_push_up"
    ),
    SkyFitClassCalendarCardItem(
        title = "Spin Class",
        date = "2025-02-03",
        hours = "07:00 - 08:00",
        category = "Cycling",
        location = "Spin Room",
        trainer = "Michael Lee",
        capacity = "18",
        cost = "$10",
        note = "High-energy indoor cycling",
        enabled = true,
        selected = false,
        booked = false
    ),
    SkyFitClassCalendarCardItem(
        title = "Boxing Basics",
        date = "2025-02-03",
        hours = "18:00 - 19:00",
        category = "Boxing",
        location = "Boxing Arena",
        trainer = "Mike Tyson",
        capacity = "10",
        cost = "$20",
        note = "Learn boxing fundamentals",
        enabled = true,
        selected = false,
        booked = false
    ),
    SkyFitClassCalendarCardItem(
        title = "Zumba Dance Party",
        date = "2025-02-04",
        hours = "17:00 - 18:00",
        category = "Dance",
        location = "Studio D",
        trainer = "Sophia Martinez",
        capacity = "25",
        cost = "$8",
        note = "Fun dance-based workout",
        enabled = true,
        selected = false,
        booked = true
    ),
    SkyFitClassCalendarCardItem(
        title = "Strength Training",
        date = "2025-02-05",
        hours = "16:00 - 17:00",
        category = "Weightlifting",
        location = "Gym A",
        trainer = "David Williams",
        capacity = "15",
        cost = "$18",
        note = "Full-body strength training",
        enabled = true,
        selected = false,
        booked = false
    )
)