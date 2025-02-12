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
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
                "https://images.squarespace-cdn.com/content/v1/63f59136c5b45330af8a1b13/be8fe8de-0eec-49c6-9140-82a501e0422e/Screen+Shot+2023-04-19+at+3.01.08+PM.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0HX6fpDHt9hoOC5XPVJtHMGgLiXpcICKXfA&s",
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
                "https://www.teatatutoasted.co.nz/cdn/shop/articles/Copy_of_Blog_pics_3_195ecc96-5c21-4363-b9c4-1e9458217175_768x.png?v=1727827333",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPfpidl_dC5LY4ASphW2qVybLkXqW5bI-BXg&s",
                "https://images.squarespace-cdn.com/content/v1/5885cce9e6f2e17ade281ea3/1729523033091-3GQ5CLQPRB5Z056ZKBYU/4.png?format=2500w",
                null,
                null,
                null,
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

