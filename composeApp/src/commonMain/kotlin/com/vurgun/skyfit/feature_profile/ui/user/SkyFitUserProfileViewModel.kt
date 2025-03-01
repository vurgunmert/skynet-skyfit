package com.vurgun.skyfit.feature_profile.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_profile.ui.fakePosts
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionRowViewData
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

    private val _exercisesRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val exercisesRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _exercisesRowViewData

    private val _habitsRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val habitsRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _habitsRowViewData

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
        _photoDiary.value = UserProfilePhotoDiaryViewData()


        val exercisesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
        )
        _exercisesRowViewData.value = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
            title = "Egzersiz Geçmişi",
            items = exercisesViewData
        )

        val habitsViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
        )
        _habitsRowViewData.value = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
            title = "Alışkanlıklar",
            items = habitsViewData
        )
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