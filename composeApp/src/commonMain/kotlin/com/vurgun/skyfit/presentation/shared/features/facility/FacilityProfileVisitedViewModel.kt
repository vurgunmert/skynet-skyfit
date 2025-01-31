package com.vurgun.skyfit.presentation.shared.features.facility

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.presentation.mobile.features.explore.TrainerProfileCardItemViewData
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem

class FacilityProfileVisitedViewModel: ViewModel() {

    val privateClasses = listOf(
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


    val trainers = listOf(
        TrainerProfileCardItemViewData("url1", "Lucas Bennett", 1800, 13, 32, 4.8),
        TrainerProfileCardItemViewData("url2", "Olivia Hayes", 1500, 10, 20, 4.5),
        TrainerProfileCardItemViewData("url3", "Mason Reed", 2000, 15, 40, 4.9),
        TrainerProfileCardItemViewData("url4", "Sophia Hill", 1700, 12, 28, 4.7),
        TrainerProfileCardItemViewData("url5", "Emma Johnson", 1600, 11, 25, 4.6),
        TrainerProfileCardItemViewData("url6", "James Smith", 1900, 14, 35, 4.8),
        TrainerProfileCardItemViewData("url7", "Ava Brown", 1750, 13, 30, 4.7)
    )
}