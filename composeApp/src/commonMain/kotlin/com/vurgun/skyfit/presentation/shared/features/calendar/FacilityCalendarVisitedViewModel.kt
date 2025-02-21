package com.vurgun.skyfit.presentation.shared.features.calendar

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FacilityCalendarVisitedViewModel : ViewModel() {

    private val _calendarClasses = MutableStateFlow(fakeCalendarClasses)
    val calendarClasses: StateFlow<List<SkyFitClassCalendarCardItem>> get() = _calendarClasses

    private val _isAppointmentAllowed = MutableStateFlow(false)
    val isAppointmentAllowed: StateFlow<Boolean> get() = _isAppointmentAllowed

    private val _navigationEvent = MutableStateFlow<NavigationRoute?>(null)
    val navigationEvent: StateFlow<NavigationRoute?> get() = _navigationEvent

    fun toggleSelection(selectedItem: SkyFitClassCalendarCardItem) {
        _calendarClasses.update { currentList ->
            val updatedList = currentList.map { item ->
                when {
                    item == selectedItem && item.enabled && !item.booked && !isClassFull(item) -> {
                        item.copy(selected = !item.selected)
                    }
                    else -> item.copy(selected = false)
                }
            }
            _isAppointmentAllowed.value = updatedList.any { it.selected }
            updatedList
        }
    }

    fun handleClassSelection(selectedItem: SkyFitClassCalendarCardItem) {
        if (selectedItem.booked) {
            _navigationEvent.value = NavigationRoute.UserAppointmentDetail
        } else {
            toggleSelection(selectedItem)
        }
    }

    private fun isClassFull(item: SkyFitClassCalendarCardItem): Boolean {
        item.capacity ?: return false
        try {
            val (current, max) = item.capacity.split("/").map { it.trim().toIntOrNull() ?: 0 }
            return current >= max
        } catch (e: IndexOutOfBoundsException) {
            return false
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
        capacity = "2/4",
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
        capacity = "1/4",
        cost = "$15",
        note = "High-intensity training",
        enabled = true,
        selected = false,
        booked = false,
        iconId = "ic_sit_up"
    ),
    SkyFitClassCalendarCardItem(
        title = "HIIT Workout",
        date = "2025-02-01",
        hours = "10:00 - 10:45",
        category = "Fitness",
        location = "Gym B",
        trainer = "John Doe",
        capacity = "3/3",
        cost = "Free",
        note = "High-intensity training",
        enabled = false,
        selected = false,
        booked = false,
        iconId = "ic_jumping_rope"
    ),
    SkyFitClassCalendarCardItem(
        title = "Pilates Core Strength",
        date = "2025-02-02",
        hours = "12:00 - 13:00",
        category = "Pilates",
        location = "Studio C",
        trainer = "Emma Stone",
        capacity = "3/3",
        cost = "$12",
        note = "Focus on core strength",
        enabled = true,
        selected = false,
        booked = true,
        iconId = "ic_push_up"
    )
)
