package com.vurgun.skyfit.presentation.shared.features.user

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardViewData
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SkyFitUserProfileViewModel: ViewModel() {

    private val _appointments = MutableStateFlow<List<AppointmentCardViewData>>(emptyList())
    val appointments: StateFlow<List<AppointmentCardViewData>> get() = _appointments

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