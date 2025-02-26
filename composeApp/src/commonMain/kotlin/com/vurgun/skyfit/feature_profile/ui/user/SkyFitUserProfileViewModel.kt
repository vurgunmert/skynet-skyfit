package com.vurgun.skyfit.feature_profile.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_profile.ui.fakePosts
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.feature_social.ui.PostViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SkyFitUserProfileViewModel : ViewModel() {

    // User Profile Data
    private val _profileData = MutableStateFlow<TopBarGroupViewData>(TopBarGroupViewData())
    val profileData: StateFlow<TopBarGroupViewData> get() = _profileData

    private val _posts = MutableStateFlow<List<PostViewData>>(emptyList())
    val posts: StateFlow<List<PostViewData>> get() = _posts

    private val _appointments = MutableStateFlow<List<SkyFitClassCalendarCardItem>>(emptyList())
    val appointments: StateFlow<List<SkyFitClassCalendarCardItem>> get() = _appointments

    private val _exercises = MutableStateFlow<List<ProfileExerciseHistoryItem>>(emptyList())
    val exercises: StateFlow<List<ProfileExerciseHistoryItem>> get() = _exercises

    private val _habits = MutableStateFlow<List<ProfileHabitItem>>(emptyList())
    val habits: StateFlow<List<ProfileHabitItem>> get() = _habits

    private val _statistics = MutableStateFlow<UserProfileActivityStatisticsViewData?>(null)
    val statistics: StateFlow<UserProfileActivityStatisticsViewData?> get() = _statistics

    private val _photoDiary = MutableStateFlow<UserProfilePhotoDiaryViewData?>(null)
    val photoDiary: StateFlow<UserProfilePhotoDiaryViewData?> get() = _photoDiary

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
        _exercises.value = fakeProfileExercises
        _habits.value = fakeProfileHabits
        _photoDiary.value = UserProfilePhotoDiaryViewData()
    }

    private fun loadProfileData() {
        _profileData.value = TopBarGroupViewData(
            name = "Dexter Moore",
            social = "@dexteretymo",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            preferences = listOf(
                UserProfilePreferenceItem(iconId = "ic_height_outline","Boy", "175"),
                UserProfilePreferenceItem(iconId = "ic_dna_outline","Kilo", "75"),
                UserProfilePreferenceItem(iconId = "ic_overweight","Vücut Tipi", "Ecto")
            ),
            showInfoMini = false
        )
    }

    private fun loadPosts() {
        _posts.value = fakePosts
    }

    private fun loadAppointments() {
        _appointments.value = fakeAppointmentClasses
    }

    fun updateScroll(scrollValue: Int, firstItemIndex: Int) {
        _scrollValue.value = scrollValue
        _firstVisibleItemIndex.value = firstItemIndex
    }

    fun toggleShowPosts(show: Boolean) {
        _showPosts.value = show
    }
}

val fakeProfileExercises: List<ProfileExerciseHistoryItem> = listOf(
    ProfileExerciseHistoryItem("ic_push_up", "Şınav"),
    ProfileExerciseHistoryItem("ic_yoga", "Yoga"),
    ProfileExerciseHistoryItem("ic_pull_up_bar", "Pull up"),
    ProfileExerciseHistoryItem("ic_sit_up", "Mekik"),
    ProfileExerciseHistoryItem("ic_jumping_rope", "İp atlama"),
)

val fakeProfileHabits: List<ProfileHabitItem> = listOf(
    ProfileHabitItem("ic_sleep", "Düzensiz Uyku"),
    ProfileHabitItem("ic_fast_food", "Fast Food"),
    ProfileHabitItem("ic_smoking", "Smoking")
)

val fakeAppointmentClasses: List<SkyFitClassCalendarCardItem> = listOf(
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
        iconId = "ic_biceps_force"
    )
)

data class UserProfilePreferenceItem(val iconId: String,
                                     val title: String,
                                     val subtitle: String)

data class TopBarGroupViewData(
    val name: String = "",
    val social: String = "",
    val imageUrl: String = "",
    val preferences: List<UserProfilePreferenceItem> = emptyList(),
    val showInfoMini: Boolean = false // Whether to show the mini info card
)