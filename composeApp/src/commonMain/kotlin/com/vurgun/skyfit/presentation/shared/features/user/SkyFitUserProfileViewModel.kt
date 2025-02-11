package com.vurgun.skyfit.presentation.shared.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardViewData
import com.vurgun.skyfit.presentation.shared.features.social.PostViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SkyFitUserProfileViewModel : ViewModel() {

    // User Profile Data
    private val _profileData = MutableStateFlow<TopBarGroupViewData?>(null)
    val profileData: StateFlow<TopBarGroupViewData?> get() = _profileData

    // Posts Data
    private val _posts = MutableStateFlow<List<PostViewData>>(emptyList())
    val posts: StateFlow<List<PostViewData>> get() = _posts

    // Appointments Data
    private val _appointments = MutableStateFlow<List<AppointmentCardViewData>>(emptyList())
    val appointments: StateFlow<List<AppointmentCardViewData>> get() = _appointments

    // UI State
    private val _showPosts = MutableStateFlow(false)
    val showPosts: StateFlow<Boolean> get() = _showPosts

    private val _scrollValue = MutableStateFlow(0)
    private val _firstVisibleItemIndex = MutableStateFlow(0)

    // Derived State for Mini Info Card
    val showInfoMini = combine(_scrollValue, _firstVisibleItemIndex) { scrollValue, firstItemIndex ->
        scrollValue > 30 || firstItemIndex > 1
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        loadProfileData()
        loadPosts()
        loadAppointments()
    }

    private fun loadProfileData() {
        _profileData.value = TopBarGroupViewData(
            name = "Dexter Moore",
            social = "@dexteretymo",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            preferences = listOf(
                ProfilePreferenceItem("Boy", "175"),
                ProfilePreferenceItem("Kilo", "75"),
                ProfilePreferenceItem("Vücut Tipi", "Ecto")
            ),
            showInfoMini = false
        )
    }

    private fun loadPosts() {
        _posts.value = fakePosts
    }

    private fun loadAppointments() {
        _appointments.value = fakeAppointments
    }

    fun updateScroll(scrollValue: Int, firstItemIndex: Int) {
        _scrollValue.value = scrollValue
        _firstVisibleItemIndex.value = firstItemIndex
    }

    fun toggleShowPosts(show: Boolean) {
        _showPosts.value = show
    }
}



data class ProfilePreferenceItem(val title: String, val subtitle: String)

data class TopBarGroupViewData(
    val name: String,
    val social: String,
    val imageUrl: String,
    val preferences: List<ProfilePreferenceItem>,
    val showInfoMini: Boolean = false // Whether to show the mini info card
)

val fakePosts = List(6) { index ->
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

val fakeAppointments = listOf(
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/strength.png",
        title = "Shoulders and Abs",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "Group Fitness",
        location = "@ironstudio",
        trainer = "Michael Blake",
        capacity = "10",
        cost = "Free",
        note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
        isFull = false,
        canNotify = true,
        status = "Planlanan" // Scheduled for the future
    ),
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/pilates.png",
        title = "Reformer Pilates",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "Pilates",
        location = "@ironstudio",
        trainer = "Michael Blake",
        capacity = "12",
        cost = "$20",
        note = null,
        isFull = false,
        canNotify = true,
        status = "Eksik" // Missed class (no-show)
    ),
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/fitness.png",
        title = "Fitness",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "PT",
        location = "@ironstudio",
        trainer = "Michael Blake",
        capacity = "15",
        cost = "$25",
        note = null,
        isFull = true,
        canNotify = true,
        status = "Tamamlandı" // Completed class (attended)
    ),
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/spinning.png",
        title = "Spinning Class",
        date = "15/10/2024",
        hours = "07:30 - 08:30",
        category = "Cycling",
        location = "@fitnesshub",
        trainer = "Emma Johnson",
        capacity = "20",
        cost = "$15",
        note = "Bring your own water bottle!",
        isFull = false,
        canNotify = false,
        status = "İptal" // Canceled
    ),
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/yoga.png",
        title = "Yoga Flow",
        date = "20/10/2024",
        hours = "18:00 - 19:00",
        category = "Yoga",
        location = "@zenstudio",
        trainer = "Samantha Green",
        capacity = "15",
        cost = "Free",
        note = "Mats provided, please bring a towel.",
        isFull = false,
        canNotify = false,
        status = "İptal" // Canceled
    ),
    AppointmentCardViewData(
        iconUrl = "https://example.com/icons/stretching.png",
        title = "Stretching & Mobility",
        date = "05/09/2024",
        hours = "12:00 - 13:00",
        category = "Recovery",
        location = "@recoverycenter",
        trainer = "Lisa Harper",
        capacity = "5",
        cost = "Free",
        note = "Foam rollers provided.",
        isFull = false,
        canNotify = false,
        status = "Eksik" // No-show
    )
)