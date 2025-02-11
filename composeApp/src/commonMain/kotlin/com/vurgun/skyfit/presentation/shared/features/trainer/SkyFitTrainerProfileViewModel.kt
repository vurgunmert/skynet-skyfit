package com.vurgun.skyfit.presentation.shared.features.trainer

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.presentation.mobile.features.trainer.profile.SpecialityItemComponentViewData
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.social.PostViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkyFitTrainerProfileViewModel : ViewModel() {

    private val _specialities = MutableStateFlow<List<SpecialityItemComponentViewData>>(emptyList())
    val specialities = _specialities.asStateFlow()

    private val _privateClasses = MutableStateFlow<List<SkyFitClassCalendarCardItem>>(emptyList())
    val privateClasses = _privateClasses.asStateFlow()

    private val _posts = MutableStateFlow<List<PostViewData>>(emptyList())
    val posts = _posts.asStateFlow()

    fun loadData() {
        _specialities.value = fakeSpecialities
        _privateClasses.value = fakePrivateClasses
        _posts.value = fakePosts
    }

    fun addPrivateClass(newClass: SkyFitClassCalendarCardItem) {
        _privateClasses.value = _privateClasses.value + newClass
    }

    fun removePrivateClass(title: String) {
        _privateClasses.value = _privateClasses.value.filter { it.title != title }
    }

    fun updatePrivateClass(updatedClass: SkyFitClassCalendarCardItem) {
        _privateClasses.value = _privateClasses.value.map {
            if (it.title == updatedClass.title) updatedClass else it
        }
    }

    val fakePrivateClasses: List<SkyFitClassCalendarCardItem> = listOf(
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

    val fakePosts: List<PostViewData> = List(6) { index ->
        PostViewData(
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

    val fakeSpecialities: List<SpecialityItemComponentViewData> = listOf(
        SpecialityItemComponentViewData("Fonksiyonel Antrenman", "push_up"),
        SpecialityItemComponentViewData("Kuvvet ve Kondisyon", "muscle"),
        SpecialityItemComponentViewData("Beslenme Danışmanlığı", "nutrition"),
        SpecialityItemComponentViewData("Atletik Performans Geliştirme", "athlete"),
        SpecialityItemComponentViewData("Beslenme Danışmanlığı", "nutrition"),
        SpecialityItemComponentViewData("Atletik Performans Geliştirme", "athlete")
    )
}

