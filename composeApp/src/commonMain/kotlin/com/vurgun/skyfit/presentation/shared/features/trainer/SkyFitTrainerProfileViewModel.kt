package com.vurgun.skyfit.presentation.shared.features.trainer

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItem

class SkyFitTrainerProfileViewModel: ViewModel() {

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


    val posts = List(6) { index ->
        SkyFitPostCardItem(
            postId = "post_${index + 1}",
            username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
            socialLink = listOf("https://instagram.com/user", "https://twitter.com/user", "https://linkedin.com/user", null).random(),
            timeAgo = listOf("5 min ago", "2 hours ago", "1 day ago", "3 days ago", "1 week ago").random(),
            profileImageUrl = listOf(
                "https://example.com/profile1.png",
                "https://example.com/profile2.png",
                "https://example.com/profile3.png",
                "https://example.com/profile4.png",
                null
            ).random(),
            content = listOf(
                "Just finished an amazing workout! 💪",
                "Morning yoga session done! 🧘‍♀️",
                "Any tips for increasing stamina? 🏃‍♂️",
                "Trying out a new HIIT routine. 🔥",
                "Recovery day with some light stretching.",
                "Nutrition is key! What’s your go-to meal?"
            ).random(),
            imageUrl = listOf(
                "https://example.com/post1.jpg",
                "https://example.com/post2.jpg",
                "https://example.com/post3.jpg",
                null
            ).random(),
            favoriteCount = (0..500).random(),
            commentCount = (0..200).random(),
            shareCount = (0..100).random(),
        )
    }
}