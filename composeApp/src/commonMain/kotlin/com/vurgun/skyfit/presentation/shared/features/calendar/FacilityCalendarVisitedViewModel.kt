package com.vurgun.skyfit.presentation.shared.features.calendar

import androidx.lifecycle.ViewModel


class FacilityCalendarVisitedViewModel: ViewModel() {

    val items = listOf(
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
            booked = false
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
            booked = false
        )
    )
}